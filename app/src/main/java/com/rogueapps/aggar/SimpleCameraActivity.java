/*
 * Copyright (C) 2014 BeyondAR
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rogueapps.aggar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.view.OnClickBeyondarObjectListener;
import com.beyondar.android.world.BeyondarObject;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleCameraActivity extends FragmentActivity implements OnClickBeyondarObjectListener {

	private BeyondarFragmentSupport mBeyondarFragment;
	private World mWorld;
	int onClickSignal;
	static int times;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		times = 0;
		onClickSignal = 0;
		// Hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.simple_camera);
		
		mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager().findFragmentById(
				R.id.beyondarFragment);
		
		// We create the world and fill it ...
		mWorld = CustomWorldHelper.generateObjects(this);
		// ... and send it to the fragment
		mBeyondarFragment.setWorld(mWorld);

		// We also can see the Frames per seconds
		mBeyondarFragment.showFPS(true);
		mBeyondarFragment.setOnClickBeyondarObjectListener(this);

	}

	@Override
	public void onClickBeyondarObject(ArrayList<BeyondarObject> beyondarObjects){
		times++;
		int[] drawables = new int[100];
		for(int i=1;i<11;i++)drawables[i] = R.drawable.object_8_old;
		if (beyondarObjects.size()>0) {
			BeyondarObject object = beyondarObjects.get(0);
			long id = object.getId();
			GeoObject geoObject = (GeoObject)object;
			if(times==1) {object.setImageResource(drawables[(int)id]);mWorld.setGeoPosition(geoObject.getLatitude()-0.00003d,geoObject.getLongitude());}
			//mWorld.setGeoPosition(geoObject.getLatitude()+0.00001d,geoObject.getLongitude()-0.00005d);
			else if(times==2){object.setImageResource(R.drawable.doherty);}
			else if(times>=3){object.setImageResource(R.drawable.objwct_8_acessibility);}
			Log.d("latitude", geoObject.getLatitude()+"");
		}
	}
}

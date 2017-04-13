package com.rogueapps.aggar.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.util.location.BeyondarLocationManager;
import com.beyondar.android.view.OnClickBeyondarObjectListener;
import com.beyondar.android.world.BeyondarObject;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.rogueapps.aggar.R;
import com.rogueapps.aggar.controllers.GeoObjectController;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnClickBeyondarObjectListener,View.OnClickListener {

    /*-------Variables--------*/
    private Location currentLocation;
    private MiLocationListener milocListener;
    private BeyondarFragmentSupport mBeyondarFragment;
    private World world;
    private Button mShowMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadViewFromXML();
        configureARModule();
        //configureLocationModule();
        configureSidebar(toolbar);
    }

    private void configureARModule() {
        mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager().findFragmentById(R.id.beyondarFragment);
        mBeyondarFragment.setMaxDistanceToRender(50);
        mBeyondarFragment.setOnClickBeyondarObjectListener(this);
        world = new World(this);
        world.setDefaultImage(R.drawable.untouched);
        world.setGeoPosition(GeoObjectController.lat,GeoObjectController.lon);
        world = GeoObjectController.fillWorld(world);
        mBeyondarFragment.setWorld(world);
    }

    private void configureLocationModule() {
        milocListener = new MiLocationListener();
        BeyondarLocationManager.setLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        BeyondarLocationManager.addWorldLocationUpdate(world);
        BeyondarLocationManager.addLocationListener(milocListener);
    }

    private void configureSidebar(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BeyondarLocationManager.disable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BeyondarLocationManager.enable();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    static int time = 0;
    GeoObject object = null;
    @Override
    public void onClickBeyondarObject(ArrayList<BeyondarObject> beyondarObjects) {
        int[] drawables = new int[100];
        for(int i=0;i<4;i++)drawables[i] = R.drawable.dohertyred;
        if(beyondarObjects.get(0).getId()==5){
        TextView view = (TextView) findViewById(R.id.targetText);
        TextView desc = (TextView) findViewById(R.id.textDesc);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
                view.setText("Doherty accessibility");
                desc.setText("Popularly known as \"The Living Room of Texas A&M\", the Memorial Student Center (MSC) has been a living memorial, a living room, and a living tradition at Texas A&M University. Dedicated on Muster Day (April 21) in 1951, the MSC was originally dedicated to those Aggies who gave their lives during World Wars I and II, but was later rededicated to all Aggies who have given or will give their lives in wartime.[85] Because the building and grounds are a memorial, those entering the MSC are asked to \"uncover\" (remove their hats) and not walk on the surrounding grass lawns.[86]\n" +
                        "On the main floor of the MSC is the Flagroom, a large, flag-lined room which students use for meetings, visiting, napping, and studying. The MSC also contains a bookstore, a bank, three art galleries, three dining facilities, and two ballrooms, one of which named after Robert Gates. Additionally, the MSC contains many meeting rooms and is the home of numerous student committees \"that provide an array of educational, cultural, recreational and entertainment programs for the Texas A&M community.\"[85]\n" +
                        "In 2007, the Aggie student body voted for $122 million renovations to the Memorial Student Center, allowing it to become fully compliant with both fire code and the Americans with Disabilities Act. The project began in the summer of 2009, requiring the building to remain closed due to the renovations.[87] The renovations increased the size of the building to accommodate the growing school population, and make more efficient use of existing space.[88][89] The MSC reopened on Muster Day, April 21, 2012, 61 years after its original opening.");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.dohertyaccessible));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);
        }
        else if (beyondarObjects.size() > 0&&(time&1)==0) {
            time++;
            Log.d("time", time+"");
            long id = beyondarObjects.get(0).getId();
            beyondarObjects.get(0).setImageResource(R.drawable.dohertyred);
            //30.6190672,-96.3402015
            object = GeoObjectController.createGeoObject(5L,GeoObjectController.lat+0.0002d,GeoObjectController.lon+0.0002d,"Dohertywheelchair", R.drawable.wheelchair);
            world.addBeyondarObject(object);
        }
        else if (beyondarObjects.size() > 0&&(time&1)!=0) {
            time++;
            beyondarObjects.get(0).setImageResource(R.drawable.doherty);
            if(object!=null)world.remove(object);
        }
//        BeyondarObject touchedObject = beyondarObjects.get(0);
//        TextView view = (TextView) findViewById(R.id.targetText);
//        TextView desc = (TextView) findViewById(R.id.textDesc);
//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        switch((int)touchedObject.getId()){
//            case 0:
//                view.setText("Memorial Student Center");
//                desc.setText("Popularly known as \"The Living Room of Texas A&M\", the Memorial Student Center (MSC) has been a living memorial, a living room, and a living tradition at Texas A&M University. Dedicated on Muster Day (April 21) in 1951, the MSC was originally dedicated to those Aggies who gave their lives during World Wars I and II, but was later rededicated to all Aggies who have given or will give their lives in wartime.[85] Because the building and grounds are a memorial, those entering the MSC are asked to \"uncover\" (remove their hats) and not walk on the surrounding grass lawns.[86]\n" +
//                        "On the main floor of the MSC is the Flagroom, a large, flag-lined room which students use for meetings, visiting, napping, and studying. The MSC also contains a bookstore, a bank, three art galleries, three dining facilities, and two ballrooms, one of which named after Robert Gates. Additionally, the MSC contains many meeting rooms and is the home of numerous student committees \"that provide an array of educational, cultural, recreational and entertainment programs for the Texas A&M community.\"[85]\n" +
//                        "In 2007, the Aggie student body voted for $122 million renovations to the Memorial Student Center, allowing it to become fully compliant with both fire code and the Americans with Disabilities Act. The project began in the summer of 2009, requiring the building to remain closed due to the renovations.[87] The renovations increased the size of the building to accommodate the growing school population, and make more efficient use of existing space.[88][89] The MSC reopened on Muster Day, April 21, 2012, 61 years after its original opening.");
//                imageView.setImageDrawable(getResources().getDrawable(R.drawable.mscpic));
//                break;
//            case 1:
//                view.setText("12 Man Statue");
//                desc.setText("On Jan. 2, 1922, the heavily outgunned Aggies were facing the top-ranked Centre College Praying Colonels on the gridiron in the Dixie Classic in Dallas. An Aggie by the name of E. King Gill, a squad player for Texas A&M’s football team, was up in the press box helping reporters identify players on the field below — and what was happening on the field wasn’t pretty.\n" +
//                        "The Aggies found themselves plagued by injuries, with their reserves seemingly dwindling with every play. As Texas A&M Coach Dana X. Bible looked across his rapidly emptying bench, he suddenly remembered Gill’s presence in the stands. Bible waved Gill down to the sideline and told him to suit up. Gill ran under the bleachers and put on the uniform of injured running back Heine Weir, who had been knocked out of the game in the first quarter.\n" +
//                        "Gi                    Mll returned to the sideline, where he stood ready to play for the entirety of the game. When the last play was run, the Aggies found that they had pulled off one of the greatest upsets in college football history, winning the game 22-14.\n" +
//                        "And Gill remained standing, the only player left on the team’s bench.");
//                imageView.setImageDrawable(getResources().getDrawable(R.drawable.manstatpic));
//                break;
//    }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*-----------Location Data--------------*/
    private class MiLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location newLocation) {
            currentLocation = newLocation;
            TextView view = (TextView) findViewById(R.id.textView);
            view.setText("----------\nLat = " + currentLocation.getLatitude()
                            + "\nLongitude = " + currentLocation.getLongitude()
                            + "\n----------");
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Desactivado",
                    Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Activo",
                    Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    //google map
    private void loadViewFromXML() {
        mShowMap = (Button) findViewById(R.id.showMapButton);
        mShowMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mShowMap) {
            Intent intent = new Intent(this, GoogleMapActivity.class);
            startActivity(intent);
        }
    }
}

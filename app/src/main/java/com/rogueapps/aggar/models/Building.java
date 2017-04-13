package com.rogueapps.aggar.models;

import android.location.Location;
import java.util.List;
/**
 * Created by zhangsikai on 11/17/16.
 */

public class Building extends Object {
    //attributes
    private String history;

    private BuildingInformation buildingInformation;

    private List<Event> events;

    private String news;

    //constructor
    public Building(){}

    public Building(String name, Location location) {super(name, location);}

    //getter
    String getHistory(){return history;}

    BuildingInformation getBuildingInformation(){return buildingInformation;}

     List<Event> getEvent(){return events;}

     String getNews(){return news;}



    //Setter
    private void setHistory(String history){this.history=history;}

    private void setBuildingInformation(BuildingInformation buildingInformation){this.buildingInformation=buildingInformation;}

    private void setEvent(List<Event> events){this.events=events;}

    private void setNews(String news){this.news=news;}

}

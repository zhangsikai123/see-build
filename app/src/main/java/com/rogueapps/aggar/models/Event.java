package com.rogueapps.aggar.models;


/**
 * Created by zhangsikai on 11/17/16.
 */

public class Event {
    //the start time of event
    private String startTime;

    //the end time of event
    private String endTime;

    //hosted by
    private String host;

    public Event(){}

    String getStartTime(){return startTime;}

    String getEndTime(){return endTime;}

    String host(){return host;}

    void setStartTime(String startTime){this.startTime = startTime;}

    void setEndTime(String endTime){this.endTime = endTime;}

    void setHost(String host){this.host = host;}


}

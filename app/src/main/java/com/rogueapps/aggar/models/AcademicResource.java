package com.rogueapps.aggar.models;

import java.util.List;

/**
 * Created by zhangsikai on 11/17/16.
 */

public class AcademicResource {

    private String name;

    private List<String> rooms;

    public AcademicResource(){}

    String getName(){return name;}

    List<String> getRooms(){return rooms;}

    void setName(String name){this.name = name;}

    void setRooms(List<String> rooms){this.rooms = rooms;}


}

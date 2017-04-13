package com.rogueapps.aggar.models;

import java.util.List;

/**
 * Created by zhangsikai on 11/17/16.
 */

public class BuildingInformation {

    private List<People> people;

    private List<OrganizationOrDepartment> organizationsOrDepartments;

    private List<AcademicResource> academicResources;



    public BuildingInformation(){}



     List<People> getPeople(){return people;}

     List<OrganizationOrDepartment> getOrganizationsOrDepartments(){return organizationsOrDepartments;}

     List<AcademicResource> getAcademicResources(){return academicResources;}

     void setPeople(List<People> people){this.people = people;}

     void setOrganizationsOrDepartments(List<OrganizationOrDepartment> organizationsOrDepartments){this.organizationsOrDepartments = organizationsOrDepartments;}

     void setAcademicResources(List<AcademicResource> academicResources){this.academicResources = academicResources;}

}

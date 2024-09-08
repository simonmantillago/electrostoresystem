package com.electrostoresystem.city.domain.entity;

public class City {
    private int  id;
    private String name;
    private int regionId;
    
    public City() {
    }

    public City(int id, String name, int regionId) {
        this.id = id;
        this.name = name;
        this.regionId = regionId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getRegionId() {
        return regionId;
    }
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
    
    
}

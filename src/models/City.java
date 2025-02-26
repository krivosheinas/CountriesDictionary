package models;

import extensions.SourceName;

import java.util.UUID;

public class City extends SourceName {

    public int population;

    public City(String name, int population){
        this.name = name;
        this.population = population;
    }

    public City(UUID uuid, String name, int population){
        this.uuid = uuid;
        this.name = name;
        this.population = population;
    }

    @Override
    public String getString(){
        return "\tГород: " + name + " (Население: " + String.format("%,d",population) + ")";
    }

    @Override
    public String convertToString() {
        return String.format("%s|%s|%s", uuid, name,population);
    }


}

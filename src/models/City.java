package models;

import common.Helper;
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

    public  void Edit(String name, int population){
        this.name = name;
        this.population = population;
    }

    @Override
    public String getInfo(){

        return "\tГород: " + name + " (Население: " + String.format("%,d",population) + ")";

    }

    @Override
    public String packedStr() {

        return Helper.unionStrings(uuid.toString(), name, String.valueOf(population));

    }

    @Override
    public String getName() {
        return String.format("%s (%s)", name, "Город");
    }

}

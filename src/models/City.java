package models;

import interfaces.IBasic;
import with.SourceName;

public class City extends SourceName {

    public int population;

    public City(String name, int population){
        this.name = name;
        this.population = population;
    }

    @Override
    public String getString(){
        return "\tГород: " + name + " (Население: " + String.format("%,d",population) + ")";
    }

    @Override
    public String convertToString() {
        return String.format("%s|%s",name,population);
    }


}

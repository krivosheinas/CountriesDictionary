package models;

import interfaces.IBasic;
import with.SourceName;

public class City extends SourceName implements IBasic {

    public int population;

    public City(String name, int population){
        this.name = name;
        this.population = population;
    }

    @Override
    public String getString(){
        return "\tГород: " + name + " (Население: " + String.format("%,d",population) + ")";
    }

}

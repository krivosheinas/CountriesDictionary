package models;

import interfaces.IBasic;
import with.WithName;

public class City extends WithName implements IBasic {

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

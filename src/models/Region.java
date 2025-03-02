package models;

import common.Helper;
import extensions.SourceList;
import extensions.SourceName;

import java.util.UUID;

public class Region extends SourceName {

    public SourceList<City> cities = new SourceList<>();

    public Region(String name){
        this.name = name;
    }

    public Region(UUID uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }
    public  void Edit(String name){
        this.name = name;
    }

    @Override
    public String getInfo() {

        StringBuilder sb = new StringBuilder();

        sb.append("\tРегион: " + name).append("\n");
        for (var city : cities.all()) {
            sb.append("\t").append(city.getInfo()).append("\n");
        }

        return sb.toString();
    }

    @Override
    public String packedStr() {

        return Helper.unionStrings(uuid.toString(), name);

    }

    @Override
    public String getName() {
        return String.format("%s (%s)", name, "Регион");
    }


}

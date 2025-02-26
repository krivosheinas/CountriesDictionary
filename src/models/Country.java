package models;
import extensions.SourceList;
import extensions.SourceName;

import java.util.UUID;

public class Country extends SourceName {

    public SourceList<Region> regions = new SourceList<>();

    public Country(String name){
        this.name = name;
    }
    public Country(UUID uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Страна: ").append(name).append("\n");
        for (var region : regions.all()){
            sb.append(region.getString());
        }
        return sb.toString();
    }

    @Override
    public String convertToString() {
        return String.format("%s|%s",uuid,name);
    }
}

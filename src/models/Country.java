package models;
import common.Helper;
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

    public  void Edit(String name){
        this.name = name;
    }

    @Override
    public String getInfo() {

        StringBuilder sb = new StringBuilder();

        sb.append("Страна: ").append(name).append("\n");
        for (var region : regions.all()){
            sb.append(region.getInfo());
        }

        return sb.toString();
    }

    @Override
    public String packedStr() {

        return Helper.unionStrings(uuid.toString(), name);

    }

}

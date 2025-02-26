package models;

import common.Helper;
import enums.RegionType;
import extensions.SourceList;
import extensions.SourceName;

import java.util.UUID;

public class Region extends SourceName {

    public RegionType regionType;

    public SourceList<City> cities = new SourceList<>();

    public Region(String name, RegionType regionType){
        this.name = name;
        this.regionType = regionType;
    }

    public Region(UUID uuid, String name, RegionType regionType){
        this.uuid = uuid;
        this.name = name;
        this.regionType = regionType;
    }

    private String getRegionTypeName (){

        return
                switch (regionType){
                    case AREA ->  "Область";
                    case REGION ->  "Край";
                    case REPUBLIC ->  "Республика";
                    case AUTONOMIC_AREA ->  "Автономная область";
                    case AUTONOMIC_DISTRICT -> "Автономный округ";
                    case FEDERAL_CITY -> "Город федерального значения";
                    case STATE -> "Штат";
                    case DISTRICT -> "Округ";
                    default -> "тип региона не определен";
                };

    }

    @Override
    public String getInfo() {

        StringBuilder sb = new StringBuilder();

        sb.append("\tРегион: " + String.format("%s (%s)", name, getRegionTypeName())).append("\n");
        for (var city : cities.all()) {
            sb.append("\t").append(city.getInfo()).append("\n");
        }

        return sb.toString();
    }

    @Override
    public String packedStr() {

        return Helper.unionStrings(uuid.toString(), name, regionType.name());

    }


}

package models;

import enums.RegionType;
import with.SourceList;
import with.SourceName;

public class Region extends SourceName {

    public RegionType regionType;

    public SourceList<City> cities = new SourceList<>();

    public Region(String name, RegionType regionType){
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
    public String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tРегион: " + String.format("%s (%s)", name, getRegionTypeName())).append("\n");
        for (var city : cities.all()) {
            sb.append("\t").append(city.getString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String convertToString() {
        return String.format("%s|%s",name, regionType.name());
    }


}

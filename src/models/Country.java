package models;
import with.SourceList;
import with.SourceName;

public class Country extends SourceName {

    public SourceList<Region> regions = new SourceList<>();

    public Country(String name){
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
        return name;
    }
}

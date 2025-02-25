package models;
import interfaces.IBasic;
import with.SourceList;
import with.SourceName;

public class Country extends SourceName implements IBasic {

    public SourceList<Region> regions = new SourceList<>();

    public Country(String name){
        this.name = name;
    }

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Страна: ").append(name).append("\n");
        for (var region : regions.get()){
            sb.append(region.getString());
        }
        return sb.toString();
    }
}

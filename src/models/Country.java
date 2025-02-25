package models;
import interfaces.IBasic;
import with.WithList;

public class Country extends WithList<Region> implements IBasic {

    public Country(String name){
        this.name = name;
    }

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Страна: ").append(name).append("\n");
        for (var region : this.getSubjects()){
            sb.append(region.getString());
        }
        return sb.toString();
    }
}

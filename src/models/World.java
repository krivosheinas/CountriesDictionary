package models;

import interfaces.IBasic;
import with.WithList;

public class World extends WithList<Country> implements IBasic {

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();
        for (var country : this.getSubjects()){
            sb.append(country.getString()).append("\n");
        }
        return sb.toString();
    }
}

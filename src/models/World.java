package models;

import interfaces.ISource;
import extensions.SourceList;

public class World implements ISource {

    public SourceList<Country> countries = new SourceList<>();
    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();
        for (var country : countries.all()){
            sb.append(country.getString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String convertToString() {
        return null;
    }
}

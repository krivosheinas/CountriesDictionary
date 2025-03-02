package models;

import interfaces.ISource;
import extensions.SourceList;

public class World implements ISource {

    public SourceList<Country> countries = new SourceList<>();

    @Override
    public String getInfo() {

        StringBuilder sb = new StringBuilder();
        for (var country : countries.all()){
            sb.append(country.getInfo()).append("\n");
        }

        return sb.toString();
    }

    @Override
    public String packedStr() {

        return null;

    }

    @Override
    public String getName() {
        return null;
    }

}

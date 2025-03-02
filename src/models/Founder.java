package models;

import enums.SourceType;
import extensions.SourceName;

import java.util.ArrayList;


public class Founder<T extends SourceName> {
    public SourceType sourceType;
    public T source;
    public ArrayList<String> levels = new ArrayList<>();

    public Founder (T source, SourceType sourceType){
        this.source = source;
        this.sourceType = sourceType;
    }

    public Founder (T source, SourceType sourceType, String ... levels){
        this.source = source;
        this.sourceType = sourceType;
        for (var level: levels){
            this.levels.add(level);
        }
    }

}

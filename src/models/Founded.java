package models;

import enums.SourceType;
import extensions.SourceName;

import java.util.ArrayList;


public class Founded<T extends SourceName> {
    public T  source;
    public T level1;
    public T level2;

    public Founded(T source){
        this.source = source;
        this.level1 = null;
        this.level2 = null;
    }

    public Founded(T source, T level1){
        this.source = source;
        this.level1 = level1;
        this.level2 = null;
    }

    public Founded(T source, T level1, T level2){
        this.source = source;
        this.level1 = level1;
        this.level2 = level2;
    }
}

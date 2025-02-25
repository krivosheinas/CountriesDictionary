package with;

import java.util.ArrayList;
import java.util.Collections;

public class SourceList<T extends SourceName> extends SourceName {

    private ArrayList<T> source = new ArrayList<>();
    private ArrayList<T> temp = new ArrayList<>();

    public ArrayList<T> get() {
        return temp;
    }

    public void append(T ... subjects){
        if (subjects == null){
            return;
        }
        for (var subject: subjects){
            this.addToSource(subject);
        }
    }

    private void addToSource(T subject){
        if (subject == null){
            return;
        }
        if (ifExists(subject.name)){
            return;
        }
        this.source.add(subject);
        reset();
    }

    public ArrayList<T> find(String condition){
        this.temp = new ArrayList<>();
        for (var subject: source) {
            if (subject.name.indexOf(condition) > -1){
                this.temp.add(subject);
            }
        }
        return this.get();
    }


    private void removeFromSource(T subject){
        if (this.source.contains(subject)){

            this.source.remove(subject);
        }
        this.reset();
    }

    public void remove(T ... subjects){
        if (subjects == null){
            return;
        }
        for (var subject: subjects){
            this.removeFromSource(subject);
        }
    }

    public void sortAsc (){
        Collections.sort(this.temp, (s1, s2) -> s1.name.compareTo(s2.name) );
    }

    public void sortDesc (){
        Collections.sort(this.temp, (s1, s2) -> s2.name.compareTo(s1.name) );
    }

    private void reset (){
        this.temp = new ArrayList<>();
        for (var subject: source) {
            this.temp.add(subject);
        }
    }

    private Boolean ifExists (String name){
        var isExists = false;
        for (var subject: source){
            if (subject.name.equalsIgnoreCase(name)){
                isExists = true;
                break;
            }
        }
        return isExists;
    }
}

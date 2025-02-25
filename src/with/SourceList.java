package with;

import java.util.ArrayList;
import java.util.Collections;

public class SourceList<T extends SourceName> extends SourceName {

    private ArrayList<T> source = new ArrayList<>();

    public ArrayList<T> all() {
        return source;
    }

    public T getByIndex(int index){
        if (source.isEmpty() || source.size() < index + 1){
            return null;
        }
        return source.get(index);
    }

    public void addByIndex(int index, T subject){
        if (source.size() < index ){
            return;
        }
        source.add(index, subject);
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
    }

//    public ArrayList<T> find(String condition){
//        for (var subject: source) {
//            if (subject.name.indexOf(condition) > -1){
//                this.source.add(subject);
//            }
//        }
//        return this.get();
//    }


    private void removeFromSource(T subject){
        if (this.source.contains(subject)){

            this.source.remove(subject);
        }
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
        Collections.sort(this.source, (s1, s2) -> s1.name.compareTo(s2.name) );
    }

    public void sortDesc (){
        Collections.sort(this.source, (s1, s2) -> s2.name.compareTo(s1.name) );
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

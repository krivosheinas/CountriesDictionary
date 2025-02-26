package extensions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class SourceList<T extends SourceName>  {
    private ArrayList<T> source = new ArrayList<>();
    public ArrayList<T> all() {
        return source;
    }

    public T get (UUID uuid){
        if (source.isEmpty()){
            return null;
        }
        for (var item : source){
            if (item.uuid.equals(uuid)){
                return  item;
            }
        }
        return null;
    }


    public T get (int index){
        if (source.isEmpty()){
            return null;
        }

        if (source.size() <= index){
            return null;
        }

        return source.get(index);
    }

    public void append (T ... subjects){
        if (subjects == null){
            return;
        }
        for (var subject: subjects){
            addToSource(subject);
        }
    }

    private void addToSource (T subject){
        if (subject == null){
            return;
        }
        if (ifExists(subject.name)){
            return;
        }
        source.add(subject);
    }

    public ArrayList<T> find (String condition){
        if (source.isEmpty()){
            return new ArrayList<T>();
        }

        var foundList = new ArrayList<T>();
        for (var subject: source) {
            if (subject.name.indexOf(condition) > -1){
                foundList.add(subject);
            }
        }
        return foundList;
    }

    private void removeFromSource (T subject){
        if (source.contains(subject)){
            source.remove(subject);
        }
    }

    public void remove (T ... subjects){
        if (subjects == null){
            return;
        }
        for (var subject: subjects){
            removeFromSource(subject);
        }
    }

    public void sortAsc (){
        Collections.sort(source, (s1, s2) -> s1.name.compareTo(s2.name) );
    }

    public void sortDesc (){
        Collections.sort(source, (s1, s2) -> s2.name.compareTo(s1.name) );
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

    public String pointer() {
        StringBuilder sb = new StringBuilder();

        for (var i=0; i < source.size(); i++){
            sb.append(String.format("%s. %s\n", i + 1, source.get(i).name));
        }

        return sb.toString();
    }
}

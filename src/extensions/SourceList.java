package extensions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class SourceList<T extends SourceName>  {
    private ArrayList<T> source = new ArrayList<>();
    public ArrayList<T> all() {
        return source;
    }

    private boolean ifEmpty(){
        if (source == null) {
            return true;
        }
        if (source.isEmpty()){
            return true;
        }
        return false;
    }

    public T get (UUID uuid){
        if (ifEmpty()){
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
        if (ifEmpty()){
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

        if (source == null){
            return;
        }

        if (ifExists(subject.name)){
            return;
        }
        source.add(subject);
    }

    public SourceList<T> find (String condition){

        if (ifEmpty()){
            return new SourceList<T>();
        }

        var sourceList = new SourceList<T>();
        for (var subject: source) {
            if (subject.name.toLowerCase().contains(condition.toLowerCase())){
                sourceList.append(subject);
            }
        }
        return sourceList;
    }

    private void removeFromSource (T subject){

        if (ifEmpty()){
            return;
        }

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
        if (ifEmpty()){
            return;
        }
        Collections.sort(source, (s1, s2) -> s1.name.compareTo(s2.name) );
    }

    public void sortDesc (){
        if (ifEmpty()){
            return;
        }
        Collections.sort(source, (s1, s2) -> s2.name.compareTo(s1.name) );
    }

    private Boolean ifExists (String name){
        if (ifEmpty()){
            return false;
        }

        for (var subject: source){
            if (subject.name.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public String pointer() {
        StringBuilder sb = new StringBuilder();

        for (var i = 0; i < source.size(); i++){
            sb.append(String.format("%s. %s\n", i + 1, source.get(i).name));
        }

        return sb.toString();
    }
}

package with;

import java.util.ArrayList;
import java.util.Collections;

public class WithList<T extends WithName> extends WithName {

    private ArrayList<T> normal = new ArrayList<>();
    private ArrayList<T> filtered = new ArrayList<>();

    public ArrayList<T> getSubjects() {
        return filtered;
    }

    public void addSubjects(ArrayList<T> subjects){
        if (subjects == null){
            return;
        }
        for (var subject: subjects){
            this.addSubject(subject);
        }
    }

    public void addSubject(T subject){
        if (subject == null){
            return;
        }
        if (isSubjectExists(subject.name)){
            return;
        }
        this.normal.add(subject);
        reset();
    }

    public ArrayList<T> findSubjects(String condition){
        this.filtered = new ArrayList<>();
        for (var subject: normal) {
            if (subject.name.indexOf(condition) > -1){
                this.filtered.add(subject);
            }
        }
        return this.getSubjects();
    }


    public void removeSubject(T subject){
        if (this.normal.contains(subject)){

            this.normal.remove(subject);
        }
        this.reset();
    }

    public void removeSubjects(ArrayList<T> subjects){
        if (subjects == null){
            return;
        }
        for (var subject: subjects){
            this.removeSubject(subject);
        }
    }

    public void sortAsc (){
        Collections.sort(this.filtered, (s1,s2) -> s1.name.compareTo(s2.name) );
    }

    public void sortDesc (){
        Collections.sort(this.filtered, (s1,s2) -> s2.name.compareTo(s1.name) );
    }

    private void reset (){
        this.filtered = new ArrayList<>();
        for (var subject: normal) {
            this.filtered.add(subject);

        }
    }

    private Boolean isSubjectExists(String name){
        var isExists = false;
        for (var subject: normal){
            if (subject.name.equalsIgnoreCase(name)){
                isExists = true;
                break;
            }
        }
        return isExists;
    }
}

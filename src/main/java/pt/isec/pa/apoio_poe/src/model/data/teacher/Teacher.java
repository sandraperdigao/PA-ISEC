package pt.isec.pa.apoio_poe.src.model.data.teacher;

import java.io.Serializable;
import java.util.List;

public class Teacher implements Serializable, Cloneable, Comparable<Teacher>{
    protected final String name;
    protected final String email;

    public Teacher(List<String> args){
        this.name = args.get(0);
        this.email = args.get(1);
    }

    Teacher(Teacher toCopy){
        this.name = toCopy.getName();
        this.email = toCopy.getEmail();
    }

    public String getName(){return name;}
    public String getEmail(){return email;}

    public static boolean isListValidAsTeacher(List<String> args) {
        return args.size() == 2;
    }

    @Override
    public String toString(){return "-> " + name + "," + email;}

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        Teacher teacher = (Teacher) o;
        return this.getEmail().equals(teacher.getEmail());
    }

    @Override
    public int hashCode(){
        return this.getEmail().hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(Teacher o){
        return this.hashCode() - o.hashCode();
    }
}

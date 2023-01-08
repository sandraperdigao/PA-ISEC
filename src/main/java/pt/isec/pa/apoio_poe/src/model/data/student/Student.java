package pt.isec.pa.apoio_poe.src.model.data.student;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.enums.Branch;
import pt.isec.pa.apoio_poe.src.model.data.enums.Course;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;
import static pt.isec.pa.apoio_poe.src.utils.FileUtils.isStringValidAsBool;

public class Student implements Serializable, Cloneable, Comparable<Student> {

    protected final Long nrStudent;
    protected final String name;
    protected final String email;
    protected final String abbCourse;
    protected final String abbBranch;
    protected final Double classification;
    protected final boolean hasAccess;

    Student(Student toCopy){
        this.nrStudent = toCopy.getNrStudent();
        this.name = toCopy.getName();
        this.email = toCopy.getEmail();
        this.abbCourse = toCopy.getAbbCourse();
        this.abbBranch = toCopy.getAbbBranch();
        this.classification = toCopy.getClassification();
        this.hasAccess = toCopy.getHasAccess();
    }

    public Student(List<String> args) {
        this.nrStudent = parseLong(args.get(0));
        this.name = args.get(1);
        this.email = args.get(2);
        this.abbCourse = args.get(3);
        this.abbBranch = args.get(4);
        this.classification = parseDouble(args.get(5));
        this.hasAccess = parseBoolean(args.get(6));
    }

    private Student(Long nrStudent, String email){
        this.nrStudent = nrStudent;
        this.name = null;
        this.email = email;
        this.abbCourse = null;
        this.abbBranch = null;
        this.classification = 0D;
        this.hasAccess = false;
    }

    public Long getNrStudent(){return nrStudent;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getAbbCourse(){return abbCourse;}
    public String getAbbBranch(){return abbBranch;}
    public Double getClassification(){return classification;}
    public boolean getHasAccess(){return hasAccess;}

    public static boolean isListValidAsStudent(List<String> args) {
        if (args.size() != 7)
            return false;

        try{
            if(parseLong(args.get(0)) < 1921000000L || parseLong(args.get(0)) > 9999999999L){
                Logger.appendMessage("A linha " + args + " tem um numero de estudante fora de formato!" );
                return false;
            }

            try{
                String [] abbCourseSplit = args.get(3).split("[|]+");
                for(String item : abbCourseSplit)
                    if(!Course.checkInEnum(item)) {
                        Logger.appendMessage("A linha " + args + " tem uma designação de curso/regime inválida!");
                        return false;
                    }
            } catch (IllegalArgumentException ex) {
                Logger.appendMessage("A linha " + args + " tem uma designação de curso/regime inválida!" );
                return false;
            }

            try{
                String [] abbFieldSplit = args.get(4).split("[|]+");
                for(String item : abbFieldSplit)
                    if(!Branch.checkInEnum(item)) {
                        Logger.appendMessage("A linha " + args + " tem uma designação de ramo inválida!");
                        return false;
                    }
            } catch (IllegalArgumentException ex) {
                Logger.appendMessage("A linha " + args + " tem uma designação de ramo inválida!" );
                return false;
            }

            if(parseDouble(args.get(5)) < 0D || parseDouble(args.get(5)) > 1D){
                Logger.appendMessage("A linha " + args + " tem uma classificação invalida!" );
                return false;
            }
            if(!isStringValidAsBool(args.get(6))){
                Logger.appendMessage("A linha " + args + " tem uma permissão de estagio invalida!" );
                return false;
            }
        }
        catch(Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s", nrStudent,name,email,
                abbCourse,abbBranch,classification,hasAccess);
    }
    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        Student student = (Student) o;
        return Objects.equals(this.getNrStudent(), student.getNrStudent());
    }
    @Override
    public int hashCode(){
        return this.getNrStudent().intValue();
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public int compareTo(Student o){
        return this.getClassification().compareTo(o.getClassification());
    }
}

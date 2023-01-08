package pt.isec.pa.apoio_poe.src.model.data.student;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.teacher.Teacher;
import pt.isec.pa.apoio_poe.src.utils.FileUtils;

import java.io.*;
import java.util.*;

import static pt.isec.pa.apoio_poe.src.utils.FileUtils.parseStringIntoList;

public class StudentsData implements Serializable, Cloneable {
    private final Map<Long,Student> students;

    public StudentsData(){
        students = new HashMap<>();
    }

    private StudentsData(Map<Long,Student> toCopy){
        this.students = toCopy;
    }

    public Map<Long, Student> getStudents() {
        return this.students;
    }

    public void insertStudentsFromFile(String filename, Collection<Teacher> teachersData) throws Exception {

        try{
            File file = new File(filename);
            List<List<String>> list = FileUtils.importCSVFileAsListOfList(file);

            StudentsData tempInsert = new StudentsData();

            for(List<String> listStudent : list){
                if(!Student.isListValidAsStudent(listStudent)){
                    continue;
                }

                Student temp = new Student(listStudent);

                if(isValidInsertStudent(temp, teachersData) && tempInsert.isValidInsertStudent(temp, teachersData)){
                    students.put(temp.getNrStudent(),temp);
                }
            }


            students.putAll(tempInsert.students);
        }
        catch (Exception e){
            throw new Exception("Erro a importar ficheiro CSV!");
        }

    }

    public void insertStudent(String student, Collection<Teacher> teachersData) throws Exception {

        List<String> temp = parseStringIntoList(student);

        if(!Student.isListValidAsStudent(temp))
            throw new Exception("O Estudante nao e valido.");

        Student studentToInsert = new Student(temp);

        if(isValidInsertStudent(studentToInsert, teachersData)){
            students.put(studentToInsert.getNrStudent(),studentToInsert);
        }
        else
            throw new Exception("O Estudante nao e valido.");
    }


    public void removeStudent(Student student) throws Exception {
        if(isValidRemoveStudent(student)){
            students.remove(student.getNrStudent(),student);
        }
        else
            throw new Exception("O aluno nao pode ser removido.");
    }

    private boolean isValidInsertStudent(Student student, Collection<Teacher> teachersData){
        if(students.containsKey(student.getNrStudent())){
            Logger.appendMessage("O estudante " + student + " nao existe ou tem um numero de estudante invalido!" );
            return false;
        }

        for (Student temp : students.values()) {
            if (temp.getEmail().equals(student.getEmail())){
                Logger.appendMessage("O estudante " + temp + " tem um email invalido!" );
                return false;
            }
            for (Teacher tempTeacher : teachersData) {
                if (tempTeacher.getEmail().equals(student.getEmail())) {
                    Logger.appendMessage("O estudante " + temp + " tem um email invalido!");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidRemoveStudent(Student student){
        if(!students.containsKey(student.getNrStudent())){
            Logger.appendMessage("O aluno " + student.getName() + " tem um numero de estudante invalido!" );
            return false;
        }
        return true;
    }

    public Student findStudent(Long studentKey){
        if(this.students.containsKey(studentKey))
            return this.students.get(studentKey);

        return null;
    }

    public Map<Long, Student> getStudentsCopy() {
        StudentsData copy = (StudentsData) this.clone();

        return copy.getStudents();
    }

    public int countStudents(String type){

        // Se nao for pedido nenhum tipo especifico, devolve o nr. total de alunos
        if(type == null)
            return students.values().size();

        int count = 0;
        List<Student> list = new ArrayList<>(students.values());
        for(Student tempStudent : list){
            String [] abbFieldSplit = tempStudent.getAbbBranch().split("[|]+");
            if(Arrays.asList(abbFieldSplit).contains(type))
                count++;
        }
        return count;
    }

    public List<Student> toList() {
        List<Student> list = new ArrayList<>(students.values());
        list.sort(Collections.reverseOrder());

        return list;
    }

    public void exportStudentsToFile(String filename) throws Exception {
        try(Writer writer = new FileWriter(filename)) {
            for (Map.Entry<Long, Student> entry : this.getStudentsCopy().entrySet())
                writer.append(entry.getValue().toString()).append("\r\n");
        } catch (IOException ex) {
            Logger.log("Erro a exportar ficheiro CSV");
            throw new Exception("Erro a exportar ficheiro CSV");
        }
    }

    @Override
    public String toString(){
        if(this.toList().isEmpty())
            return null;

        StringBuilder sb = new StringBuilder();
        for (Object o : this.toList())
            sb.append("\n\t-").append(o.toString());

        return sb.toString();
    }

    @Override
    public Object clone() {
        HashMap<Long,Student> newHashmap = new HashMap<>();
        for (Map.Entry<Long,Student> entry : this.getStudents().entrySet()) {
            newHashmap.put(entry.getKey(), new Student(entry.getValue()));
        }
        return new StudentsData(newHashmap);
    }
}

package pt.isec.pa.apoio_poe.src.model.data.teacher;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.utils.FileUtils;

import java.io.*;
import java.util.*;

import static pt.isec.pa.apoio_poe.src.utils.FileUtils.parseStringIntoList;

public class TeachersData implements Serializable, Cloneable {
    private final Map<String,Teacher> teachers;

    public TeachersData(){
        teachers = new HashMap<>();
    }

    private TeachersData(Map<String,Teacher> toCopy){
        this.teachers = toCopy;
    }

    public Map<String, Teacher> getTeachers() {
        return this.teachers;
    }

    public void insertTeacher(Teacher teacher){
        if(isValidInsertTeacher(teacher)){
            teachers.put(teacher.getEmail(),teacher);
        }
    }

    public void insertTeacher(String teacher) throws Exception {

        List<String> temp = parseStringIntoList(teacher);

        if(!Teacher.isListValidAsTeacher(temp))
            throw new Exception("O Docente nao e valido.");

        Teacher teacherToInsert = new Teacher(temp);

        if(isValidInsertTeacher(teacherToInsert)){
            insertTeacher(teacherToInsert);
        }
        else
            throw new Exception("O Estudante nao e valido.");
    }

    public void insertTeachersFromFile(String filename) throws Exception {
        File file = new File(filename);
        List<List<String>> list = FileUtils.importCSVFileAsListOfList(file);

        TeachersData tempInsert = new TeachersData();

        for(List<String> listTeacher : list){
            if(!Teacher.isListValidAsTeacher(listTeacher)){
                continue;
            }

            Teacher temp = new Teacher(listTeacher);

            if(isValidInsertTeacher(temp) && tempInsert.isValidInsertTeacher(temp)){
                tempInsert.insertTeacher(temp);
            }
        }

        try{
            teachers.putAll(tempInsert.teachers);
        } catch (Exception ex) {
            Logger.logError("Erro a importar ficheiro CSV: " + ex);
            throw new Exception("Erro a importar ficheiro CSV");
        }

    }

    public void removeTeacher(Teacher teacher) throws Exception {
        if(isValidRemoveTeacher(teacher)){
            teachers.remove(teacher.getEmail(), teacher);
        }
        else
            throw new Exception("O docente nao pode ser removido.");
    }

    private boolean isValidInsertTeacher(Teacher teacher){
        if(teachers.containsKey(teacher.getEmail())){
            Logger.appendMessage("O docente " + teacher + " tem um email invalido!" );
            return false;
        }
        return true;
    }

    private boolean isValidRemoveTeacher(Teacher teacher){
        if(!teachers.containsKey(teacher.getEmail())){
            Logger.appendMessage("O docente " + teacher.getName() + " nao existe ou tem um email invalido!" );
            return false;
        }
        return true;
    }

    public Teacher findTeacher(String teacherKey){
        if(this.teachers.containsKey(teacherKey))
            return this.teachers.get(teacherKey);

        return null;
    }

    public List<Teacher> toList() {
        List<Teacher> list = new ArrayList<>(teachers.values());
        list.sort(Collections.reverseOrder());

        return list;
    }

    public Map<String,Teacher> getTeachersCopy() {
        TeachersData copy = (TeachersData) this.clone();
        return copy.getTeachers();
    }

    public void exportTeachersToFile(String filename) throws Exception {
        try(Writer writer = new FileWriter(filename)) {
            for (Map.Entry<String,Teacher> entry : this.getTeachersCopy().entrySet()){
                writer.append(entry.getValue().toString()).append("\r\n");
            }
        } catch (IOException ex) {
            Logger.logError("Erro a exportar ficheiro CSV: " + ex);
            throw new IOException("Erro a exportar ficheiro CSV");
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
        HashMap<String, Teacher> newHashmap = new HashMap<>();
        for (Map.Entry<String, Teacher> entry : this.getTeachers().entrySet()) {
            newHashmap.put(entry.getKey(), new Teacher(entry.getValue()));
        }
        return new TeachersData(newHashmap);
    }
}

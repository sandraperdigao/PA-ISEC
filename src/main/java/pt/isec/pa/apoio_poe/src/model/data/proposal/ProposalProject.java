package pt.isec.pa.apoio_poe.src.model.data.proposal;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.enums.Branch;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;
import pt.isec.pa.apoio_poe.src.model.data.teacher.Teacher;

import java.util.List;

import static java.lang.Long.parseLong;

public class ProposalProject extends ProposalAdapter {

    private final String teacherEmail;

    public ProposalProject(ProposalProject prop){
        super(prop.type, prop.code, prop.title);
        this.abbField = prop.abbField;
        this.teacherEmail = prop.teacherEmail;
        setNrStudent(getNrStudent());
    }

    public ProposalProject(List<String> args){
        super(args.get(0), args.get(1), args.get(2),args.get(3));
        this.teacherEmail = args.get(4);
        if(args.size() > 5){
            setNrStudent(parseLong(args.get(5)));
        }
    }

    @Override
    public String getTeacherEmail() {
        return this.teacherEmail;
    }
    public static boolean isListValidAsProposal(List<String> args, List<Teacher> tdata, List<Student> sdata) {

        try{
            String [] abbFieldSplit = args.get(2).split("[|]+");
            for(String item : abbFieldSplit)
                Branch.valueOf(item.toUpperCase());
        } catch (IllegalArgumentException ex) {
            Logger.appendMessage("A linha " + args + " tem uma designação de ramo(s) inválida!" );
            return false;
        }

        if(tdata.stream().noneMatch(s -> s.getEmail().equals(args.get(4)))) {
            Logger.appendMessage("A linha " + args + " tem um email inválido!" );
            return false;
        }

        if (args.size() != 5 && args.size() != 6) {
            Logger.appendMessage("A linha " + args + " tem um número de valores inválido!" );
            return false;
        }

        try{
            if(args.size() == 6 && args.get(5) != null)
                if(sdata.stream().map(Student::getNrStudent).toString().equals(args.get(5))){
                    Logger.appendMessage("A linha " + args + " em um número de estudante inválido!" );
                    return false;
                }
        }
        catch(Exception ex) {
            Logger.appendMessage("A linha " + args + " tem um número de estudante inválido!" );
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return type + "," + code + "," + abbField + "," + title + "," + teacherEmail + "," + getNrStudent();
    }
}

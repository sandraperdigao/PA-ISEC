package pt.isec.pa.apoio_poe.src.model.data.proposal;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;

import java.util.List;

import static java.lang.Long.parseLong;

public class ProposalAuto extends ProposalAdapter {

    public ProposalAuto(ProposalAuto prop){
        super(prop.type,prop.code,prop.title);
        setNrStudent(getNrStudent());
    }

    public ProposalAuto(List<String> args){
        super(args.get(0), args.get(1), args.get(2));
        setNrStudent(parseLong(args.get(3)));
    }
    public static boolean isListValidAsProposal(List<String> args, List<Student> sdata) {
        if (args.size() != 4) {
            Logger.appendMessage("A linha " + args + " tem um número de valores inválido!" );
            return false;
        }

        try{
            if(sdata.stream().map(Student::getNrStudent).toString().equals(args.get(3))){
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
        return type + "," + code + "," + title + "," + getNrStudent();
    }
}

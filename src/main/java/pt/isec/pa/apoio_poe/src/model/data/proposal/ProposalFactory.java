package pt.isec.pa.apoio_poe.src.model.data.proposal;

import pt.isec.pa.apoio_poe.src.model.data.student.Student;
import pt.isec.pa.apoio_poe.src.model.data.teacher.Teacher;

import java.util.List;

public class ProposalFactory {

    public static ProposalAdapter builder (List<String> list) {
        switch (list.get(0)){
            case "T1" -> {return new ProposalInternship(list);}
            case "T2" -> {return new ProposalProject(list);}
            case "T3" -> {return new ProposalAuto(list);}
        }
        return null;
    }

    public static ProposalAdapter builder (ProposalAdapter prop) {
        switch (prop.getType()){
            case "T1" -> {return new ProposalInternship((ProposalInternship) prop);}
            case "T2" -> {return new ProposalProject((ProposalProject) prop);}
            case "T3" -> {return new ProposalAuto((ProposalAuto) prop);}
        }
        return null;
    }

    public static boolean validator(List<String> list, List<Teacher> teacherList, List<Student> studentList){
        switch (list.get(0)) {
            case "T1" -> {return ProposalInternship.isListValidAsProposal(list);}
            case "T2" -> {return ProposalProject.isListValidAsProposal(list, teacherList, studentList);}
            case "T3" -> {return ProposalAuto.isListValidAsProposal(list, studentList);}
        }
        return false;
    }
}

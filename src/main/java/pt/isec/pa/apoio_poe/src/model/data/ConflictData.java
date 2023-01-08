package pt.isec.pa.apoio_poe.src.model.data;

import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConflictData implements Serializable {
    ProposalAdapter proposal;
    List<Student> studentList;

    public ConflictData(){
        proposal = null;
        studentList = new ArrayList<>();
    }

    public ProposalAdapter getProposal() {
        return proposal;
    }

    public void setProposal(ProposalAdapter proposal) {
        this.proposal = proposal;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void clear(){
        proposal = null;
        studentList = new ArrayList<>();
    }
}

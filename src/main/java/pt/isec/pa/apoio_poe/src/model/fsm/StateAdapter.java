package pt.isec.pa.apoio_poe.src.model.fsm;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;

import java.io.Serializable;
import java.util.List;

public abstract class StateAdapter implements IState, Serializable {
    protected Context context;
    protected DataManager data;

    protected StateAdapter(Context context, DataManager data) {
        this.context = context;
        this.data = data;
    }

    final protected void changeState(StateEnum stateEnum, DataManager data){
        context.changeState(stateEnum, data);
        Logger.log("Changed state to " + stateEnum + ".");
    }

    @Override
    public void forward() {
    }
    @Override
    public void backward(){
    }
    @Override
    public void closePhase() throws Exception {}
    @Override
    public boolean verifyCloseable(){return false;}

    //Common Btw Some Phases
    @Override
    public String listStudentsAuto(){return null;}
    @Override
    public String listStudentsApplied(){return null;}
    @Override
    public String listStudentsNotApplied(){return null;}
    @Override
    public String listStudentsAttributed(){return null;}
    @Override
    public String listStudentsNotAttributed(){return null;}
    @Override
    public String listProposalsNotAttributed(){return null;}

    //Initial
    @Override
    public void loadLastSession() throws Exception {}

    //Phase1
    @Override
    public void manageStudents(){}
    @Override
    public void manageTeachers(){}
    @Override
    public void manageProposals(){}

    //Phase1Students
    @Override
    public void insertStudentsFromFile(String filename) throws Exception {}
    @Override
    public void exportStudentsToFile(String filename) throws Exception {}
    @Override
    public String getStudentsAsString(){return null;}

    public void manualInsertStudent(String readString) throws Exception {}

    public void manualUpdateStudent(String readString) {}

    public void manualRemoveStudent(String readString) throws Exception {}

    public void removeAllStudents() throws Exception {}

    //Phase1Teachers
    @Override
    public void insertTeachersFromFile(String filename) throws Exception {}
    @Override
    public void exportTeachersToFile(String filename) throws Exception {}
    @Override
    public String getTeachersAsString(){return null;}
    @Override
    public void manualInsertTeacher(String readString) throws Exception {}
    @Override
    public void manualUpdateTeacher(String readString) {}
    @Override
    public void manualRemoveTeacher(String readString) throws Exception {}
    @Override
    public void removeAllTeachers() throws Exception {}


    //Phase1Proposals
    @Override
    public void insertProposalsFromFile(String filename) throws Exception {}
    @Override
    public void exportProposalsToFile(String filename) throws Exception {}
    @Override
    public String getProposalsAsString(){return null;}

    @Override
    public void manualInsertProposal(String readString) throws Exception {}

    @Override
    public void manualUpdateProposal(String readString) {}

    @Override
    public void manualRemoveProposal(String readString) throws Exception {}

    @Override
    public void removeAllProposals() throws Exception {}

    //Phase2
    @Override
    public void insertApplicationsFromFile(String filename) throws Exception {}
    @Override
    public void exportApplicationsToFile(String filename) throws Exception {}
    @Override
    public String listApplications(){return null;}
    @Override
    public String listProposalsFilteredPhase2(boolean [] filters) {return null;}
    @Override
    public void manualInsertApplication(String readString) throws Exception {}

    @Override
    public void manualUpdateApplication(String readString) {}

    @Override
    public void manualRemoveApplication(String readString) throws Exception {}

    @Override
    public void removeAllApplications() {}

    //Phase3
    @Override
    public void autoAssignProposalsStudents() throws Exception {}
    @Override
    public boolean autoAttributionStudent() throws Exception {return false;}
    @Override
    public List<Student> listStudentsEligible(ProposalAdapter proposal) {return null;}
    @Override
    public void insertAttribution(ProposalAdapter prop, Student student) throws Exception {}
    @Override
    public ProposalAdapter getRandomUnassignedProposal(boolean hasAccess) {return null;}
    @Override
    public String listProposalsFilteredPhase3and5(boolean [] filters) {return null;}

    //Phase 3 Conflict
    @Override
    public void resolveConflict(int option) throws Exception {}
    @Override
    public ProposalAdapter getConflictProposal(){return null;}
    @Override
    public String[] getConflictStudents(){return null;}
    @Override
    public void manualAssignAttribution(String nrStudentAsString, String codeProposal) throws Exception {}
    @Override
    public void manualUnassignAttribution(String nrStudentAsString, String codeProposal) throws Exception {}
    @Override
    public void removeAllAttributions() throws Exception {}

    //Phase4
    @Override
    public void autoAssignProposalsTeachers() {}
    @Override
    public String listSupervisors() {return null;}
    @Override
    public String listStudentsAssignedWithSupervisor(){return null;}
    @Override
    public String listStudentsAssignedWithoutSupervisor(){return null;}
    @Override
    public String listSupervisorStats() throws CloneNotSupportedException {return null;}
    @Override
    public void manualAssignSupervisorAttribution(String teacherEmail, String codeProposal) throws Exception {}
    @Override
    public void manualUnassignSupervisorAttribution(String teacherEmail, String codeProposal) throws Exception {}
    @Override
    public void removeAllSupervisorAttributions() throws Exception {}

    //Phase 5
    @Override
    public String listStudentsNotAttributedWithApplications(){return null;}

    @Override
    public void undoPhase3() throws Exception {}
    @Override
    public void redoPhase3() {}
    @Override
    public void undoPhase4() throws Exception {}
    @Override
    public void redoPhase4() {}
}

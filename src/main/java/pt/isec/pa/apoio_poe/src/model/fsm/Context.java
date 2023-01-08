package pt.isec.pa.apoio_poe.src.model.fsm;

import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class Context implements Serializable{

    private DataManager data;
    private IState state;

    public Context() {
        data = new DataManager();
        state = StateEnum.INITIAL.createState(this,data);
    }

    public StateEnum getState() {return state.getState();}
    public DataManager getData(){return this.data;}
    public List<String> getList() throws Exception {return data.getList();}

    //COMMON BTW PHASES
    public void changeState(StateEnum stateEnum, DataManager dataManager){state = stateEnum.createState(this, dataManager);}
    public void closePhase() throws Exception {state.closePhase();}
    public void forward() {state.forward();}
    public void backward() {state.backward();}
    public String listStudentsAuto(){return state.listStudentsAuto();}
    public String listStudentsApplied(){return state.listStudentsApplied();}
    public String listStudentsNotApplied(){return state.listStudentsNotApplied();}
    public String listStudentsAttributed() {return state.listStudentsAttributed();}  // PHASE 3 & 5
    public String listProposalsFilteredPhase3and5(boolean [] filters) {return state.listProposalsFilteredPhase3and5(filters);}  // PHASE 3 & 5
    public String listSupervisorStats() throws CloneNotSupportedException {return state.listSupervisorStats();}  // PHASE 4 & 5

    // INITIAL
    public void loadLastSession() throws Exception {state.loadLastSession();}

    //PHASE 1
    public void manageStudents(){state.manageStudents();}
    public void manageTeachers(){state.manageTeachers();}
    public void manageProposals(){state.manageProposals();}
    //PHASE 1 STUDENTS
    public void insertStudentsFromFile(String filename) throws Exception {state.insertStudentsFromFile(filename);}
    public void exportStudentsToFile(String filename) throws Exception {state.exportStudentsToFile(filename);}
    public void manualInsertStudent(String readString) throws Exception {state.manualInsertStudent(readString);}
    public void manualUpdateStudent(String readString) {state.manualUpdateStudent(readString);}
    public void manualRemoveStudent(String readString) throws Exception {state.manualRemoveStudent(readString);}
    public void removeAllStudents() throws Exception {state.removeAllStudents();}
    public String getStudentsAsString(){return state.getStudentsAsString();}
    //PHASE 1 TEACHERS
    public void insertTeachersFromFile(String filename) throws Exception {state.insertTeachersFromFile(filename);}
    public void exportTeachersToFile(String filename) throws Exception {state.exportTeachersToFile(filename);}
    public String getTeachersAsString(){return state.getTeachersAsString();}
    public void manualInsertTeacher(String readString) throws Exception { state.manualInsertTeacher(readString);}
    public void manualUpdateTeacher(String readString) { state.manualUpdateTeacher(readString);}
    public void manualRemoveTeacher(String readString) throws Exception { state.manualRemoveTeacher(readString);}
    public void removeAllTeachers() throws Exception { state.removeAllTeachers();}
    //PHASE 1 PROPOSALS
    public void insertProposalsFromFile(String filename) throws Exception {state.insertProposalsFromFile(filename);}
    public void exportProposalsToFile(String filename) throws Exception {state.exportProposalsToFile(filename);}
    public String getProposalsAsString(){return state.getProposalsAsString();}
    public void manualInsertProposal(String readString) throws Exception {state.manualInsertProposal(readString);}
    public void manualUpdateProposal(String readString) {state.manualUpdateProposal(readString);}
    public void manualRemoveProposal(String readString) throws Exception {state.manualRemoveProposal(readString);}
    public void removeAllProposals() throws Exception {state.removeAllProposals();}

    //PHASE 2
    public void insertApplicationsFromFile(String filename) throws Exception {state.insertApplicationsFromFile(filename);}
    public void exportApplicationsToFile(String filename) throws Exception {state.exportApplicationsToFile(filename);}
    public String listApplications(){return state.listApplications();}
    public String listProposalsFilteredPhase2(boolean [] filters) {return state.listProposalsFilteredPhase2(filters);}
    public void manualInsertApplication(String readString) throws Exception {state.manualInsertApplication(readString);}
    public void manualUpdateApplication(String readString) {state.manualUpdateApplication(readString);}
    public void manualRemoveApplication(String readString) throws Exception {state.manualRemoveApplication(readString);}
    public void removeAllApplications() {state.removeAllApplications();}

    //PHASE 3
    public void autoAssignProposalsStudents() throws Exception {state.autoAssignProposalsStudents();}
    public void autoAttributionStudent() throws Exception {
        if(state.autoAttributionStudent())
            changeState(StateEnum.PHASE3_CONFLICT, data);
    }

    public void insertAttribution(ProposalAdapter prop, Student student) throws Exception {state.insertAttribution(prop, student);}
    public String listStudentsNotAttributed() {return state.listStudentsNotAttributed();}
    public String listProposalsNotAttributed() {return state.listProposalsNotAttributed();}
    public void exportAttributionsToFile(String filename) {data.exportAttributionsToFile(filename);}
    public void manualAssignAttribution(String nrStudentAsString, String codeProposal) throws Exception { state.manualAssignAttribution(nrStudentAsString, codeProposal);}
    public void manualUnassignAttribution(String nrStudentAsString, String codeProposal) throws Exception {state.manualUnassignAttribution(nrStudentAsString, codeProposal);}
    public void removeAllAttributions() throws Exception {state.removeAllAttributions();}

    //PHASE 3 CONFLICT
    public void resolveConflict(int option) throws Exception {
        state.resolveConflict(option);
        changeState(StateEnum.PHASE3,data);
    }
    public ProposalAdapter getConflictProposal() {return state.getConflictProposal();}
    public String[] getConflictStudents() {return state.getConflictStudents();}

    //PHASE 4
    public void autoAssignProposalsTeachers(){ state.autoAssignProposalsTeachers();}
    public String listSupervisors(){return state.listSupervisors();}
    public String listStudentsAssignedWithSupervisor(){return state.listStudentsAssignedWithSupervisor();}
    public String listStudentsAssignedWithoutSupervisor(){return state.listStudentsAssignedWithoutSupervisor();}
    public void exportSupervisorAttributionsToFile(String filename) { data.exportSupervisorAttributionsToFile(filename);} // PHASE 4 & 5
    public void manualAssignSupervisorAttribution(String teacherEmail, String codeProposal) throws Exception {state.manualAssignSupervisorAttribution(teacherEmail, codeProposal);}
    public void manualUnassignSupervisorAttribution(String nrStudentAsString, String teacherEmail) throws Exception {state.manualUnassignSupervisorAttribution(nrStudentAsString, teacherEmail);}
    public void removeAllSupervisorAttributions() throws Exception {state.removeAllSupervisorAttributions();}
    //PHASE 5
    public String listStudentsNotAttributedWithApplications(){return state.listStudentsNotAttributedWithApplications();}

    //PHASE 5 UI
    public int getNumberProposalAttributionPerAbbField(String abbField){return data.getNumberProposalAttributionPerAbbField(abbField);}
    public int getNumberProposalsAttributed(){return data.getNumberProposalsAttributed();}
    public int getNumberProposalsAvailable(){return data.getNumberProposalsAvailable();}
    public LinkedHashMap<String,Integer> getTeachersOrderedByNumberOfProposalsAttributed() throws CloneNotSupportedException {return data.getTeachersOrderedByNumberOfProposalsAttributed();}
    public LinkedHashMap<String, Integer> getCompaniesOrderedByNumberOfInternshipsAttributed() throws CloneNotSupportedException {return data.getCompaniesOrderedByNumberOfInternshipsAttributed();}

    //FLAGS
    public boolean isState1Closed(){return data.isClosedPhase1();}
    public boolean isState2Closed(){return data.isClosedPhase2();}
    public boolean isState3Closed(){return data.isClosedPhase3();}
    public boolean isState4Closed(){return data.isClosedPhase4();}

    public void undoPhase3() throws Exception { state.undoPhase3();}
    public void redoPhase3() throws Exception { state.redoPhase3();}
    public void undoPhase4() throws Exception { state.undoPhase4();}
    public void redoPhase4() throws Exception { state.undoPhase4();}
}

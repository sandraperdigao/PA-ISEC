package pt.isec.pa.apoio_poe.src.model.fsm;

import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;

import java.util.List;

public interface IState {
    StateEnum getState();

    void forward();
    void backward();

    void closePhase() throws Exception;
    boolean verifyCloseable();

    String listStudentsAuto();
    String listStudentsApplied();
    String listStudentsNotApplied();
    String listStudentsAttributed();
    String listStudentsNotAttributed();
    String listProposalsNotAttributed();
    void loadLastSession() throws Exception;
    void manageStudents();
    void manageTeachers();
    void manageProposals();
    void insertStudentsFromFile(String filename) throws Exception;
    void exportStudentsToFile(String filename) throws Exception;
    String getStudentsAsString();
    void insertTeachersFromFile(String filename) throws Exception;
    void exportTeachersToFile(String filename) throws Exception;
    String getTeachersAsString();
    void insertProposalsFromFile(String filename) throws Exception;
    void exportProposalsToFile(String filename) throws Exception;
    String getProposalsAsString();
    void insertApplicationsFromFile(String filename) throws Exception;
    void exportApplicationsToFile(String filename) throws Exception;
    String listApplications();
    String listProposalsFilteredPhase2(boolean [] filters);
    void autoAssignProposalsStudents() throws Exception;
    boolean autoAttributionStudent() throws Exception;

    List<Student> listStudentsEligible(ProposalAdapter proposal);
    void insertAttribution(ProposalAdapter prop, Student student) throws Exception;
    ProposalAdapter getRandomUnassignedProposal(boolean hasAccess);
    String listProposalsFilteredPhase3and5(boolean [] filters);
    void autoAssignProposalsTeachers();
    String listSupervisors();
    String listStudentsAssignedWithSupervisor();
    String listStudentsAssignedWithoutSupervisor();
    String listSupervisorStats() throws CloneNotSupportedException;
    String listStudentsNotAttributedWithApplications();

    void resolveConflict(int option) throws Exception;
    ProposalAdapter getConflictProposal();
    String[] getConflictStudents();

    void manualInsertStudent(String readString) throws Exception;
    void manualUpdateStudent(String readString);
    void manualRemoveStudent(String readString) throws Exception;
    void removeAllStudents() throws Exception;

    void manualInsertProposal(String readString) throws Exception;
    void manualUpdateProposal(String readString);
    void manualRemoveProposal(String readString) throws Exception;
    void removeAllProposals() throws Exception;

    void manualInsertTeacher(String readString) throws Exception;
    void manualUpdateTeacher(String readString);
    void manualRemoveTeacher(String readString) throws Exception;
    void removeAllTeachers() throws Exception;

    void manualInsertApplication(String readString) throws Exception;
    void manualUpdateApplication(String readString);
    void manualRemoveApplication(String readString) throws Exception;
    void removeAllApplications();

    void manualAssignAttribution(String nrStudentAsString, String codeProposal) throws Exception;
    void manualUnassignAttribution(String nrStudentAsString, String codeProposal) throws Exception;
    void removeAllAttributions() throws Exception;

    void manualAssignSupervisorAttribution(String teacherEmail, String codeProposal) throws Exception;
    void manualUnassignSupervisorAttribution(String nrStudentAsString, String teacherEmail) throws Exception;
    void removeAllSupervisorAttributions() throws Exception;

    void undoPhase3() throws Exception;
    void redoPhase3();
    void undoPhase4() throws Exception;
    void redoPhase4();
}

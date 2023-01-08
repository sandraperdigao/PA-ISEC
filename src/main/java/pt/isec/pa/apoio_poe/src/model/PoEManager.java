package pt.isec.pa.apoio_poe.src.model;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;

public class PoEManager {

    private static final String SERDES_FILENAME = "savedContext.dat";
    private static PoEManager instance = null;
    private Context fsm;
    PropertyChangeSupport pcs;

    public static PoEManager getInstance(boolean testEnv) throws Exception {

        if (instance == null){
            instance = new PoEManager(testEnv);
        }

        return instance;
    }

    private PoEManager(boolean testEnv) throws Exception {
        this.fsm = new Context();
        this.pcs = new PropertyChangeSupport(this);
        if(testEnv)
            this.testEnvironment();
    }

    public Context getContext(){
        return fsm;
    }

    public void testEnvironment() throws Exception {

        String localNuno = "C:\\DEVROOT\\trabalho-pa-gpp\\src\\main\\java\\pt\\isec\\pa\\apoio_poe\\src\\";
        String localSandra = "C:\\Users\\sandr\\IdeaProjects\\trabalho-pa-gpp - Cópia\\src\\main\\java\\pt\\isec\\pa\\apoio_poe\\src\\";

        fsm.changeState(StateEnum.PHASE1, fsm.getData());
        fsm.changeState(StateEnum.PHASE1_STUDENTS, fsm.getData());
        fsm.insertStudentsFromFile(localNuno + "testStudents.csv");
        fsm.changeState(StateEnum.PHASE1_TEACHERS, fsm.getData());
        fsm.insertTeachersFromFile(localNuno + "testTeachers.csv");
        fsm.changeState(StateEnum.PHASE1_PROPOSALS, fsm.getData());
        fsm.insertProposalsFromFile(localNuno + "testProposals.csv");
        fsm.changeState(StateEnum.PHASE1, fsm.getData());
        fsm.closePhase();
        fsm.changeState(StateEnum.PHASE2, fsm.getData());
        fsm.insertApplicationsFromFile(localNuno + "testApplications.csv");
        fsm.closePhase();
        fsm.changeState(StateEnum.PHASE2, fsm.getData());
    }

    public void saveState(Context context) throws IOException {
        try {
            FileOutputStream file = new FileOutputStream(SERDES_FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(context);
            out.close();
            file.close();
            Logger.log("Context serializado com sucesso.");
        }
        catch(IOException ex) {
            Logger.logError("IOException: " + ex);
            throw new IOException(ex);
        }
    }

    public Context loadState() throws IOException, ClassNotFoundException {
        try {
            FileInputStream file = new FileInputStream(SERDES_FILENAME);
            ObjectInputStream in = new ObjectInputStream(file);
            Context context = (Context) in.readObject();
            in.close();
            file.close();
            Logger.log("Context desserializado com sucesso.");
            return context;
        } catch(IOException ex) {
            Logger.logError("IOException: " + ex);
            throw new IOException(ex);
        } catch(ClassNotFoundException ex) {
            Logger.logError("ClassNotFoundException" + ex);
            throw new ClassNotFoundException(ex.toString());
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void forward() {
        fsm.forward();
        pcs.firePropertyChange(null,null,null);
    }

    public void backward() {
        fsm.backward();
        pcs.firePropertyChange(null,null,null);
    }

    public void loadLastSession() throws Exception {
        fsm.loadLastSession();
        pcs.firePropertyChange(null,null,null);
    }

    public List<String> getList() throws Exception {return fsm.getList();}

    public void manageStudents(){
        fsm.manageStudents();
        pcs.firePropertyChange(null,null,null);
    }
    public void manageTeachers(){
        fsm.manageTeachers();
        pcs.firePropertyChange(null,null,null);
    }
    public void manageProposals(){
        fsm.manageProposals();
        pcs.firePropertyChange(null,null,null);
    }
    public void closePhase() throws Exception {
        fsm.closePhase();
        pcs.firePropertyChange(null,null,null);
    }

    public boolean isState1Closed(){
        return fsm.isState1Closed();
    }

    public boolean isState2Closed(){
        return fsm.isState2Closed();
    }

    public boolean isState3Closed(){
        return fsm.isState3Closed();
    }

    public boolean isState4Closed(){return fsm.isState4Closed();}

    public void insertStudentsFromFile(String filename) throws Exception {
        fsm.insertStudentsFromFile(filename);
        pcs.firePropertyChange(null,null,null);
    }

    public void insertTeachersFromFile(String filename) throws Exception {
        fsm.insertTeachersFromFile(filename);
        pcs.firePropertyChange(null,null,null);
    }

    public void insertProposalsFromFile(String filename) throws Exception {
        fsm.insertProposalsFromFile(filename);
        pcs.firePropertyChange(null,null,null);
    }

    public void exportStudentsToFile(String filename) throws Exception {
        fsm.exportStudentsToFile(filename);
    }

    public void exportTeachersToFile(String filename) throws Exception {
        fsm.exportTeachersToFile(filename);
    }

    public void exportProposalsToFile(String filename) throws Exception {
        fsm.exportProposalsToFile(filename);
    }

    public String getStudentsAsString(){
        return fsm.getStudentsAsString();
    }

    public String getTeachersAsString(){
        return fsm.getTeachersAsString();
    }

    public String getProposalsAsString(){
        return fsm.getProposalsAsString();
    }

    public String listStudentsAuto(){return fsm.listStudentsAuto();}

    public String listProposalsFilteredPhase2(boolean [] filters){return fsm.listProposalsFilteredPhase2(filters);}

    public String listApplications(){return fsm.listApplications();}

    public String listStudentsApplied(){return fsm.listStudentsApplied();}

    public String listStudentsNotApplied(){return fsm.listStudentsNotApplied();}

    public void insertApplicationsFromFile(String filename) throws Exception {
        fsm.insertApplicationsFromFile(filename);
        pcs.firePropertyChange(null,null,null);
    }

    public void exportApplicationsToFile(String filename) throws Exception {
        fsm.exportApplicationsToFile(filename);
    }

    public void autoAssignProposalsStudents() throws Exception {
        fsm.autoAssignProposalsStudents();
        pcs.firePropertyChange(null,null,null);
    }

    public void autoAttributionStudent() throws Exception {
        fsm.autoAttributionStudent();
        pcs.firePropertyChange(null,null,null);
    }

    public void manualAssignAttribution(String nrStudentAsString, String codeProposal) throws Exception {
        fsm.manualAssignAttribution(nrStudentAsString,codeProposal);
        pcs.firePropertyChange(null,null,null);
    }

    public void manualUnassignAttribution(String nrStudentAsString, String codeProposal) throws Exception {
        fsm.manualUnassignAttribution(nrStudentAsString,codeProposal);
        pcs.firePropertyChange(null,null,null);
    }

    public void removeAllAttributions() throws Exception {
        fsm.removeAllAttributions();
        pcs.firePropertyChange(null,null,null);
    }

    public void exportAttributionsToFile(String filename) throws Exception {
        fsm.exportTeachersToFile(filename);
    }

    public String listProposalsFilteredPhase3and5(boolean [] filters){return fsm.listProposalsFilteredPhase3and5(filters);}

    public String listStudentsAttributed(){return fsm.listStudentsAttributed();}

    public String listStudentsNotAttributed(){return fsm.listStudentsNotAttributed();}

    public ProposalAdapter getConflictProposal(){return fsm.getConflictProposal();}

    public String[] getConflictStudents(){return fsm.getConflictStudents();}

    public void resolveConflict(int option) throws Exception {
        fsm.resolveConflict(option);
        pcs.firePropertyChange(null,null,null);
    }

    public String listSupervisors(){return fsm.listSupervisors();}

    public String listStudentsAssignedWithSupervisor(){return fsm.listStudentsAssignedWithSupervisor();}

    public String listStudentsAssignedWithoutSupervisor(){return fsm.listStudentsAssignedWithoutSupervisor();}

    public String listSupervisorStats() throws CloneNotSupportedException {return fsm.listSupervisorStats();}

    public void autoAssignProposalsTeachers(){
        fsm.autoAssignProposalsTeachers();
        pcs.firePropertyChange(null,null,null);
    }

    public void manualAssignTeacher(String nrStudentAsString, String teacherEmail) throws Exception {
        fsm.manualAssignSupervisorAttribution(nrStudentAsString, teacherEmail);
        pcs.firePropertyChange(null,null,null);
    }

    public void manualUnassignSupervisorAttribution(String nrStudentAsString, String teacherEmail) throws Exception {
        fsm.manualUnassignSupervisorAttribution(nrStudentAsString, teacherEmail);
        pcs.firePropertyChange(null,null,null);
    }

    public void removeAllSupervisorAttributions() throws Exception {
        fsm.removeAllSupervisorAttributions();
        pcs.firePropertyChange(null,null,null);
    }

    public void exportSupervisorAttributionsToFile(String filename){fsm.exportSupervisorAttributionsToFile(filename);}

    public String listStudentsNotAttributedWithApplications(){return fsm.listStudentsNotAttributedWithApplications();}

    public int getNumberProposalAttributionPerAbbField(String abbField){return fsm.getNumberProposalAttributionPerAbbField(abbField);}

    public int getNumberProposalsAttributed(){return fsm.getNumberProposalsAttributed();}

    public int getNumberProposalsAvailable(){return fsm.getNumberProposalsAvailable();}

    public LinkedHashMap<String,Integer> getTeachersOrderedByNumberOfProposalsAttributed() throws CloneNotSupportedException {return fsm.getTeachersOrderedByNumberOfProposalsAttributed();}

    public LinkedHashMap<String, Integer> getCompaniesOrderedByNumberOfInternshipsAttributed() throws CloneNotSupportedException {return fsm.getCompaniesOrderedByNumberOfInternshipsAttributed();}

    public void manualInsertStudent(String response) throws Exception {
        fsm.manualInsertStudent(response);
        pcs.firePropertyChange(null,null,null);
    }

    public void manualRemoveStudent(String response) throws Exception{
        fsm.manualRemoveStudent(response);
        pcs.firePropertyChange(null,null,null);
    }

    public void removeAllStudents() throws Exception {
        fsm.removeAllStudents();
        pcs.firePropertyChange(null,null,null);
    }

    public void manualInsertTeacher(String response) throws Exception {
        fsm.manualInsertTeacher(response);
        pcs.firePropertyChange(null,null,null);
    }

    public void manualRemoveTeacher(String response) throws Exception {
        fsm.manualRemoveTeacher(response);
        pcs.firePropertyChange(null,null,null);
    }

    public void removeAllTeachers() throws Exception {
        fsm.removeAllTeachers();
        pcs.firePropertyChange(null,null,null);
    }

    public void manualInsertProposal(String response) throws Exception {
        fsm.manualInsertProposal(response);
        pcs.firePropertyChange(null,null,null);
    }

    public void manualRemoveProposal(String response) throws Exception {
        fsm.manualRemoveProposal(response);
        pcs.firePropertyChange(null,null,null);
    }

    public void removeAllProposals() throws Exception {
        fsm.removeAllProposals();
        pcs.firePropertyChange(null,null,null);
    }

    public StateEnum getState() {
        return fsm.getState();
    }

    public void manualInsertApplication(String response) throws Exception {
        fsm.manualInsertApplication(response);
        pcs.firePropertyChange(null,null,null);
    }

    public void manualRemoveApplication(String response) throws Exception {
        fsm.manualRemoveApplication(response);
        pcs.firePropertyChange(null,null,null);
    }

    public void removeAllApplications() {
        fsm.removeAllApplications();
        pcs.firePropertyChange(null,null,null);
    }

    public void undoPhase3() throws Exception {
        fsm.undoPhase3();
        pcs.firePropertyChange(null,null,null);
    }

    public void redoPhase3() throws Exception {
        fsm.redoPhase3();
        pcs.firePropertyChange(null,null,null);
    }

    public void undoPhase4() throws Exception {
        fsm.undoPhase4();
        pcs.firePropertyChange(null,null,null);
    }

    public void redoPhase4() throws Exception {
        fsm.redoPhase4();
        pcs.firePropertyChange(null,null,null);
    }

}

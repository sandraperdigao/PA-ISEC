package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

import java.util.List;

public class StatePhase3 extends StateAdapter {

    public StatePhase3(Context context, DataManager dataManager) {
        super(context, dataManager);
    }

    @Override
    public StateEnum getState() {
        return StateEnum.PHASE3;
    }
    @Override
    public void forward() {
        changeState(StateEnum.PHASE4, data);
    }
    @Override
    public void backward() {
        changeState(StateEnum.PHASE2, data);
    }

    @Override
    public String getTeachersAsString(){return data.getTeachersAsString();}
    @Override
    public String listStudentsAuto(){return data.listStudentsAuto();}
    @Override
    public String listStudentsApplied(){return data.listStudentsApplied();}
    @Override
    public String listStudentsNotApplied(){return data.listStudentsNotApplied();}
    @Override
    public String listStudentsAttributed(){return data.listStudentsAttributed();}
    @Override
    public String listStudentsNotAttributed() {return data.listStudentsNotAttributed();}
    @Override
    public String listProposalsNotAttributed() {return data.listProposalsNotAttributed();}

    @Override
    public String listProposalsFilteredPhase3and5(boolean [] filters) {return data.listProposalsFilteredPhase3and5(filters);}

    @Override
    public void autoAssignProposalsStudents() throws Exception {data.autoAssignProposalsStudents();}
    @Override
    public boolean autoAttributionStudent() throws Exception {return data.autoAttributionStudent();}
    @Override
    public List<Student> listStudentsEligible(ProposalAdapter proposal) {return data.listStudentsEligible(proposal);}
    @Override
    public ProposalAdapter getRandomUnassignedProposal(boolean hasAccess) {return data.getRandomUnassignedProposal(hasAccess);}
    @Override
    public void insertAttribution(ProposalAdapter prop, Student student) throws Exception {data.insertAttribution(prop, student);}
    @Override
    public boolean verifyCloseable(){
        for(Long temp : data.getAppliedStudentsNrs()){
            Student tempStudent = data.findStudent(temp);
            if (!data.getAttributions().containsValue(tempStudent)){
                return false;
            }
        }
        return true;
    }
    @Override
    public void closePhase() throws Exception {
        try{
            if(verifyCloseable()){
                data.setClosedPhase3();
                Logger.log("Closed Phase 3.");
                changeState(StateEnum.PHASE4, data);
                Logger.log("Changed state to Phase4.");
            }
            else{
                throw new Exception("Não é possível fechar a fase 3: Todos os alunos com candidatura submetida necessitam " +
                        "de ter um projecto atribuido.");
            }
        } catch(Exception ex){
            Logger.logError("Unable to close Phase 3: " + ex);
            throw new Exception("Não foi possível fechar a fase 3.");
        }
    }

    @Override
    public void manualAssignAttribution(String nrStudentAsString, String codeProposal) throws Exception {data.manualAssignAttribution(nrStudentAsString, codeProposal);}
    @Override
    public void manualUnassignAttribution(String nrStudentAsString, String codeProposal) throws Exception {data.manualUnassignAttribution(nrStudentAsString, codeProposal);}
    @Override
    public void removeAllAttributions() throws Exception {data.removeAllAttributions();}

    public void undoPhase3() throws Exception { data.undoPhase3();}
}

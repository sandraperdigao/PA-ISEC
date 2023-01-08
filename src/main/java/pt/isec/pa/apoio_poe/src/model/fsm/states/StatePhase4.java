package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class StatePhase4 extends StateAdapter {

    public StatePhase4(Context context, DataManager dataManager) {
        super(context, dataManager);
    }

    @Override
    public StateEnum getState() {
        return StateEnum.PHASE4;
    }

    @Override
    public void backward() {
        changeState(StateEnum.PHASE3, data);
    }

    @Override
    public String listStudentsAttributed(){return data.listStudentsAttributed();}
    @Override
    public void autoAssignProposalsTeachers() {
        data.autoAssignProposalsTeachers();
    }
    @Override
    public String listStudentsAssignedWithSupervisor(){return data.listStudentsAssignedWithSupervisor();}
    @Override
    public String listStudentsAssignedWithoutSupervisor(){return data.listStudentsAssignedWithoutSupervisor();}
    @Override
    public String listSupervisorStats() throws CloneNotSupportedException {return data.listSupervisorStats();}
    @Override
    public String listSupervisors() {
        return data.listSupervisorAttributions();
    }
    @Override
    public void closePhase() throws Exception {
        try{
            data.setClosedPhase4();
            Logger.log("Closed Phase 4.");
            changeState(StateEnum.PHASE5, data);
            Logger.log("Changed state to Phase5.");
        } catch(Exception ex){
            Logger.logError("Unable to close Phase 4: " + ex);
            throw new Exception("Não foi possível fechar a fase 4.");
        }
    }

    @Override
    public void manualAssignSupervisorAttribution(String nrStudentAsString, String teacherEmail) throws Exception { data.manualAssignSupervisorAttribution(nrStudentAsString, teacherEmail);}
    @Override
    public void manualUnassignSupervisorAttribution(String nrStudentAsString, String teacherEmail) throws Exception {data.manualUnassignSupervisorAttribution(nrStudentAsString, teacherEmail);}
    @Override
    public void removeAllSupervisorAttributions() throws Exception {data.removeAllSupervisorAttributions();}
    @Override
    public void undoPhase4() throws Exception { data.undoPhase4();}
}

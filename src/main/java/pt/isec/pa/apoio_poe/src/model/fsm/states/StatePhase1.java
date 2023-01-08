package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class StatePhase1 extends StateAdapter {

    public StatePhase1(Context context, DataManager dataManager) {
        super(context, dataManager);
    }

    @Override
    public StateEnum getState() {
        return StateEnum.PHASE1;
    }

    @Override
    public void forward() {
        changeState(StateEnum.PHASE2, data);
    }

    @Override
    public void manageStudents() {
        changeState(StateEnum.PHASE1_STUDENTS, data);
    }

    @Override
    public void manageTeachers() {
        changeState(StateEnum.PHASE1_TEACHERS, data);
    }

    @Override
    public void manageProposals() {
        changeState(StateEnum.PHASE1_PROPOSALS, data);
    }

    @Override
    public String getStudentsAsString(){return data.getStudentsAsString();}
    @Override
    public String getTeachersAsString(){return data.getTeachersAsString();}
    @Override
    public String getProposalsAsString(){return data.getProposalsAsString();}

    @Override
    public boolean verifyCloseable(){
        return (data.getNrProposals("DA") >= data.getNrStudents("DA"))
                && (data.getNrProposals("SI") >= data.getNrStudents("SI"))
                && (data.getNrProposals("RAS") >= data.getNrStudents("RAS"))
                && (data.getNrStudents(null) > 0);
    }

    @Override
    public void closePhase() throws Exception {
        try{
            if(verifyCloseable()){
                data.setClosedPhase1();
                Logger.log("Closed Phase 1.");
            }
            else {
                throw new Exception("Não é possível fechar a fase 1: O número de propostas de " +
                        "pelo menos um ramo é inferior ao numero de alunos desse ramo.");
            }
        } catch(Exception ex) {
            Logger.logError("Unable to close Phase 1: " + ex);
            throw new Exception("Não foi possível fechar a fase 1.");
        }
    }
}

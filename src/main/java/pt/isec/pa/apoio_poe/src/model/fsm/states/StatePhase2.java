package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class StatePhase2 extends StateAdapter {

    public StatePhase2(Context context, DataManager dataManager) {
        super(context, dataManager);
    }

    @Override
    public StateEnum getState() {
        return StateEnum.PHASE2;
    }

    @Override
    public void forward() {
        changeState(StateEnum.PHASE3, data);
    }

    @Override
    public void backward() {
        changeState(StateEnum.PHASE1, data);
    }

    @Override
    public void insertApplicationsFromFile(String filename) throws Exception {
        data.insertApplicationsFromFile(filename);
    }

    @Override
    public String listApplications() {
        return data.getApplicationsAsString();
    }

    @Override
    public String listStudentsAuto(){return data.listStudentsAuto();}
    @Override
    public String listStudentsApplied(){return data.listStudentsApplied();}
    @Override
    public String listStudentsNotApplied(){return data.listStudentsNotApplied();}

    @Override
    public String listProposalsFilteredPhase2(boolean [] filters) { return data.listProposalsFilteredPhase2(filters);}
    @Override
    public void exportApplicationsToFile(String filename) throws Exception {
        data.exportApplicationsToFile(filename);
    }

    @Override
    public boolean verifyCloseable(){return (data.isClosedPhase1());}

    @Override
    public void closePhase() throws Exception {
        try{
            if(verifyCloseable()){
                data.setClosedPhase2();
                Logger.log("Closed Phase 2.");
                changeState(StateEnum.PHASE3, data);
                Logger.log("Changed state to Phase3.");
            }
            else{
                throw new Exception("Não é possível fechar a fase 2: A fase anterior " +
                        "precisa de ser fechada para poder fechar a fase 2.");
            }
        } catch(Exception ex){
            Logger.logError("Unable to close Phase 2: " + ex);
            throw new Exception("Não foi possível fechar a fase 2.");
        }
    }

    @Override
    public void manualInsertApplication(String readString) throws Exception {data.manualInsertApplication(readString);}

    @Override
    public void manualUpdateApplication(String readString) {data.manualUpdateApplication(readString);}

    @Override
    public void manualRemoveApplication(String readString) throws Exception {data.manualRemoveApplication(readString);}

    @Override
    public void removeAllApplications() {data.removeAllApplications();}
}

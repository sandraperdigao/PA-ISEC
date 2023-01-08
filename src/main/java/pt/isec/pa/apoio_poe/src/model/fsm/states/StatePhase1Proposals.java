package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class StatePhase1Proposals extends StateAdapter {
    public StatePhase1Proposals(Context context, DataManager dataManager) {
        super(context, dataManager);
    }

    @Override
    public StateEnum getState() {
        return StateEnum.PHASE1_PROPOSALS;
    }
    @Override
    public void forward() {
        changeState(StateEnum.PHASE1, data);
    }
    @Override
    public void insertProposalsFromFile(String filename) throws Exception {
        data.insertProposalsFromFile(filename);
    }
    @Override
    public void exportProposalsToFile(String filename) throws Exception {
        data.exportProposalsToFile(filename);
    }
    @Override
    public String getProposalsAsString(){return data.getProposalsAsString();}

    @Override
    public void manualInsertProposal(String readString) throws Exception {data.manualInsertProposal(readString);}
    @Override
    public void manualUpdateProposal(String readString) {data.manualUpdateProposal(readString);}
    @Override
    public void manualRemoveProposal(String readString) throws Exception {data.manualRemoveProposal(readString);}
    @Override
    public void removeAllProposals() throws Exception {data.removeAllProposals();}
}

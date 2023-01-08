package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class StatePhase3Conflict extends StateAdapter {
    public StatePhase3Conflict(Context context, DataManager dataManager) {
        super(context, dataManager);
    }
    @Override
    public StateEnum getState() {return StateEnum.PHASE3_CONFLICT;}

    @Override
    public void resolveConflict(int option) throws Exception {
        data.resolveConflict(option);
    }

    @Override
    public ProposalAdapter getConflictProposal(){return data.getConflictProposal();}
    @Override
    public String[] getConflictStudents(){return data.getConflictStudents();}

}

package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class StatePhase5 extends StateAdapter {
    public StatePhase5(Context context, DataManager dataManager) {
        super(context, dataManager);
    }

    @Override
    public StateEnum getState() {
        return StateEnum.PHASE5;
    }

    @Override
    public String listStudentsAttributed(){return data.listStudentsAttributed();}

    @Override
    public String listStudentsNotAttributedWithApplications(){return data.listStudentsNotAttributedWithApplications();}

    @Override
    public String listProposalsFilteredPhase3and5(boolean [] filters) {return data.listProposalsFilteredPhase3and5(filters);}

    @Override
    public String listSupervisorStats() throws CloneNotSupportedException {return data.listSupervisorStats();}
}

package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class StateInitial extends StateAdapter {

    public StateInitial(Context context, DataManager dataManager) {super(context, dataManager);}

    @Override
    public StateEnum getState() {return StateEnum.INITIAL;}

    @Override
    public void forward() {
        changeState(StateEnum.PHASE1, data);
    }

    @Override
    public void loadLastSession() throws Exception {
        try{
            Context loadContext = PoEManager.getInstance(false).loadState();

            if(loadContext!=null){
                StateEnum loadState = loadContext.getState();
                changeState(loadState, loadContext.getData());
            } else{
                throw new Exception("Não foi possivel carregar o estado anterior.");
            }
        } catch (Exception ex) {
            Logger.logError("Unable to load last session: " + ex);
            throw new Exception("Não foi possivel carregar o estado anterior.");
        }
    }
}

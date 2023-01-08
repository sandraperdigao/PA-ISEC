package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

public class TextInterface {
    private final Context context;
    private boolean end = false;

    public TextInterface(Context context){
        this.context = context;
    }

    public void start() {
        while (!end){
            try{
                Logger.dumpMessages();
                Logger.print("------------------------------------------------------------------------------------");
                end = switch (context.getState()){
                    case INITIAL -> Phase0.ui(this.context);
                    case PHASE1 -> Phase1.ui(this.context);
                    case PHASE1_STUDENTS -> Phase1Students.ui(this.context);
                    case PHASE1_TEACHERS -> Phase1Teachers.ui(this.context);
                    case PHASE1_PROPOSALS-> Phase1Proposals.ui(this.context);
                    case PHASE2 -> Phase2.ui(this.context);
                    case PHASE3 -> Phase3.ui(this.context);
                    case PHASE3_CONFLICT -> Phase3Conflict.ui(this.context);
                    case PHASE4 -> Phase4.ui(this.context);
                    case PHASE5 -> Phase5.ui(this.context);
                };
            }
            catch (Exception e) {
                Logger.print("Erro ao realizar a operação escolhida: " + e);
                Logger.logError("Erro ao realizar a operação escolhida: " + e);
            }
        }
    }

}

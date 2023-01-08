package pt.isec.pa.apoio_poe.src.model.fsm;

import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.fsm.states.*;

public enum StateEnum {
    INITIAL,PHASE1,PHASE1_STUDENTS, PHASE1_TEACHERS, PHASE1_PROPOSALS,PHASE2,PHASE3,PHASE3_CONFLICT,PHASE4,PHASE5;

    public IState createState(Context context, DataManager dataManager) {
        return switch (this) {
            case INITIAL -> new StateInitial(context, dataManager);
            case PHASE1 -> new StatePhase1(context, dataManager);
            case PHASE1_STUDENTS -> new StatePhase1Students(context, dataManager);
            case PHASE1_TEACHERS -> new StatePhase1Teachers(context, dataManager);
            case PHASE1_PROPOSALS -> new StatePhase1Proposals(context, dataManager);
            case PHASE2 -> new StatePhase2(context, dataManager);
            case PHASE3 -> new StatePhase3(context, dataManager);
            case PHASE3_CONFLICT -> new StatePhase3Conflict(context, dataManager);
            case PHASE4 -> new StatePhase4(context, dataManager);
            case PHASE5 -> new StatePhase5(context, dataManager);
        };
    }
}

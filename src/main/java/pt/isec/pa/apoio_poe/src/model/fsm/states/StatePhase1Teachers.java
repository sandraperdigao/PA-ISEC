package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class StatePhase1Teachers extends StateAdapter {
    public StatePhase1Teachers(Context context, DataManager dataManager) {
        super(context, dataManager);
    }

    @Override
    public StateEnum getState() {
        return StateEnum.PHASE1_TEACHERS;
    }

    @Override
    public void forward() {
        changeState(StateEnum.PHASE1, data);
    }

    @Override
    public void insertTeachersFromFile(String filename) throws Exception {
        data.insertTeachersFromFile(filename);
    }

    @Override
    public String getTeachersAsString(){return data.getTeachersAsString();}

    @Override
    public void exportTeachersToFile(String filename) throws Exception {
        data.exportTeachersToFile(filename);
    }

    @Override
    public void manualInsertTeacher(String readString) throws Exception {data.manualInsertTeacher(readString);}
    @Override
    public void manualUpdateTeacher(String readString) {data.manualUpdateTeacher(readString);}
    @Override
    public void manualRemoveTeacher(String readString) throws Exception {data.manualRemoveTeacher(readString);}
    @Override
    public void removeAllTeachers() throws Exception {data.removeAllTeachers();}
}

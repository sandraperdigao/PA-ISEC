package pt.isec.pa.apoio_poe.src.model.fsm.states;

import pt.isec.pa.apoio_poe.src.model.data.DataManager;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;
import pt.isec.pa.apoio_poe.src.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class StatePhase1Students extends StateAdapter {
    public StatePhase1Students(Context context, DataManager dataManager) {
        super(context, dataManager);
    }

    @Override
    public StateEnum getState() {
        return StateEnum.PHASE1_STUDENTS;
    }

    @Override
    public void forward() {
        changeState(StateEnum.PHASE1, data);
    }

    @Override
    public void insertStudentsFromFile(String filename) throws Exception {
        data.insertStudentsFromFile(filename);
    }

    @Override
    public String getStudentsAsString(){return data.getStudentsAsString();}

    @Override
    public void exportStudentsToFile(String filename) throws Exception {
        data.exportStudentsToFile(filename);
    }
    @Override
    public void manualInsertStudent(String readString) throws Exception {data.manualInsertStudent(readString);}

    @Override
    public void manualUpdateStudent(String readString) {data.manualUpdateStudent(readString);}

    @Override
    public void manualRemoveStudent(String readString) throws Exception {data.manualRemoveStudent(readString);}

    @Override
    public void removeAllStudents() throws Exception {data.removeAllStudents();}

}

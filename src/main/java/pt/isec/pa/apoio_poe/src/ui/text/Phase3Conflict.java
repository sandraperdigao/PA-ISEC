package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.utils.PAInput.chooseOption;

public class Phase3Conflict {
    static boolean ui(Context context) throws Exception {
        Logger.print("Para atribuir a proposta abaixo,");
        Logger.print(context.getConflictProposal().toString());
        int option = chooseOption("por favor, selecione um dos alunos:", context.getConflictStudents());
        context.resolveConflict(option);
        return false;
    }
}

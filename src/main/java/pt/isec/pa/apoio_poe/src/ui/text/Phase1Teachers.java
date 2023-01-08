package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.ui.text.Messages.*;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.chooseOption;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.readString;

public class Phase1Teachers {

    static boolean ui(Context context) throws Exception {
        int option;
        String title = "Fase 1 - Editar Docentes";
        String [] options = {TEXTO_IMPORTAR_CSV, TEXTO_EXPORTAR_CSV ,TEXTO_MOSTRAR + " Docentes",
                TEXTO_INSERCAO_MANUAL, TEXTO_EDICAO_MANUAL, TEXTO_REMOCAO_MANUAL,
                TEXTO_REMOCAO_TOTAL, TEXTO_FINALIZAR_EDICAO, TEXTO_TERMINAR};
        option = chooseOption(title, options);
        switch (option){
            case 1 -> context.insertTeachersFromFile(readString(TEXTO_PEDIR_FICHEIRO, true));
            case 2 -> context.exportTeachersToFile(readString(TEXTO_PEDIR_FICHEIRO, true));
            case 3 -> Logger.printData(context.getTeachersAsString());
            case 4 -> context.manualInsertTeacher(readString(TEXTO_PEDIR_DOCENTE, false));
            case 5 -> context.manualUpdateTeacher(readString(TEXTO_ACTUALIZAR_DOCENTE, true));
            case 6 -> context.manualRemoveTeacher(readString(TEXTO_REMOVER_DOCENTE, true));
            case 7 -> context.removeAllTeachers();
            case 8 -> context.forward();
            case 9 -> {return true;}
        }
        return false;
    }
}

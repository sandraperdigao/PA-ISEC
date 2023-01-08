package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.ui.text.Messages.*;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.chooseOption;

public class Phase1 {

    static boolean ui(Context context) throws Exception {
        int option;
        String title = "Fase 1 - Configuração";
        if(!context.isState1Closed()){
            String [] optionsOpen = {"Gerir estudantes", "Gerir docentes", "Gerir propostas",
                    TEXTO_FECHAR_FASE, TEXTO_AVANCAR_FASE, TEXTO_TERMINAR};
            option = chooseOption(title, optionsOpen);
            switch (option){
                case 1 -> context.manageStudents();
                case 2 -> context.manageTeachers();
                case 3 -> context.manageProposals();
                case 4 -> {Logger.print(TEXTO_A_FECHAR_FASE); context.closePhase();}
                case 5 -> {Logger.print(TEXTO_A_AVANCAR_FASE); context.forward();}
                case 6 -> {return true;}
            }
        }
        else{
            String [] optionsClosed = {"Apresentar estudantes", "Apresentar docentes",
                    "Apresentar propostas", TEXTO_AVANCAR_FASE, TEXTO_TERMINAR};
            option = chooseOption(title, optionsClosed);
            switch (option){
                case 1 -> Logger.printData(context.getStudentsAsString());
                case 2 -> Logger.printData(context.getTeachersAsString());
                case 3 -> Logger.printData(context.getProposalsAsString());
                case 4 -> {
                    Logger.print(TEXTO_A_AVANCAR_FASE);
                    context.forward();
                }
                case 5 -> {return true;}
            }
        }
        return false;
    }
}

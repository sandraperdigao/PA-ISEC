package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.ui.text.Messages.*;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.chooseOption;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.readString;

public class Phase5 {

    static boolean ui(Context context) throws Exception {
        int option;
        boolean [] filtersC = {false,false,true,false};
        boolean [] filtersD = {false,false,false,true};
        String title = "Fase 5 - Consulta";
        String [] options = {"Apresentar estudantes com propostas atribuídas",
                "Apresentar estudantes sem propostas atribuídas e com opções de candidatura",
                "Apresentar propostas disponíveis", "Apresentar propostas atribuídas",
                "Número de orientações por docente, em média, mínimo, máximo, e por docente especificado.",
                TEXTO_EXPORTAR_CSV, TEXTO_TERMINAR};
        option = chooseOption(title, options);
        switch (option){
            case 1 -> Logger.print(context.listStudentsAttributed());
            case 2 -> Logger.print(context.listStudentsNotAttributedWithApplications());
            case 3 -> Logger.print(context.listProposalsFilteredPhase3and5(filtersC));
            case 4 -> Logger.print(context.listProposalsFilteredPhase3and5(filtersD));
            case 5 -> context.listSupervisorStats();
            case 6 -> context.exportSupervisorAttributionsToFile(readString(TEXTO_PEDIR_FICHEIRO, true));
            case 7 -> {return true;}
        }
        return false;
    }
}

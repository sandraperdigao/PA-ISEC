package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.ui.text.Common.proposalsDisplayFiltersPhase2UI;
import static pt.isec.pa.apoio_poe.src.ui.text.Messages.*;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.chooseOption;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.readString;

public class Phase2 {

    static boolean ui(Context context) throws Exception {
        int option;
        String title = "Fase 2 - Opções de candidatura";
        if(!context.isState2Closed()){
            String [] optionsOpen = {TEXTO_IMPORTAR_CSV, TEXTO_INSERCAO_MANUAL, TEXTO_EDICAO_MANUAL,
                    TEXTO_REMOCAO_MANUAL, TEXTO_REMOCAO_TOTAL, TEXTO_EXPORTAR_CSV, "Apresentar Candidaturas",
                    "Apresentar Estudantes com AutoProposta", "Apresentar Estudantes com Candidatura Registada",
                    "Apresentar Estudantes sem Candidatura Registada", "Apresentar Propostas",
                    TEXTO_FECHAR_FASE, TEXTO_AVANCAR_FASE, TEXTO_RECUAR_FASE, TEXTO_TERMINAR};
            option = chooseOption(title, optionsOpen);
            switch (option){
                case 1 -> context.insertApplicationsFromFile(readString(TEXTO_PEDIR_FICHEIRO, true));
                case 2 -> context.manualInsertApplication(readString(TEXTO_PEDIR_CANDIDATURA, false));
                case 3 -> context.manualUpdateApplication(readString(TEXTO_ACTUALIZAR_CANDIDATURA, true));
                case 4 -> context.manualRemoveApplication(readString(TEXTO_REMOVER_CANDIDATURA, true));
                case 5 -> context.removeAllApplications();
                case 6 -> context.exportApplicationsToFile(readString(TEXTO_PEDIR_FICHEIRO, true));
                case 7 -> Logger.printData(context.listApplications());
                case 8 -> Logger.printData(context.listStudentsAuto());
                case 9 -> Logger.printData(context.listStudentsApplied());
                case 10 -> Logger.printData(context.listStudentsNotApplied());
                case 11 -> Logger.printData(proposalsDisplayFiltersPhase2UI(context));
                case 12 -> {Logger.print(TEXTO_A_FECHAR_FASE); context.closePhase();}
                case 13 -> {Logger.print(TEXTO_A_AVANCAR_FASE); context.forward();}
                case 14 -> {Logger.print(TEXTO_A_RECUAR_FASE); context.backward();}
                case 15 -> {return true;}
            }
        }
        else{
            String [] optionsClosed = {TEXTO_EXPORTAR_CSV, "Apresentar Candidaturas", "Apresentar Estudantes com AutoProposta",
                    "Apresentar Estudantes com Candidatura Registada", "Apresentar Estudantes sem Candidatura Registada",
                    "Apresentar Propostas", TEXTO_AVANCAR_FASE, TEXTO_RECUAR_FASE, TEXTO_TERMINAR};
            option = chooseOption(title, optionsClosed);
            switch (option){
                case 1 -> context.exportApplicationsToFile(readString(TEXTO_PEDIR_FICHEIRO, true));
                case 2 -> Logger.print(context.listApplications());
                case 3 -> Logger.print(context.listStudentsAuto());
                case 4 -> Logger.print(context.listStudentsApplied());
                case 5 -> Logger.print(context.listStudentsNotApplied());
                case 6 -> Logger.print(proposalsDisplayFiltersPhase2UI(context));
                case 7 -> context.forward();
                case 8 -> context.backward();
                case 9 -> {return true;}
            }
        }
        return false;
    }
}

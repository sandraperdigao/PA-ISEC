package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.ui.text.Common.proposalsDisplayFiltersPhase3and5UI;
import static pt.isec.pa.apoio_poe.src.ui.text.Messages.*;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.chooseOption;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.readString;


public class Phase3 {

    static boolean ui(Context context) throws Exception {
        int option;
        String title = "Fase 3 - Atribuição de Propostas";
        if(!context.isState2Closed() && !context.isState3Closed()) {
            String [] options = {
                    "Associar automaticamente alunos a projectos auto-propostos e a projectos aos quais já estão associados",
                    "Apresentar estudantes com autoProposta", "Apresentar estudantes com candidatura registada",
                    "Apresentar estudantes com proposta atribuída ","Apresentar estudantes sem proposta atribuída",
                    "Apresentar propostas", TEXTO_EXPORTAR_CSV,TEXTO_FECHAR_FASE, TEXTO_AVANCAR_FASE, TEXTO_RECUAR_FASE,
                    TEXTO_TERMINAR};
            option = chooseOption(title, options);
            switch (option){
                case 1 -> context.autoAssignProposalsStudents();
                case 2 -> Logger.print(context.listStudentsAuto());
                case 3 -> Logger.print(context.listStudentsApplied());
                case 4 -> Logger.print(context.listStudentsAttributed());
                case 5 -> Logger.print(context.listStudentsNotAttributed());
                case 6 -> Logger.print(proposalsDisplayFiltersPhase3and5UI(context));
                case 7 -> context.exportAttributionsToFile(readString(TEXTO_PEDIR_FICHEIRO, true));
                case 8 -> {Logger.print(TEXTO_A_FECHAR_FASE); context.closePhase();}
                case 9 -> {Logger.print(TEXTO_A_AVANCAR_FASE); context.forward();}
                case 10 -> {Logger.print(TEXTO_A_RECUAR_FASE); context.backward();}
                case 11 -> {return true;}
            }
        } else if (context.isState2Closed() && !context.isState3Closed()) {

            String [] options = {
                    "Associar automaticamente alunos a projectos auto-propostos e a projectos aos quais já estão associados",
                    "Associar automaticamente alunos a propostas sem orientador associado",
                    TEXTO_INSERCAO_MANUAL, TEXTO_REMOCAO_MANUAL, TEXTO_REMOCAO_TOTAL, TEXTO_UNDO, TEXTO_REDO,
                    "Apresentar estudantes com autoProposta", "Apresentar estudantes com candidatura registada",
                    "Apresentar estudantes com proposta atribuída ","Apresentar estudantes sem proposta atribuída",
                    "Apresentar propostas", TEXTO_EXPORTAR_CSV, TEXTO_FECHAR_FASE, TEXTO_AVANCAR_FASE, TEXTO_RECUAR_FASE, TEXTO_TERMINAR};
            option = chooseOption(title, options);
            switch (option){
                case 1 -> context.autoAssignProposalsStudents();
                case 2 -> context.autoAttributionStudent();
                case 3 -> manualAttributionsUI(context, false);
                case 4 -> manualAttributionsUI(context, true);
                case 5 -> context.removeAllAttributions();
                case 6 -> context.undoPhase3();
                case 7 -> context.redoPhase3();
                case 8 -> Logger.print(context.listStudentsAuto());
                case 9 -> Logger.print(context.listStudentsApplied());
                case 10-> Logger.print(context.listStudentsAttributed());
                case 11-> Logger.print(context.listStudentsNotAttributed());
                case 12 -> Logger.print(proposalsDisplayFiltersPhase3and5UI(context));
                case 13 -> context.exportAttributionsToFile(readString(TEXTO_PEDIR_FICHEIRO, true));
                case 14 -> {Logger.print(TEXTO_A_FECHAR_FASE); context.closePhase();}
                case 15 -> {Logger.print(TEXTO_A_AVANCAR_FASE); context.forward();}
                case 16 -> {Logger.print(TEXTO_A_RECUAR_FASE); context.backward();}
                case 17 -> {return true;}
            }



        } else if (!context.isState2Closed() && context.isState3Closed()) {

            String [] options = {
                    "Apresentar estudantes com autoProposta", "Apresentar estudantes com candidatura registada",
                    "Apresentar estudantes com proposta atribuída ", "Apresentar estudantes sem proposta atribuída",
                    "Apresentar propostas", TEXTO_AVANCAR_FASE, TEXTO_RECUAR_FASE, TEXTO_TERMINAR};
            option = chooseOption(title, options);
            switch (option){
                case 1 -> Logger.print(context.listStudentsAuto());
                case 2 -> Logger.print(context.listStudentsApplied());
                case 3 -> Logger.print(context.listStudentsAttributed());
                case 4 -> Logger.print(context.listStudentsNotAttributed());
                case 5 -> Logger.print(proposalsDisplayFiltersPhase3and5UI(context));
                case 6 -> {Logger.print(TEXTO_A_AVANCAR_FASE); context.forward();}
                case 7  -> {Logger.print(TEXTO_A_RECUAR_FASE); context.backward();}
                case 8 -> {return true;}
            }
        } else{
            String [] options = {
                    "Apresentar estudantes com autoProposta", "Apresentar estudantes com candidatura registada",
                    "Apresentar estudantes com proposta atribuída ", "Apresentar estudantes sem proposta atribuída",
                    "Apresentar propostas", TEXTO_AVANCAR_FASE, TEXTO_RECUAR_FASE, TEXTO_TERMINAR};
            option = chooseOption(title, options);
            switch (option){
                case 1 -> Logger.print(context.listStudentsAuto());
                case 2 -> Logger.print(context.listStudentsApplied());
                case 3 -> Logger.print(context.listStudentsAttributed());
                case 4 -> Logger.print(context.listStudentsNotAttributed());
                case 5 -> Logger.print(proposalsDisplayFiltersPhase3and5UI(context));
                case 6 -> {Logger.print(TEXTO_A_AVANCAR_FASE); context.forward();}
                case 7  -> {Logger.print(TEXTO_A_RECUAR_FASE); context.backward();}
                case 8  -> {return true;}
            }
        }

        return false;
    }

    static public void manualAttributionsUI(Context context, boolean remove) throws Exception {

        String todo = remove ? "remover atribuicao" : "atribuir";

        do {
            Logger.print(context.listStudentsNotAttributed());
            Logger.print(context.listProposalsNotAttributed());

            String nrStudentAsString = readString("Insira um numero de aluno para " + todo + " ou SAIR (Exemplo 2019121655)", true);
            if (nrStudentAsString.equalsIgnoreCase("sair"))
                break;

            String codeProposal = readString("Insira um codigo de proposta para " + todo + " ou SAIR (Exemplo P010)", true);
            if (codeProposal.equalsIgnoreCase("sair"))
                break;



            if(remove)
                context.manualUnassignAttribution(nrStudentAsString, codeProposal);
            else
                context.manualAssignAttribution(nrStudentAsString, codeProposal);

        } while (true);
    }
}

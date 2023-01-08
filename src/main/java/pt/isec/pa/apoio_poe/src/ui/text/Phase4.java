package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.ui.text.Messages.*;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.chooseOption;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.readString;

public class Phase4 {
    static boolean ui(Context context) throws Exception {
        int option;
        String title = "Fase 4 - Atribuição de Orientadores";
        String [] options = {
                "Associar automaticamente orientadores a projectos de que são proponentes",
                "Associar manualmente orientadores a estudantes com propostas atribuídas",
                "Remover associação de orientadores a estudantes com propostas atribuídas",
                "Remover todas as associações de orientadores a estudantes com propostas atribuídas",
                TEXTO_UNDO, TEXTO_REDO,
                "Listar Orientadores", "Apresentar estudantes com proposta atribuída e com orientador associado.",
                "Apresentar estudantes com proposta atribuída mas sem orientador associado.",
                "Número de orientações por docente, em média, mínimo, máximo, e por docente especificado.",
                TEXTO_EXPORTAR_CSV, TEXTO_FECHAR_FASE, TEXTO_RECUAR_FASE, TEXTO_TERMINAR};
        option = chooseOption(title, options);
        switch (option){
            case 1 -> context.autoAssignProposalsTeachers();
            case 2 -> manualSupervisorAttributionssUI(context, false);
            case 3 -> manualSupervisorAttributionssUI(context, true);
            case 4 -> context.removeAllSupervisorAttributions();
            case 5 -> context.undoPhase4();
            case 6 -> context.redoPhase4();
            case 7 -> Logger.print(context.listSupervisors());
            case 8 -> Logger.print(context.listStudentsAssignedWithSupervisor());
            case 9 -> Logger.print(context.listStudentsAssignedWithoutSupervisor());
            case 10-> Logger.print(context.listSupervisorStats());
            case 11-> context.exportSupervisorAttributionsToFile(readString(TEXTO_PEDIR_FICHEIRO, true));
            case 12-> {Logger.print(TEXTO_A_FECHAR_FASE); context.closePhase();}
            case 13  -> {Logger.print(TEXTO_A_RECUAR_FASE); context.backward();}
            case 14 -> {return true;}
        }
        return false;
    }

    static void manualSupervisorAttributionssUI(Context context, boolean remove) throws Exception {

        String todo = remove ? "remover atribuicao" : "atribuir";

        do {
            Logger.print(context.listStudentsAttributed());
            Logger.print(context.getTeachersAsString());

            String nrStudentAsString = readString("Insira um numero de aluno para " + todo + " ou SAIR (Exemplo 2019121655)", true);
            if (nrStudentAsString.equalsIgnoreCase("sair"))
                break;

            String teacherEmail = readString("Insira um email para " + todo + " ou SAIR (Exemplo P010)", true);
            if (teacherEmail.equalsIgnoreCase("sair"))
                break;

            if(remove)
                context.manualUnassignSupervisorAttribution(nrStudentAsString, teacherEmail);
            else
                context.manualAssignSupervisorAttribution(nrStudentAsString, teacherEmail);

        } while (true);
    }
}

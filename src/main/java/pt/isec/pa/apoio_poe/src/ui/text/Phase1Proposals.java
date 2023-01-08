package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.ui.text.Messages.*;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.chooseOption;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.readString;

public class Phase1Proposals {

    static boolean ui(Context context) throws Exception {
        int option;
        String title = "Fase 1 - Editar Propostas";
        String [] options = {TEXTO_IMPORTAR_CSV, TEXTO_EXPORTAR_CSV ,TEXTO_MOSTRAR + " Propostas",
                TEXTO_INSERCAO_MANUAL, TEXTO_EDICAO_MANUAL, TEXTO_REMOCAO_MANUAL,
                TEXTO_REMOCAO_TOTAL,TEXTO_FINALIZAR_EDICAO, TEXTO_TERMINAR};
        option = chooseOption(title, options);
        switch (option){
            case 1 -> context.insertProposalsFromFile(readString(TEXTO_PEDIR_FICHEIRO, true));
            case 2 -> context.exportProposalsToFile(readString(TEXTO_PEDIR_FICHEIRO, true));
            case 3 -> Logger.printData(context.getProposalsAsString());
            case 4 -> context.manualInsertProposal(readString(TEXTO_PEDIR_PROPOSTA, false));
            case 5 -> context.manualUpdateProposal(readString(TEXTO_ACTUALIZAR_PROPOSTA, true));
            case 6 -> context.manualRemoveProposal(readString(TEXTO_REMOVER_PROPOSTA, true));
            case 7 -> context.removeAllProposals();
            case 8 -> context.forward();
            case 9 -> {return true;}
        }
        return false;
    }
}

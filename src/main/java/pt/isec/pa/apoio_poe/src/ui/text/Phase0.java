package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.ui.text.Messages.TEXTO_TERMINAR;
import static pt.isec.pa.apoio_poe.src.utils.PAInput.chooseOption;

public class Phase0 {

    static boolean ui(Context context) throws Exception {
        int option;
        String title = "Gestão de projetos e estágios do Departamento de Engenharia Informática e de Sistemas do ISEC";
        String [] options = {"Nova sessão", "Retomar sessão anterior...", TEXTO_TERMINAR};
        option = chooseOption(title, options);
        switch (option){
            case 1 -> {Logger.logAndPrint("A iniciar nova sessão..."); context.forward();}
            case 2 -> {Logger.logAndPrint("A carregar sessao anterior..."); context.loadLastSession();}
            case 3 -> {Logger.logAndPrint("A terminar..."); return true;}
        }
        return false;
    }
}

package pt.isec.pa.apoio_poe.src.ui.text;

import pt.isec.pa.apoio_poe.src.model.fsm.Context;

import static pt.isec.pa.apoio_poe.src.utils.PAInput.readString;

public class Common {

    static public String proposalsDisplayFiltersPhase2UI(Context context) {
        boolean [] filters = {false,false,false,false};
        String intro = ("""
                Escolha os filtros desejados (exemplo: ACD)
                A - Autopropostas de alunos
                B - Propostas de docentes
                C - Propostas com candidaturas
                D - Propostas sem candidatura
                """);
        String answer = readString(intro,true);

        if(answer.contains("A")) filters[0]=true;
        if(answer.contains("B")) filters[1]=true;
        if(answer.contains("C")) filters[2]=true;
        if(answer.contains("D")) filters[3]=true;

        if(!(filters[0] || filters[1] || filters[2] || filters[3]))
            return "Filtro inválido!\n";

        return context.listProposalsFilteredPhase2(filters);
    }

    static public String proposalsDisplayFiltersPhase3and5UI(Context context) {
        boolean [] filters = {false,false,false,false};
        String intro = ("""
                Escolha os filtros desejados (exemplo: ACD)
                A - Autopropostas de alunos
                B - Propostas de docentes
                C - Propostas disponíveis
                D - Propostas atribuídas
                """);
        String answer = readString(intro,true);

        if(answer.contains("A")) filters[0]=true;
        if(answer.contains("B")) filters[1]=true;
        if(answer.contains("C")) filters[2]=true;
        if(answer.contains("D")) filters[3]=true;

        if(!(filters[0] || filters[1] || filters[2] || filters[3]))
            return "Filtro inválido!\n";

        return context.listProposalsFilteredPhase3and5(filters);
    }
}

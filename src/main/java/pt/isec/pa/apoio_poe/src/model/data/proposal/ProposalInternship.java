package pt.isec.pa.apoio_poe.src.model.data.proposal;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.enums.Branch;

import java.util.List;

import static java.lang.Long.parseLong;

public class ProposalInternship extends ProposalAdapter {
    private final String entity;

    public ProposalInternship(ProposalInternship prop){
        super(prop.type, prop.code, prop.title);
        this.abbField = prop.abbField;
        this.entity = prop.entity;
        setNrStudent(prop.getNrStudent());
    }

    public ProposalInternship(List<String> args){
        super(args.get(0), args.get(1), args.get(2), args.get(3));
        this.entity = args.get(4);
        if(args.size() > 5){
            setNrStudent(parseLong(args.get(5)));
        }
    }
    public static boolean isListValidAsProposal(List<String> args) {
        try{
            String [] abbFieldSplit = args.get(2).split("[|]+");
            for(String item : abbFieldSplit)
                Branch.valueOf(item.toUpperCase());
        } catch (IllegalArgumentException ex) {
            Logger.appendMessage("A linha " + args + " tem uma designação de ramo(s) inválida!");
            return false;
        }

        if (args.size() != 5 && args.size() != 6){
            Logger.appendMessage("A linha " + args + " tem um número de valores inválido!");
            return false;
        }

        try{
            if(args.size() == 6 && args.get(5) != null)
                parseLong(args.get(5));
        }
        catch(Exception ex) {
            Logger.appendMessage("A linha " + args + " tem um número de estudante inválido!");
            return false;
        }

        return true;
    }

    @Override
    public String getEntity(){
        return this.entity;
    }

    @Override
    public String toString() {
        return type + "," + code + "," + abbField + "," + title + ","+ entity;
    }
}

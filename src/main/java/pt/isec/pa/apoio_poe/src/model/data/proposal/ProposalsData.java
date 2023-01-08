package pt.isec.pa.apoio_poe.src.model.data.proposal;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;
import pt.isec.pa.apoio_poe.src.model.data.teacher.Teacher;
import pt.isec.pa.apoio_poe.src.utils.FileUtils;

import java.io.*;
import java.util.*;

import static pt.isec.pa.apoio_poe.src.utils.FileUtils.parseStringIntoList;

public class ProposalsData implements Serializable, Cloneable{
    private final Map<String, ProposalAdapter> proposals;

    public ProposalsData(){
        proposals = new HashMap<>();
    }

    public Map<String, ProposalAdapter> getProposals() {
        return this.proposals;
    }

    public void insertProposal(ProposalAdapter proposal){
        if(isValidInsertProposal(proposal)){
            proposals.put(proposal.getCode(), proposal);
        }
    }

    public void insertProposal(String proposal, List<Teacher> teachersList, List<Student> studentsList) throws Exception {
        List<String> temp = parseStringIntoList(proposal);

        if(!ProposalFactory.validator(temp, teachersList, studentsList)){
            throw new Exception("A Proposta nao e valida.");
        }

        ProposalAdapter tempProposal = ProposalFactory.builder(temp);

        if(isValidInsertProposal(tempProposal)){
            if (tempProposal != null) {
                proposals.put(tempProposal.getCode(), tempProposal);
            }
        }
        else
            throw new Exception("A Proposta nao e valida.");
    }

    public void insertProposalsFromFile(String filename, List<Teacher> teachersList, List<Student> studentsList) throws Exception {
        File file = new File(filename);
        List<List<String>> list = FileUtils.importCSVFileAsListOfList(file);

        ProposalsData tempInsert = new ProposalsData();

        for(List<String> listProposal : list){
            if(!ProposalFactory.validator(listProposal, teachersList, studentsList)){
                continue;
            }

            ProposalAdapter temp = ProposalFactory.builder(listProposal);

            if(isValidInsertProposal(temp) && tempInsert.isValidInsertProposal(temp)){
                tempInsert.insertProposal(temp);
            }
        }

        try{
            proposals.putAll(tempInsert.proposals);
        }
        catch (Exception ex){
            Logger.logError("Error importing CSV file!");
            throw new Exception("Erro importar ficheiro CSV!");
        }

    }

    public void removeProposal(ProposalAdapter temp) throws Exception {
        if(isValidRemoveProposal(temp)){
            proposals.remove(temp.getCode(), temp);
        }
        else
            throw new Exception("A proposta nao pode ser removida.");

    }

    private boolean isValidInsertProposal(ProposalAdapter newProposal){
        if(newProposal == null || findProposal(newProposal.getCode()) != null){
            Logger.appendMessage("A proposta " + newProposal + " tem um codigo de proposta invalido!" );
            return false;
        }
        for (ProposalAdapter temp : proposals.values()) {
            if (newProposal.getNrStudent() == null)
                return true;

            if (Objects.equals(temp.getNrStudent(), newProposal.getNrStudent())){
                Logger.appendMessage("A proposta " + newProposal + " tem um numero de estudante invalido!" );
                return false;
            }
        }
        return true;
    }

    private boolean isValidRemoveProposal(ProposalAdapter temp) {
        if(!proposals.containsKey(temp.getCode())){
            Logger.appendMessage("A proposta " + temp + " nao é válida!" );
            return false;
        }
        return true;
    }

    public boolean teacherHasProposals(String teacherEmail) {
        return proposals.values().stream().anyMatch(p -> (p.getTeacherEmail()!=null) && p.getTeacherEmail().equals(teacherEmail));
    }

    public void removeTeacherProposals(String teacherEmail){
        List<ProposalAdapter> proposalsFiltered = proposals.values().stream().filter(p -> (p.getTeacherEmail() !=null && p.getTeacherEmail().equals(teacherEmail))).toList();

        for(ProposalAdapter temp : proposalsFiltered)
            proposals.remove(temp.getCode());
    }

    public boolean studentHasProposals(Long nrStudent) {
        return proposals.values().stream().anyMatch(p -> p.getNrStudent().equals(nrStudent));
    }

    public void removeStudentProposals(Long nrStudent){
        List<ProposalAdapter> proposalsFiltered = proposals.values().stream().filter(p -> (p.getNrStudent() !=null && p.getNrStudent().equals(nrStudent))).toList();

        for(ProposalAdapter temp : proposalsFiltered)
            proposals.remove(temp.getCode());
    }

    public ProposalAdapter findProposal(String proposalKey){
        if(this.proposals.containsKey(proposalKey))
            return this.proposals.get(proposalKey);

        return null;
    }

    public int getNrProposals(String type){
        int count = 0;
        List<ProposalAdapter> list = new ArrayList<>(proposals.values());
        for(ProposalAdapter tempProposal : list){
            String [] abbFieldSplit = tempProposal.getAbbField().split("[|]+");
            if(Arrays.asList(abbFieldSplit).contains(type))
                count++;
        }
        return count;
    }

    public List<ProposalAdapter> toList() {
        List<ProposalAdapter> list = new ArrayList<>(proposals.values());
        Collections.sort(list);
        return list;
    }

    public Map<String, ProposalAdapter> getProposalsCopy() {
        ProposalsData copy = (ProposalsData) this.clone();
        return copy.getProposals();
    }

    public void exportProposalsToFile(String filename) throws Exception {
        try(Writer writer = new FileWriter(filename)){
            for (Map.Entry<String, ProposalAdapter> entry : this.getProposalsCopy().entrySet()){
                writer.append(entry.getValue().toString()).append("\r\n");
            }
        } catch (IOException ex) {
            Logger.logError("Erro a exportar ficheiro CSV");
            throw new Exception("Erro a exportar ficheiro CSV" + ex);
        }
    }

    @Override
    public String toString(){
        if(this.toList().isEmpty())
            return null;

        StringBuilder sb = new StringBuilder();
        for (Object o : this.toList())
            sb.append("\n\t-").append(o.toString());

        return sb.toString();
    }

    @Override
    public Object clone() {
        ProposalsData newProposalsData;
        try {
            newProposalsData = (ProposalsData) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }

        for(ProposalAdapter prop : this.proposals.values())
            newProposalsData.insertProposal(ProposalFactory.builder(prop));

        return newProposalsData;
    }
}

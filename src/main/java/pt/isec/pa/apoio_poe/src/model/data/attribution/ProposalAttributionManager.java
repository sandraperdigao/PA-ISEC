package pt.isec.pa.apoio_poe.src.model.data.attribution;

import pt.isec.pa.apoio_poe.src.model.CommandManager;
import pt.isec.pa.apoio_poe.src.model.commands.AddAttribution;
import pt.isec.pa.apoio_poe.src.model.commands.RemoveAttribution;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;

import java.io.Serializable;
import java.util.*;

public class ProposalAttributionManager implements Serializable, Cloneable {

    CommandManager comMan;
    private final Map<ProposalAdapter, Student> attributions;

    public ProposalAttributionManager() {
        this.attributions = new HashMap<>();
        this.comMan = new CommandManager();
    }

    public boolean hasUndo() { return comMan.hasUndo(); }

    public boolean undo() throws Exception { return comMan.undo(); }

    public boolean hasRedo() { return comMan.hasRedo(); }

    public boolean redo() throws Exception { return comMan.redo(); }

    public boolean addAttributionCommand(Student tempStudent, ProposalAdapter tempProposal) throws Exception {
        return comMan.invokeCommand(new AddAttribution(this, null, tempStudent,  tempProposal));
    }

    public boolean removeAttributionCommand(Student tempStudent, ProposalAdapter tempProposal) throws Exception {
        return comMan.invokeCommand(new RemoveAttribution(this, null, tempStudent,  tempProposal));
    }

    public Map<ProposalAdapter, Student> getAttributions() {
        return this.attributions;
    }

    private ProposalAttributionManager(Map<ProposalAdapter, Student> toCopy){
        this.attributions = toCopy;
    }

    public boolean insertAttribution(ProposalAdapter prop, Student student) throws Exception {
        if(isAttributionPossible(prop, student)){
            this.attributions.put(prop, student);
            prop.setNrStudent(student.getNrStudent());
            return true;
        }

        throw new Exception("A atribuição nao é permitida.");

    }

    public boolean isAttributionPossible(ProposalAdapter proposal, Student student) {

        if(proposal.getType().equals("T1") && !student.getHasAccess())
            return false;

        return !isProposalAttributed(proposal) && !isStudentAttributed(student);
    }

    public Student findStudentAttribution(ProposalAdapter proposalKey) {
        if(this.attributions.containsKey(proposalKey))
            return this.attributions.get(proposalKey);

        return null;
    }

    public ProposalAdapter findProposalAttribution(Student student) {
        if(this.attributions.containsValue(student))
            for (Map.Entry<ProposalAdapter, Student> entry : attributions.entrySet()) {
                Student value = entry.getValue();
                if(value.equals(student))
                    return entry.getKey();
            }
        return null;
    }

    public boolean isAttributedToStudent(ProposalAdapter proposal, Student student){

        if(!attributions.containsKey(proposal))
            return false;

        Student tempStudent = attributions.get(proposal);

        return tempStudent.getEmail().equals(student.getEmail());
    }

    public boolean removeAttribution(ProposalAdapter proposal, Student student) throws Exception {
        if(isAttributedToStudent(proposal, student)){
            attributions.remove(proposal, student);
            return true;
        }
        else
            throw new Exception("A atribuição que pediu para remover, nao existe.");
    }

    public boolean isStudentAttributed(Student student) {
        return attributions.containsValue(student);
    }

    public boolean isProposalAttributed(ProposalAdapter proposal) {
        return attributions.containsKey(proposal);
    }

    public List<ProposalAdapter> toList() {
        List<ProposalAdapter> list = new ArrayList<>(attributions.keySet());
        Collections.sort(list);
        return list;
    }

    public Map<ProposalAdapter, Student> getProposalAttributionsCopy() {
        ProposalAttributionManager copy = (ProposalAttributionManager) this.clone();

        return copy.getAttributions();
    }

    @Override
    public String toString(){
        if(attributions.entrySet().isEmpty())
            return null;

        StringBuilder sb = new StringBuilder("Atribuição de Propostas:\n");
        for (Map.Entry<ProposalAdapter, Student> entry : attributions.entrySet()) {
            sb.append(String.format("%s -> %s \n",entry.getKey(),entry.getValue()));
        }

        return sb.toString();
    }

    @Override
    public Object clone() {
        Map<ProposalAdapter, Student> newHashmap = new HashMap<>();
        for (Map.Entry<ProposalAdapter, Student> entry : this.getAttributions().entrySet()) {
            newHashmap.put(entry.getKey(), entry.getValue());
        }
        return new ProposalAttributionManager(newHashmap);
    }

    public void removeAllAttributions() throws Exception {
        try{
            for (Map.Entry<ProposalAdapter, Student> entry : getProposalAttributionsCopy().entrySet()) {
                if(entry.getKey().getType().equals("T3"))
                    continue;
                if(entry.getKey().getType().equals("T2") && entry.getKey().getNrStudent() != null)
                    continue;

                attributions.remove(entry.getKey(), entry.getValue());
            }
        }
        catch(Exception ex){
            throw new Exception("Ocorreu um problema durante a remoção das atribuições de propostas.");
        }


    }
}

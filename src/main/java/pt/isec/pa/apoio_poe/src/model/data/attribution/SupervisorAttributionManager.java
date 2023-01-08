package pt.isec.pa.apoio_poe.src.model.data.attribution;

import pt.isec.pa.apoio_poe.src.model.CommandManager;
import pt.isec.pa.apoio_poe.src.model.commands.AddSupervisorAttribution;
import pt.isec.pa.apoio_poe.src.model.commands.RemoveSupervisorAttribution;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.teacher.Teacher;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SupervisorAttributionManager implements Serializable {

    CommandManager comMan;
    private final Map<ProposalAdapter, Teacher> supervisorAttributions;

    public SupervisorAttributionManager() {
        this.supervisorAttributions = new HashMap<>();
        this.comMan = new CommandManager();
    }

    public boolean hasUndo() { return comMan.hasUndo(); }
    public boolean undo() throws Exception { return comMan.undo(); }
    public boolean hasRedo() { return comMan.hasRedo(); }
    public boolean redo() throws Exception { return comMan.redo(); }

    public boolean addSupervisorAttributionCommand(Teacher tempTeacher, ProposalAdapter tempProposal) throws Exception {
        return comMan.invokeCommand(new AddSupervisorAttribution(null, this, tempTeacher,  tempProposal));
    }

    public boolean removeSupervisorAttributionCommand(Teacher tempTeacher, ProposalAdapter tempProposal) throws Exception {
        return comMan.invokeCommand(new RemoveSupervisorAttribution(null, this, tempTeacher,  tempProposal));
    }

    public Map<ProposalAdapter, Teacher> getSupervisorAttributions() {
        return this.supervisorAttributions;
    }

    private SupervisorAttributionManager(Map<ProposalAdapter, Teacher> toCopy){
        this.supervisorAttributions = toCopy;
    }

    public boolean insertSupervisorAttribution(ProposalAdapter prop, Teacher teacher){
        this.supervisorAttributions.put(prop, teacher);

        return true;
    }

    public Teacher findProposalAttribution(ProposalAdapter proposalKey) {
        if(this.supervisorAttributions.containsKey(proposalKey))
            return this.supervisorAttributions.get(proposalKey);

        return null;
    }

    public boolean isAttributedToTeacher(ProposalAdapter proposal, Teacher teacher){

        if(!supervisorAttributions.containsKey(proposal))
            return false;

        Teacher tempTeacher = supervisorAttributions.get(proposal);

        return tempTeacher.getEmail().equals(teacher.getEmail());
    }

    public Map<ProposalAdapter, Teacher> getSupervisorAttributionsCopy() {
        SupervisorAttributionManager copy = (SupervisorAttributionManager) this.clone();

        return copy.getSupervisorAttributions();
    }

    public boolean removeSupervisorAttribution(ProposalAdapter proposal, Teacher teacher) throws Exception {
        if(isAttributedToTeacher(proposal, teacher)){
            supervisorAttributions.remove(proposal, teacher);
            return true;
        }
        else
            throw new Exception("A atribuição que pediu para remover, nao existe.");
    }

    public boolean isTeacherAttributed(Teacher teacher) {
        return supervisorAttributions.containsValue(teacher);
    }

    public boolean isProposalAttributed(ProposalAdapter proposal) {
        return supervisorAttributions.containsKey(proposal);
    }

    public boolean isAttributionPossible(ProposalAdapter proposal, Teacher teacher) {
        return !isProposalAttributed(proposal);
    }

    @Override
    public String toString(){
        if(supervisorAttributions.entrySet().isEmpty())
            return null;

        StringBuilder sb = new StringBuilder("Atribuição de Orientadores:\n");
        for (Map.Entry<ProposalAdapter, Teacher> entry : supervisorAttributions.entrySet()) {
            sb.append(String.format("%s -> %s \n",entry.getKey(),entry.getValue()));
        }

        return sb.toString();
    }

    @Override
    public Object clone() {
        Map<ProposalAdapter, Teacher> newHashmap = new HashMap<>();
        for (Map.Entry<ProposalAdapter, Teacher> entry : this.getSupervisorAttributions().entrySet()) {
            newHashmap.put(entry.getKey(), entry.getValue());
        }
        return new SupervisorAttributionManager(newHashmap);
    }

    public void removeAllSupervisorAttributions() throws Exception {
        try{
            for (Map.Entry<ProposalAdapter, Teacher> entry : getSupervisorAttributionsCopy().entrySet()) {
                supervisorAttributions.remove(entry.getKey(), entry.getValue());
            }
        }
        catch(Exception ex){
            throw new Exception("Ocorreu um problema durante a remoção das atribuições de propostas.");
        }


    }
}

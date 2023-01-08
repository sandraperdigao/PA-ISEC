package pt.isec.pa.apoio_poe.src.model.commands;

import pt.isec.pa.apoio_poe.src.model.data.attribution.ProposalAttributionManager;
import pt.isec.pa.apoio_poe.src.model.data.attribution.SupervisorAttributionManager;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.teacher.Teacher;

public class RemoveSupervisorAttribution extends CommandAdapter {
    Teacher teacherToAttribute;
    ProposalAdapter proposalToAttribute;

    public RemoveSupervisorAttribution(ProposalAttributionManager paMan, SupervisorAttributionManager saMan, Teacher tempTeacher, ProposalAdapter tempProposal) {
        super(paMan, saMan);
        this.teacherToAttribute = tempTeacher;
        this.proposalToAttribute = tempProposal;
    }

    @Override
    public boolean undo() {
        return saMan.insertSupervisorAttribution(proposalToAttribute, teacherToAttribute);
    }

    @Override
    public boolean execute() throws Exception {
        return saMan.removeSupervisorAttribution(proposalToAttribute, teacherToAttribute);
    }
}

package pt.isec.pa.apoio_poe.src.model.commands;

import pt.isec.pa.apoio_poe.src.model.data.attribution.ProposalAttributionManager;
import pt.isec.pa.apoio_poe.src.model.data.attribution.SupervisorAttributionManager;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;

public class AddAttribution extends CommandAdapter {
    Student studentToAttribute;
    ProposalAdapter proposalToAttribute;

    public AddAttribution(ProposalAttributionManager paMan, SupervisorAttributionManager saMan, Student tempStudent, ProposalAdapter tempProposal) {
        super(paMan, saMan);
        this.studentToAttribute = tempStudent;
        this.proposalToAttribute = tempProposal;
    }

    @Override
    public boolean undo() throws Exception {
        return paMan.removeAttribution(proposalToAttribute, studentToAttribute);
    }

    @Override
    public boolean execute() throws Exception {
        return paMan.insertAttribution(proposalToAttribute, studentToAttribute);
    }
}

package pt.isec.pa.apoio_poe.src.model.commands;


import pt.isec.pa.apoio_poe.src.model.data.attribution.ProposalAttributionManager;
import pt.isec.pa.apoio_poe.src.model.data.attribution.SupervisorAttributionManager;

public abstract class CommandAdapter implements ICommand {
    protected ProposalAttributionManager paMan;
    protected SupervisorAttributionManager saMan;

    protected CommandAdapter(ProposalAttributionManager paMan, SupervisorAttributionManager saMan) {
        this.paMan = paMan;
        this.saMan = saMan;
    }

}
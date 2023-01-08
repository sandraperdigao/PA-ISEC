package pt.isec.pa.apoio_poe.src.model.commands;

public interface ICommand {
    boolean undo() throws Exception;
    boolean execute() throws Exception;
}

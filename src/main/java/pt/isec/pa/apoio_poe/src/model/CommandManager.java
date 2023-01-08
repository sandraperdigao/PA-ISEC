package pt.isec.pa.apoio_poe.src.model;

import pt.isec.pa.apoio_poe.src.model.commands.ICommand;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandManager {
    private final Deque<ICommand> history;
    private final Deque<ICommand> redoCmds;
    public CommandManager() {
        history = new ArrayDeque<>();
        redoCmds = new ArrayDeque<>();
    }

    public boolean invokeCommand(ICommand cmd) throws Exception {
        redoCmds.clear();
        if (cmd.execute()) {
            history.push(cmd);
            return true;
        }
        history.clear();
        return false;
    }

    public boolean hasUndo() {
        return history.size() > 0;
    }

    public boolean undo() throws Exception {
        if (history.isEmpty())
            return false;
        ICommand cmd = history.pop();
        cmd.undo();
        redoCmds.push(cmd);
        return true;
    }

    public boolean hasRedo() {
        return redoCmds.size() > 0;
    }

    public boolean redo() throws Exception {
        if (redoCmds.isEmpty())
            return false;
        ICommand cmd = redoCmds.pop(); cmd.execute();
        history.push(cmd);
        return true;
    }
}

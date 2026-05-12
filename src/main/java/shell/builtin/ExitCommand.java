package shell.builtin;

import shell.state.ShellContext;

import java.util.List;

public class ExitCommand implements Command {
    @Override
    public String execute(List<String> args, ShellContext context) {
        context.stop();
        return "";
    }
}

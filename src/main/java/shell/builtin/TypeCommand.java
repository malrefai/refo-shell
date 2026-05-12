package shell.builtin;


import shell.resolver.CommandResolution;
import shell.resolver.CommandResolver;
import shell.state.ShellContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record TypeCommand(Set<String> builtinNames) implements Command {

    @Override
    public String execute(List<String> args, ShellContext context) {
        return args.stream().map(arg -> evaluate(arg, context)).collect(Collectors.joining("\n"));
    }

    private String evaluate(String cmdName, ShellContext context) {
        CommandResolution cmdResolution = CommandResolver.resolve(cmdName, context, builtinNames);

        return switch (cmdResolution.type()) {
            case BUILTIN -> String.format("%s is a shell builtin", cmdName);
            case EXTERNAL -> String.format("%s is %s", cmdName, cmdResolution.absolutePath());
            case NOT_FOUND -> String.format("%s: not found", cmdName);
        };
    }
}

package shell.resolver;

import shell.state.ShellContext;

import java.io.File;
import java.util.Set;

public class CommandResolver {
    private CommandResolver() {
        /* This utility class should not be instantiated */
    }

    public static CommandResolution resolve(String cmdName, ShellContext context, Set<String> builtinNames) {
        if (builtinNames.contains(cmdName)) {
            return new CommandResolution(CommandType.BUILTIN, null);
        }

        for (String path : context.getPaths()) {
            File file = new File(path, cmdName);
            if (file.isFile() && file.canExecute())
                return new CommandResolution(CommandType.EXTERNAL, file.getAbsolutePath());
        }

        return new CommandResolution(CommandType.NOT_FOUND, null);
    }
}

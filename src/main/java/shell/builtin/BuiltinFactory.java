package shell.builtin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BuiltinFactory {
    private final Map<String, Command> builtins = new HashMap<>();

    public BuiltinFactory() {
        builtins.put("cd", new CdCommand());
        builtins.put("echo", new EchoCommand());
        builtins.put("exit", new ExitCommand());
        builtins.put("pwd", new PwdCommand());

        // Register 'type' LAST so it "sees" everything above
        builtins.put("type", new TypeCommand(builtins.keySet()));
    }

    public Command getCommand(String name) {
        return builtins.get(name);
    }

    public Set<String> getNames() {
        return builtins.keySet();
    }
}

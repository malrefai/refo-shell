package shell.state;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ShellContext {
    private boolean running = true;
    private Path currentDirectory;
    private final List<String> paths;

    public ShellContext() {
        // Initialize directory to where the JVM started
        this.currentDirectory = Paths.get(System.getProperty("user.dir")).toAbsolutePath();

        String pathEnv = System.getenv("PATH");
        if (pathEnv != null) {
            this.paths = Arrays.asList(pathEnv.split(File.pathSeparator));
        } else {
            this.paths = List.of();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        this.running = false;
    }

    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(Path newPath) {
        this.currentDirectory = newPath.normalize().toAbsolutePath();
    }

    public List<String> getPaths() {
        return paths;
    }
}

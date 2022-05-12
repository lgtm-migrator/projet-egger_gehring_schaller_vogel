package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import utils.DirectoryDeleter;

@Command(name = "clean", description = "Clean a static site")
public class Clean implements Callable<Integer> {

    @Parameters(paramLabel = "SITE", description = "The site to build")
    public Path site;

    @Override
    public Integer call() throws IOException {
        if (site == null) {
            throw new NullPointerException("site ne doit pas etre null");
        }
        Path path = Paths.get(site + "/build");
        File file = path.toFile();

        if (!file.exists() || !file.isDirectory()) {
            return -1;
        }

        DirectoryDeleter.delete(file);
        return 0;
    }
}

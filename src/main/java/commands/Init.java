package commands;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/** Cette classe est utilisée en conjonction avec picocli pour initialiser un site static. */
@Command(name = "init", description = "Initialise un dossier pour le site statique")
public class Init implements Callable<Integer> {
    // Chemin vers le template
    private static final Path TEMPLATE_PATH = Paths.get("src/main/resources/index.md");

    @Parameters(paramLabel = "PATH", description = "Le chemin qui doit contenir le site")
    public String path;

    @Override
    public Integer call() throws URISyntaxException, IOException {
        if (path == null) {
            throw new NullPointerException("PATH ne doit pas etre null");
        }
        var ri = Build.class.getClassLoader().getResource("exempleSite");
        if (ri == null) {
            throw new RuntimeException("Le dossier exempleSite n'a pas été trouvé");
        }
        if (Files.exists(Path.of(Paths.get(path).toString(), "content"))) {
            System.err.println(
                    "Le dossier "
                            + Path.of(Paths.get(path).toString(), "content")
                            + " existe  déjà");
            return -1;
        }
        try {
            var srcFile = Path.of(ri.getPath()).toFile();
            FileUtils.copyDirectory(srcFile, Path.of(path).toFile());
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
}

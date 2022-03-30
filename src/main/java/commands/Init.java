package commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

@Command(name = "init", description = "Initialize a static site directory")
public class Init implements Callable<Integer> {
    // Chemin vers le template
    private static final Path TEMPLATE_PATH = Paths.get("src/main/resources/index.md");

    @Parameters(paramLabel = "PATH", description = "Le chemin qui doit contenir le site")
    public String path;

    @Override
    public Integer call() throws URISyntaxException, IOException {
        // Crée le File associé au path
        File destination = new File(path + "/index.md");
        // Vérifie si le dossier existe déjà
        if (destination.exists()) {
            System.err.println("Le dossier existe déjà");
            return -1;
        }
        if (!destination.mkdirs()) {
            System.err.println("Impossible de créer le dossier");
            return -1;
        }

        // Copie le template dans le dossier créé
        Files.copy(TEMPLATE_PATH, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Template créé ici: " + destination.toPath());
        return 0;
    }

}
package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "build", description = "Build a static site")
public class Build implements Callable<Integer> {

    @CommandLine.Parameters(
            paramLabel = "rootDirectory",
            description = "root directory of markdown files")
    public File rootDirectory;

    @Override
    public Integer call() throws IOException {
        // check param exists
        if (rootDirectory == null) {
            throw new NullPointerException("Le nom de dossier ne peut pas être vide");
        }

        if (rootDirectory.exists()) {
            if (rootDirectory.isDirectory()) {

                String[] file = rootDirectory.list();

                assert file != null;
                // the directory must not be empty
                if (file.length == 0) {
                    System.out.println("Error: Empty directory");
                    return -1;
                }

                // create dir build
                Files.createDirectories(Path.of(rootDirectory + "/build"));

                // read each file
                for (String md : file) {
                    File md_file = new File(rootDirectory + "/" + md);

                    // only parse if it's a file
                    if (md_file.isFile()) {

                        String name = md_file.getName();
                        int ext = name.lastIndexOf('.');
                        String extension = name.substring(ext);

                        // check extension is md
                        if (extension.equals(".md")) {
                            String fileNameWithOutExt = name.substring(0, ext);

                            Runtime runtime = Runtime.getRuntime();
                            String command =
                                    "pandoc --standalone -o "
                                            + rootDirectory
                                            + "/build/"
                                            + fileNameWithOutExt
                                            + ".html "
                                            + rootDirectory
                                            + "/"
                                            + fileNameWithOutExt
                                            + ".md";
                            runtime.exec(command);
                        }
                    }
                }

            } else {
                System.out.println("Error: not a directory");
                return -1;
            }

        } else {
            System.out.println("Error: incorrect directory");
            return -1;
        }

        return 0;
    }
}

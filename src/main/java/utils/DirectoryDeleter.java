package utils;

import java.io.File;
import java.nio.file.Files;

public class DirectoryDeleter {
    public static void delete(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (!Files.isSymbolicLink(f.toPath())) {
                    delete(f);
                }
            }
        }
        file.delete();
    }
}

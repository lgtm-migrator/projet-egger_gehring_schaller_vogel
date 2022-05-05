package commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class ServeTest {
    private static final String TEST_PATH_1 = "test1/site/";

    @Test
    public void testServeWithoutParameterThrowsException() {
        Serve serve = new Serve();
        // assert que serve sans paramètre lance une exception
        NullPointerException thrown = assertThrows(NullPointerException.class, serve::call);

        assertEquals("Le nom de dossier ne peut pas être null", thrown.getMessage());
    }

    @Test
    public void testServeOnDirectoryThatDoesntExistsDoesntWork() throws IOException {
        Serve serve = new Serve();
        serve.rootDirectory = new File(TEST_PATH_1);
        // essayer de refaire un serve sur un dossier non existant retourne une erreur
        assertEquals(serve.call(), -1);
    }

    @AfterAll
    public static void deleteDirectories() {
        File directory1 = new File(TEST_PATH_1);
        deleteDirectory(directory1);
    }

    private static void deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }
}

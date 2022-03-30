package commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InitTest {
    private static final String TEST_PATH = "test/site/";

    @Test
    public void testInitWithoutParameterThrowsException() {
        Init init = new Init();
        //assert que init sans paramètre lance une exception
        NullPointerException thrown = assertThrows(NullPointerException.class, init::call);

        assertEquals("PATH ne doit pas etre null", thrown.getMessage());
    }

    @Test
    public void testInitWithParameterWorks() throws URISyntaxException, IOException {
        Init init = new Init();
        init.path = TEST_PATH;
        //assert que init retourne sans erreur
        assertEquals(init.call(), 0);
    }

    @Test
    public void testInitWithInvalidParameterReturnsError() throws URISyntaxException, IOException {
        Init init = new Init();
        init.path = "/&abc\\";
        //assert que init retourne une erreur
        assertEquals(init.call(), -1);
    }

    @AfterAll
    public static void deleteDirectories() {
        File directory = new File(TEST_PATH);
        deleteDirectory(directory);
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

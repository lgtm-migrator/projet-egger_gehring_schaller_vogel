package commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import utils.DirectoryDeleter;

public class InitTest {
    private static final String TEST_PATH_1 = "test1/site/";
    private static final String TEST_PATH_2 = "test2/site/";

    @Test
    public void testInitWithoutParameterThrowsException() {
        Init init = new Init();
        // assert que init sans paramètre lance une exception
        NullPointerException thrown = assertThrows(NullPointerException.class, init::call);

        assertEquals("PATH ne doit pas etre null", thrown.getMessage());
    }

    @Test
    public void testInitWithParameterWorks() throws URISyntaxException, IOException {
        Init init = new Init();
        init.path = TEST_PATH_1;
        // assert que init retourne sans erreur
        assertEquals(init.call(), 0);
    }

    @Test
    public void testInitOnDirectoryThatExistsDoesntWork() throws URISyntaxException, IOException {
        Init init = new Init();
        init.path = TEST_PATH_2;
        // assert que init retourne sans erreur
        assertEquals(init.call(), 0);
        // essayer de refaire un init sur le meme path provoque une erreur
        assertEquals(init.call(), -1);
    }

    @Test
    public void testInitWithInvalidParameterReturnsError() throws URISyntaxException, IOException {
        Init init = new Init();
        init.path = "/&abc\\";
        // assert que init retourne une erreur
        assertEquals(init.call(), -1);
    }

    @AfterAll
    public static void deleteDirectories() {
        DirectoryDeleter.delete(Path.of("test1").toFile());
        DirectoryDeleter.delete(Path.of("test2").toFile());
    }
}

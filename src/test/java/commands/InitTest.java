package commands;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InitTest {
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
        init.path = "test/site";
        //assert que init retourne sans erreur
        assertEquals(init.call(), 0);
        deleteDirectory(new File("test"));
    }

    @Test
    public void testInitWithInvalidParameterReturnsError() throws URISyntaxException, IOException {
        Init init = new Init();
        init.path = "/&abc\\";
        //assert que init retourne une erreur
        assertEquals(init.call(), -1);
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}

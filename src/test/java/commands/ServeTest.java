package commands;

import java.io.File;
import java.io.IOException;
import java.net.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.DirectoryDeleter;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    public void testSiteWorksAfterBuild() throws IOException, URISyntaxException {
        Init init = new Init();
        Build build = new Build();
        Serve serve = new Serve();
        init.path = TEST_PATH_1;
        build.rootDirectory = new File(TEST_PATH_1);
        serve.rootDirectory = new File(TEST_PATH_1);
        init.call();
        build.call();
        assertEquals(serve.call(), 0);
        assertTrue(isSocketAlive("localhost", 4242));
    }

    @AfterEach
    public void deleteDirectories() {
        File directory1 = new File(TEST_PATH_1);
        DirectoryDeleter.delete(directory1);
    }

    private boolean isSocketAlive(String hostName, int port) {
        boolean isAlive = false;

        // Creates a socket address from a hostname and a port number
        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();

        // Timeout required - it's in milliseconds
        int timeout = 2000;

        try {
            socket.connect(socketAddress, timeout);
            socket.close();
            isAlive = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return isAlive;
    }
}

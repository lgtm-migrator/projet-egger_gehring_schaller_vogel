package commands;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BuildTest {
    private static final String TEST_PATH = "test1/";

    private static final String TEST_PATH_1 = "test1/site/";

    // not existing directory
    private static final String TEST_PATH_2 = "test2/site/";

    // empty directory
    private static final String TEST_PATH_3 = "test3/";

    @BeforeAll
    public static void CreateDirectory() throws IOException {
        Files.createDirectories(Path.of("test1/"));
        Files.createDirectories(Path.of(TEST_PATH_1));
        Files.createFile(Path.of(TEST_PATH_1 + "fichier2.md"));
        Files.createFile(Path.of(TEST_PATH_1 + "test.md"));
        Files.createDirectories(Path.of(TEST_PATH_3));
    }

    @Test
    public void testBuildWithoutParameterThrowsException() {
        Build build = new Build();
        // assert que build sans paramètre lance une exception
        NullPointerException thrown = assertThrows(NullPointerException.class, build::call);
        assertEquals("Le nom de dossier ne peut pas être vide", thrown.getMessage());
    }

    @Test
    public void testBuildWithParameterWorks() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_1);
        // assert que build retourne sans erreur
        assertEquals(build.call(), 0);
    }

    @Test
    public void testBuildOnDirectoryThatDoesntExistsDoesntWork() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_2);
        // assert que build retourne avec erreur
        assertEquals(build.call(), -1);
    }

    @Test
    public void testBuildOnFileDoesntWork() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_1 + "/test.md");
        // assert que build retourne avec erreur
        assertEquals(build.call(), -1);
    }

    @Test
    public void testBuildOnEmptyDirectoryDoesntWork() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_3);
        // assert que build retourne avec erreur
        assertEquals(build.call(), -1);
    }

    @Test
    public void testBuildOnInvalidParameterDoesntWork() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File("*qwert");
        // assert que build retourne avec erreur
        assertEquals(build.call(), -1);
    }

    @Test
    public void testBuildCreateDirectoryBuild() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_1);
        // assert que build retourne sans erreur
        assertEquals(build.call(), 0);

        File dir = new File(TEST_PATH_1 + "build/");
        assertTrue(dir.exists());
    }

    @Test
    public void testBuildCreateFileHtml() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_1);
        // assert que build retourne sans erreur
        assertEquals(build.call(), 0);

        File dir = new File(TEST_PATH_1 + "build/");
        String[] result = dir.list();
        assert result != null;
        Arrays.sort(result);
        String[] except = {"fichier2.html", "test.html"};
        assertArrayEquals(result, except);
    }

    @AfterAll
    public static void deleteDirectories() {
        File directory1 = new File(TEST_PATH);
        deleteDirectory(directory1);
        File directory2 = new File(TEST_PATH_3);
        deleteDirectory(directory2);
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

package commands;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.*;
import utils.DirectoryDeleter;

public class BuildTest {
    private static final String TEST_PATH = "test1/";

    private static final String TEST_PATH_1 = "test1/site/";

    // not existing directory
    private static final String TEST_PATH_2 = "test2/site/";

    // empty directory
    private static final String TEST_PATH_3 = "test3/";
    private final String siteFolderName = "site";

    @BeforeAll
    public static void CreateDirectory() throws IOException {
        try {
            Files.createDirectories(Path.of(TEST_PATH));
            Files.createDirectories(Path.of(TEST_PATH_1, Build.contentFolderName));
            File f = new File(TEST_PATH_1 + "/" + Build.contentFolderName);
            f.setWritable(true);
            Files.createDirectories(Path.of(TEST_PATH_1, Build.templateFolderName));
            Files.createFile(Path.of(TEST_PATH_1, Build.contentFolderName, "index.md"));
            Files.createFile(Path.of(TEST_PATH_1, Build.templateFolderName, "layout.html"));

            Files.createDirectories(Path.of(TEST_PATH));
            Files.createDirectories(Path.of(TEST_PATH_2, Build.contentFolderName));
            Files.createDirectories(Path.of(TEST_PATH_2, Build.templateFolderName));
            Files.createFile(Path.of(TEST_PATH_2, Build.contentFolderName, "content.md"));
            Files.createFile(Path.of(TEST_PATH_2, Build.templateFolderName, "layout.html"));
            Files.createDirectories(Path.of(TEST_PATH_3));

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testBuildWithoutParameterFails() {
        Build build = new Build();
        // assert que build sans paramètre lance une exception
        NullPointerException thrown = assertThrows(NullPointerException.class, build::call);
        assertEquals("Le nom de dossier ne peut pas être vide", thrown.getMessage());
    }

    @Test
    public void testBuildWithParameterWorks() throws IOException {
        Build build = new Build();
        File f = new File(TEST_PATH_1);
        f.setWritable(true);

        build.rootDirectory = f;
        // assert que build retourne sans erreur
        assertEquals(0, build.call());
    }

    @Test
    public void testBuildOnDirectoryThatDoesntExistsDoesntWork() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_3);
        // assert que build retourne avec erreur
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, build::call);
        assertEquals(
                "Ce site n'a pas de contenu ou ce repertoire ne contient pas de site",
                thrown.getMessage());
    }

    @Test
    public void testBuildOnFileDoesntWork() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_1 + "/test.md");
        // assert que build retourne avec erreur
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, build::call);
        assertEquals("Le dossier n'existe pas", thrown.getMessage());
    }

    @Test
    public void testBuildOnEmptyDirectoryDoesntWork() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_3);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, build::call);
        assertEquals(
                "Ce site n'a pas de contenu ou ce repertoire ne contient pas de site",
                thrown.getMessage());
    }

    @Test
    public void testBuildOnInvalidParameterDoesntWork() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File("*qwert");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, build::call);
        assertEquals("Le dossier n'existe pas", thrown.getMessage());
    }

    @Test
    public void testBuildCreateDirectoryBuild() throws IOException {
        Build build = new Build();
        build.rootDirectory = new File(TEST_PATH_1);
        // assert que build retourne sans erreur
        assertEquals(0, build.call());

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
        String[] expect = {"index.html"};
        assertArrayEquals(expect, result);
    }

    @AfterAll
    public static void deleteDirectories() {
        File directory = new File(TEST_PATH);
        DirectoryDeleter.delete(directory);
        File dir = new File("test2");
        DirectoryDeleter.delete(dir);
        File directory1 = new File(TEST_PATH_1);
        DirectoryDeleter.delete(directory1);
        File directory2 = new File(TEST_PATH_2);
        DirectoryDeleter.delete(directory2);
        File directory3 = new File(TEST_PATH_3);
        DirectoryDeleter.delete(directory3);
    }


    @Test
    void genHtmlFromMarkdown() {
        String markdown = "# Hello World";
        String html = Build.genHtmlFromMarkdown(markdown);
        assertEquals("<h1>Hello World</h1>\n", html);
    }
    @Test
    void genHtmlFromMarkdownHeader2() {
        String markdown = "## Hello World";
        String html = Build.genHtmlFromMarkdown(markdown);
        assertEquals("<h2>Hello World</h2>\n", html);
    }
    @Test
    void genHtmlFromMarkdownHeader3() {
        String markdown = "### Hello World";
        String html = Build.genHtmlFromMarkdown(markdown);
        assertEquals("<h3>Hello World</h3>\n", html);
    }

    void setUp() throws IOException, URISyntaxException {
        var ri = Build.class.getClassLoader().getResource("exempleSite");
        try {
            var srcFile =  Path.of(ri.getPath()).toFile();
            FileUtils.copyDirectory( srcFile, Path.of(siteFolderName).toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void tearDown() {
        DirectoryDeleter.delete(new File(siteFolderName));
    }
    @Test
    void createHtmlFromContentFile() {
        try{
            setUp();
            Build b = new Build();
            b.rootDirectory = new File(siteFolderName);
            b.call();
            var index = Path.of(siteFolderName.toString(), "build","index.html").toFile();
            assert(index.exists());
            //there should be template, content, and build
            var site = Path.of(siteFolderName).toFile();
            assertTrue(site.exists());
            assertNotNull(site.list());
            assertEquals( 3,site.list().length);
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println("Erreur" + e.getMessage());
        }
        tearDown();
    }
}

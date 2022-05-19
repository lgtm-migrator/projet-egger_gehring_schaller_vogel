import static org.junit.jupiter.api.Assertions.*;

import commands.Build;
import commands.Clean;
import commands.Init;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.DirectoryDeleter;

public class IntegrationTest {
    private static final String TEST_DIR = "test_dir";

    @AfterEach
    void tearDown() {
        DirectoryDeleter.delete(Path.of(TEST_DIR).toFile());
    }

    @Test
    void initThenBuildShouldWork() {
        var init = new Init();
        var build = new Build();
        init.path = TEST_DIR;
        build.rootDirectory = Path.of(TEST_DIR).toFile();
        try {
            assertEquals(0, init.call());
            assertFalse(Path.of(TEST_DIR, "build").toFile().exists());
            assertEquals(0, build.call());
            assertTrue(Path.of(TEST_DIR, "build").toFile().exists());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown" + e.getMessage());
        }
        assertTrue(Path.of(TEST_DIR).toFile().exists());
        assertTrue(Path.of(TEST_DIR, "template").toFile().exists());
        assertTrue(Path.of(TEST_DIR, "content").toFile().exists());
    }

    @Test
    void initThenBuildThenCleanShouldWork() {
        var init = new Init();
        var build = new Build();
        var clean = new Clean();

        init.path = TEST_DIR;
        build.rootDirectory = Path.of(TEST_DIR).toFile();
        clean.site = Path.of(TEST_DIR);
        try {
            assertEquals(0, init.call());
            assertFalse(Path.of(TEST_DIR, "build").toFile().exists());
            assertEquals(0, build.call());
            assertTrue(Path.of(TEST_DIR, "build").toFile().exists());
            assertEquals(clean.call(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown" + e.getMessage());
        }
        assertTrue(Path.of(TEST_DIR).toFile().exists());
        assertTrue(Path.of(TEST_DIR, "template").toFile().exists());
        assertTrue(Path.of(TEST_DIR, "content").toFile().exists());
        assertFalse(Path.of(TEST_DIR, "build").toFile().exists());
    }
}

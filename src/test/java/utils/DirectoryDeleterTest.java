package utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;

public class DirectoryDeleterTest {
    @Test
    public void testDeleteDirectoryWithContent() throws Exception {
        File folder = new File("folderWithContent");
        folder.mkdir();
        var file = new File("folderWithContent/file1");
        file.createNewFile();
        assertTrue(file.exists());

        DirectoryDeleter.delete(file);
        assertFalse(file.exists());
        DirectoryDeleter.delete(folder);
        assertFalse(folder.exists());
    }

    @Test
    public void testDeleteEmptyDirectory() throws Exception {
        File folder = new File("folderWithContent");
        folder.mkdir();
        assertTrue(folder.exists());
        DirectoryDeleter.delete(folder);
        assertFalse(folder.exists());
    }
}

package commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class CleanTest {

    @Test
    public void testCleanWithoutParameter() {
        Clean clean = new Clean();
        NullPointerException thrown = assertThrows(NullPointerException.class, clean::call);
        assertEquals("site ne doit pas etre null", thrown.getMessage());
    }

    @Test
    public void testCleanWithInvalidParameter() throws IOException {
        Clean clean = new Clean();
        clean.site = Path.of("/&abc");
        assertEquals(clean.call(), -1);
    }
}

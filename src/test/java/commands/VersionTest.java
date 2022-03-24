package commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VersionTest {
    @Test
    public void testVersionEndsWithoutError() {
        Version version = new Version();
        //assert the version returns without error
         assertEquals(version.call(),0);
    }
}

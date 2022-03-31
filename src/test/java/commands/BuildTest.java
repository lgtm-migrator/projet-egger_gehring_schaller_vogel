package commands;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuildTest {
  private static final String TEST_PATH_1 = "test1/site/";

  // not existing directory
  private static final String TEST_PATH_2 = "test2/site/";

  // empty directory
  private static final String TEST_PATH_3 = "test3/site/";

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
    // assert que build retourne sans erreur
    assertEquals(build.call(), -1);
  }

  @Test
  public void testBuildOnFileDoesntWork() throws IOException {
    Build build = new Build();
    build.rootDirectory = new File(TEST_PATH_1 + "/test.md");
    // assert que build retourne sans erreur
    assertEquals(build.call(), -1);
  }

  @Test
  public void testBuildOnEmptyDirectoryDoesntWork() throws IOException {
    Build build = new Build();
    build.rootDirectory = new File(TEST_PATH_3);
    // assert que build retourne sans erreur
    assertEquals(build.call(), -1);
  }

  @Test
  public void testBuildOnInvalidParameterDoesntWork() throws IOException {
    Build build = new Build();
    build.rootDirectory = new File("*qwert");
    // assert que build retourne sans erreur
    assertEquals(build.call(), -1);
  }
}

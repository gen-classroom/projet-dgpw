package ch.heigvd.labo.command;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BuildTest {
    static final String DIR_TO_BUILD = "www/test_build";

    /**
     * Create a test repertory
     */
    @BeforeAll
    static void createRepertoryTest() throws Exception {
        try {
            int exitCode = new CommandLine(new Init()).execute("test_build");
            assertEquals(exitCode, 0);
            assertThrows(Exception.class, () -> {
                throw new Exception();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int exitCode = new CommandLine(new New()).execute("-f", "mapremierepage", "-d" , "test_build");
            assertEquals(exitCode, 0);
            assertThrows(Exception.class, () -> {
                throw new Exception();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Build an inexistant repertory
     */
    @Test
    @Order(1)
    public void shouldNotBuildInexistantRepertory() throws Exception{
        int exitCode = new CommandLine(new Build()).execute("-d", "test_build_inexistant");
        assertEquals(exitCode, 1);
        assertThrows(IOException.class, () -> {
            throw new IOException("Le répertoire est inexistant");
        });
    }

    /**
     * Build an existant repertory
     */
    @Test
    @Order(2)
    public void shouldBuildExistantRepertory() throws Exception{
        int exitCode = new CommandLine(new Build()).execute("-d", "test_build");
        assertEquals(exitCode, 0);
        assertThrows(IOException.class, () -> {
            throw new IOException("Le répertoire est inexistant");
        });
    }

    @AfterAll
    /**
     * Une fois les tests terminés, le répertoire servant de test est supprimé
     * afin de ne pas surcharger le projet
     */
    static void cleanRepertoryTest(){
        File dir = new File("www/test_build");
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

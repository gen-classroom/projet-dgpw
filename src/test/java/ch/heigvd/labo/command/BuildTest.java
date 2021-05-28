package ch.heigvd.labo.command;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openjdk.jmh.annotations.*;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BuildTest {
    static final String DIR_TO_BUILD = "www/test_build";

    /**
     * Création d'un répertoire test
     */
    @BeforeAll
    public static void createRepertoryTest() throws Exception {
        try {
            int exitCode = new CommandLine(new Init()).execute("-d", "test_build");
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
     * Build d'un répertoire inexistant, retourne une erreur
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
     * Build d'un répertoire existant
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

    /**
     * Une fois les tests terminés, le répertoire servant de test est supprimé
     * afin de ne pas surcharger le projet
     */
    @AfterAll
    public static void cleanRepertoryTest(){
        File dir = new File(DIR_TO_BUILD);
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package ch.heigvd.labo.command;
import static org.junit.jupiter.api.Assertions.*;

import ch.heigvd.labo.Main;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CleanTest {
    static final String DIR_TO_REMOVE = "www/test_clean";

    /**
     * Create a test repertory
     */
    @BeforeAll
    static void createRepertoryTest() throws Exception{
        File dir = new File(DIR_TO_REMOVE + "/build");
        try {
            if (!dir.mkdirs()) {
                throw new IOException("Impossible de créer le répertoire");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an existant repertory
    */
    @Test
    @Order(1)
    void shouldDeleteExistantRepertoryBuild()throws Exception{
        int exitCode = new CommandLine(new Clean()).execute(DIR_TO_REMOVE);
        assertEquals(exitCode, 0);
        assertThrows(IOException.class, () -> {
            throw new IOException("Impossible de supprimer le répertoire");
        });
    }

    /**
     * Try to delete an inexistant repertory build
     */
    @Test
    @Order(2)
    void shouldNotDeleteInexistantRepertoryBuild() throws Exception {
        int exitCode = new CommandLine(new Clean()).execute(DIR_TO_REMOVE);
        assertEquals(exitCode, 2);
        assertThrows(IOException.class, () -> {
            throw new IOException("Le répertoire est inexistant");
        });
    }

    /**
     * Try to delete an inexistant repertory
    */
    @Test
    @Order(3)
    void shouldNotDeleteInexistantRepertory() throws Exception {
        int exitCode = new CommandLine(new Clean()).execute("www/inexistant");
        assertEquals(exitCode, 1);
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
        File dir = new File("www/test_clean");
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
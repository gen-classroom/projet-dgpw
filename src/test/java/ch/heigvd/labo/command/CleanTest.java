package ch.heigvd.labo.command;
import static org.junit.jupiter.api.Assertions.*;

import ch.heigvd.labo.Main;
import org.junit.jupiter.api.*;
import picocli.CommandLine;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CleanTest {
    static final String DIR_TO_REMOVE = "www/test_init";

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
    void shouldDeleteExistantRepertory()throws Exception{
        File dir = new File(DIR_TO_REMOVE);
        int exitCode = new CommandLine(new Clean()).execute(dir);
        assertEquals(exitCode, 0);
        assertThrows(IOException.class, () -> {
            throw new IOException("Impossible de supprimer le répertoire");
        });
    }

    /**
     * Try to delete an inexistant repertory
     */
    @Test
    @Order(2)
    void shouldNotDeleteInexistantRepertory() throws Exception {
        File dir = new File(DIR_TO_REMOVE);
        int exitCode = new CommandLine(new Clean()).execute(dir);
        assertEquals(exitCode, 1);
        assertThrows(IOException.class, () -> {
            throw new IOException("Le répertoire est inexistant");
        });
    }

}
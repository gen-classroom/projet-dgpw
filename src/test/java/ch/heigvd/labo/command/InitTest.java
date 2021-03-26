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
public class InitTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    /**
     * Test de la création d'un répertoire qui n'existe pas dans le projet
     * @throws Exception
     */
    @Test
    @Order(1)
    void shouldCreateRepertory() throws Exception {
        int exitCode = new CommandLine(new Init()).execute("test_init");
        assertEquals(exitCode, 0);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    /**
     * Test de la création d'un répertoire qui existe déjà, la création doit échouer
     * @throws Exception
     */
    @Test
    @Order(2)
    void shouldNotCreateRepertory() throws Exception {
        int exitCode = new CommandLine(new Init()).execute("test_init");
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    /**
     * Une fois les tests terminés, le répertoire servant de test est supprimé
     * afin de ne pas surcharger le projet
     */
    @AfterAll
    static void cleanRepertoryTest(){
        File dir = new File("www/test_init");
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
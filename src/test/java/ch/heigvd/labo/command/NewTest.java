package ch.heigvd.labo.command;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    /**
     * Test qui n'a aucun argument en paramètres, la commande retourne une erreur
     * @throws Exception
     */
    @Test
    @Order(1)
    void shouldCreateErrorNoArgument() throws Exception {
        int exitCode = new CommandLine(new New()).execute();
        assertEquals(exitCode, 0);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    /**
     * Test qui prend en paramètres un seul argument, il devrait prendre deux arguments normalement
     * @throws Exception
     */
    @Test
    @Order(2)
    void shouldCreateErrorOneArgument() throws Exception {
        int exitCode = new CommandLine(new New()).execute("-f", "mapremierepage");
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    /**
     * Test qui prend en paramètre un répertoire du site statique qui n'existe pas
     * @throws Exception
     */
    @Test
    @Order(3)
    void shouldCreateErrorDirDoesntExist() throws Exception {
        int exitCode = new CommandLine(new New()).execute("-f", "mapremierepage", "-d" , "test/");
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    /**
     * Test qui crée la page correctement
     * @throws Exception
     */
    @Test
    @Order(4)
    void shouldCreateFile() throws Exception {
        File test = new File("www/mon/site/");
        test.mkdirs();
        int exitCode = new CommandLine(new New()).execute("-f", "mapremierepage", "-d" , "mon/site/");
        assertEquals(exitCode, 0);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    /**
     * Test qui ne devrait pas recréer une page car elle existe déjà, une erreur est retournée
     * @throws Exception
     */
    @Test
    @Order(5)
    void shouldNotCreateFile() throws Exception {
        int exitCode = new CommandLine(new New()).execute("-f", "mapremierepage", "-d" , "mon/site/");
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    /**
     * Après que tous les tests aient été effectués, on nettoie tout le répertoire test
     * -> www/mon/site/mapremierepage.md
     */
    @AfterAll
    static void cleanRepertoryTest() {
        File dir = new File("www/mon");
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
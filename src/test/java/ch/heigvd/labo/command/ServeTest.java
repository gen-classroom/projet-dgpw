package ch.heigvd.labo.command;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

public class ServeTest {

    @Test
    @Order(1)
    void shouldCreateErrorNoArgument() throws Exception {
        int exitCode = new CommandLine(new Serve()).execute();
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(2)
    void shouldCreateErrorInit() throws Exception {
        int exitCode = new CommandLine(new Serve()).execute("-init");
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(3)
    void shouldCreateErrorNew() throws Exception {
        int exitCode = new CommandLine(new Serve()).execute("-new");
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(4)
    void shouldCreateErrorInitWrongArgument() throws Exception {
        int exitCode = new CommandLine(new Serve()).execute("-init", "-f", "page");
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(5)
    void shouldCreateErrorNewMissArgument() throws Exception {
        int exitCode = new CommandLine(new Serve()).execute("-new", "-f", "page");
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(6)
    void shouldCreateSite() throws Exception {
        int exitCode = new CommandLine(new Serve()).execute("-init", "-d", "test_serve");
        assertEquals(exitCode, 0);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(7)
    void shouldCreateSitePage() throws Exception {
        int exitCode = new CommandLine(new Serve()).execute("-new", "-d", "test_serve","-f", "page");
        assertEquals(exitCode, 0);
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
        File dir = new File("www/test_serve");
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

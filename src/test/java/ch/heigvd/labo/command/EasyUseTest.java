package ch.heigvd.labo.command;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

public class EasyUseTest {

    @Test
    @Order(1)
    void shouldCreateErrorNoArgument() throws Exception {
        int exitCode = new CommandLine(new EasyUse()).execute();
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(2)
    void shouldCreateErrorInit() throws Exception {
        int exitCode = new CommandLine(new EasyUse()).execute("-init");
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(3)
    void shouldCreateErrorNew() throws Exception {
        int exitCode = new CommandLine(new EasyUse()).execute("-new");
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(4)
    void shouldCreateErrorInitWrongArgument() throws Exception {
        int exitCode = new CommandLine(new EasyUse()).execute("-init", "-f", "page");
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(5)
    void shouldCreateErrorNewMissArgument() throws Exception {
        int exitCode = new CommandLine(new EasyUse()).execute("-new", "-f", "page");
        assertEquals(exitCode, 3);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(6)
    void shouldCreateSite() throws Exception {
        int exitCode = new CommandLine(new EasyUse()).execute("-init", "-d", "test_easyuse");
        assertEquals(exitCode, 0);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(7)
    void shouldCreateSitePage() throws Exception {
        int exitCode = new CommandLine(new EasyUse()).execute("-new", "-d", "test_easyuse","-f", "page");
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
        File dir = new File("www/test_easyuse");
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

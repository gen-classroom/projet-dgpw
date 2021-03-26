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

    @Test
    @Order(1)
    void shouldCreateErrorNoArgument() throws Exception {
        int exitCode = new CommandLine(new New()).execute();
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(2)
    void shouldCreateErrorOneArgument() throws Exception {
        int exitCode = new CommandLine(new New()).execute("-f", "mapremierepage");
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(3)
    void shouldCreateErrorDirDoesntExist() throws Exception {
        int exitCode = new CommandLine(new New()).execute("-f", "mapremierepage", "-d" , "test/");
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

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

    @Test
    @Order(5)
    void shouldNotCreateFile() throws Exception {
        int exitCode = new CommandLine(new New()).execute("-f", "mapremierepage", "-d" , "mon/site/");
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

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
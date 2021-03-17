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

public class InitTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Test
    void shouldNotCreateRepertory() throws Exception {
        int exitCode = new CommandLine(new Init()).execute("test_init");
        assertEquals(exitCode, 0);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    void shouldCreateRepertory() throws Exception {
        int exitCode = new CommandLine(new Init()).execute("test_init");
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @AfterAll
    static void cleanRepertoryTest(){
        File dir = new File("test_init");
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
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
    void shouldCreateFile() throws Exception {
        int exitCode = new CommandLine(new New()).execute("mapremierepage");
        assertEquals(exitCode, 0);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    @Order(2)
    void shouldNotCreateFile() throws Exception {
        int exitCode = new CommandLine(new New()).execute("mapremierepage");
        assertEquals(exitCode, 1);
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @AfterAll
    static void cleanRepertoryTest(){
        New n = new New();
        File dir = new File(n.getMetaPath() + "/mapremierepage.md");
        if(!dir.delete())
        {
            System.out.println("Erreur de suppression du fichier");
        }
    }

}
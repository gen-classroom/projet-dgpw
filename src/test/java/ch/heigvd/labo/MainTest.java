package ch.heigvd.labo;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class MainTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void shouldDisplayVersion(){
        String strSouhaite = "DGPW version";
        new CommandLine(new Main()).execute("-v");
        assertTrue(output.toString().contains(strSouhaite));
    }

    @Test
    public void shouldDisplayVersion2(){
        String strSouhaite = "DGPW version";
        new CommandLine(new Main()).execute("--version");
        assertTrue(output.toString().contains(strSouhaite));
    }
}

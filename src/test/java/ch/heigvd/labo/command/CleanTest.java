package ch.heigvd.labo.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import picocli.CommandLine;
import java.io.File;
import java.io.IOException;


import org.apache.commons.io.FileUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CleanTest {
    static final String DIR_TO_REMOVE = "www/test_clean";

    /**
     * Création d'un répertoire test
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
     * Suppression d'un répertoire existant
    */
    @Test
    @Order(1)
    void shouldDeleteExistantRepertoryBuild()throws Exception{
        int exitCode = new CommandLine(new Clean()).execute("-d", DIR_TO_REMOVE);
        assertEquals(exitCode, 0);
        assertThrows(IOException.class, () -> {
            throw new IOException("Impossible de supprimer le répertoire");
        });
    }

    /**
     * Suppression d'un répertoire build inexistant, retourne une erreur
     */
    @Test
    @Order(2)
    void shouldNotDeleteInexistantRepertoryBuild() throws Exception {
        int exitCode = new CommandLine(new Clean()).execute("-d", DIR_TO_REMOVE);
        assertEquals(exitCode, 2);
        assertThrows(IOException.class, () -> {
            throw new IOException("Le répertoire est inexistant");
        });
    }

    /**
     * Suppression d'un répertoire inexistant, retourne une erreur
    */
    @Test
    @Order(3)
    void shouldNotDeleteInexistantRepertory() throws Exception {
        int exitCode = new CommandLine(new Clean()).execute("-d", "inexistant");
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
package ch.heigvd.labo.command;

import java.io.File;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "init", description ="Initialise un répertoire de site statique")
public class Init implements Callable<Integer> {
    // Répertoire racine de notre site statique
    static final String DIR_RACINE = "www/";

    @Override public Integer call(){
        File dir = new File(DIR_RACINE + file.getPath());

        if (!dir.mkdirs()) {
            System.out.println("Le création du répertoire a échoué ou le répertoire existe déjà");
            return 1;
        } else {
            System.out.println("Le répertoire a été créé");
            return 0;
        }
    }
    @Parameters(paramLabel = "FILE", description = "répertoire pour site statique")
    File file;
}
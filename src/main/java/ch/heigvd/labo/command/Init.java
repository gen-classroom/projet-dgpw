package ch.heigvd.labo.command;

import java.io.File;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "init", description ="Initialise un répertoire de site statique")
public class Init implements Callable<Integer> {
    @Override public Integer call(){

        File dir = new File(file.getPath());

        if (!dir.mkdirs()) {
            System.out.println("Le création du répertoire a échoué");
            return 1;
        } else {
            System.out.println("Le répertoire a été créé");
        }
        return 0;
    }
    @Parameters(paramLabel = "FILE", description = "répertoire pour site statique")
    File file;
}
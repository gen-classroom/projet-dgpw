package ch.heigvd.labo.command;

import java.util.concurrent.Callable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "build", description ="Compile un site statique")
public class Build implements Callable<Integer> {
    static final String DIR_ROOT = "www/";
    static final String DIR_TO_COMPILE = "/metadonnee";

    @CommandLine.Option(names = "-d", description = "Répertoire du site statique")
    static File siteDir;

    @Override public Integer call(){
        if(siteDir != null){
            File dir = new File(DIR_ROOT + siteDir.getPath());
            //Vérifie que le répertoire du site est existant
            if (!dir.exists()) {
                System.out.format("Impossible d'accéder au répertoire %s. Celui-ci est inexistant.\n",dir.getName());
                return 1;
            }

            

            return 0;
        } else {
            System.out.println("Le paramètre -d n'a pas été précisé !");
            return 1;
        }
    }
}
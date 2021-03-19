package ch.heigvd.labo.command;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "clean", description ="Nettoie le répertoire build du site statique")
public class Clean implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "FILE", description = "Chemin du répertoire du site statique à nettoyer")
    File dir;

    @Override public Integer call(){
        if (!dir.exists()) {
            System.out.format("Impossible d'accéder au répertoire %s. Celui-ci est inexistant\n",dir.getName());
            return 1;
        }

        File dirBuild = new File(dir.getPath()+"/build");

        if(!dirBuild.exists()){
            System.out.format("Impossible de supprimer %s. Celui-ci est inexistant\n",dirBuild.getName());
            return 2;
        }

        try {
            FileUtils.deleteDirectory(dirBuild);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Le répertoire a été supprimé.");
        return 0;
    }
}
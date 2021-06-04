package ch.heigvd.labo.command;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import static ch.heigvd.labo.Utility.*;

@Command(name = "clean", description ="Nettoie le répertoire build du site statique.")
public class Clean implements Callable<Integer> {

    @CommandLine.Option(names = "-d", description = "Chemin du répertoire du site statique à nettoyer")
    private File dir;

    @Override public Integer call() throws IOException{
        File dirS = new File(DIR_ROOT + dir.getPath());
        System.out.println(dirS);

        //Vérifie que le répertoire du site est existant
        if (!dirS.exists()) {
            System.out.format("Impossible d'accéder au répertoire %s. Celui-ci est inexistant.\n",dirS.getName());
            return 1;
        }

        File dirToRemoveBuild = new File(dirS.getPath() + DIR_BUILD);
        File dirToRemoveResources = new File(dirS.getPath() + DIR_RESOURCES);

        //Vérifie que le répertoire build a supprimé est existant
        if(!dirToRemoveBuild.exists()){
            System.out.format("Impossible de supprimer %s. Celui-ci est inexistant.\n",dirToRemoveBuild.getName());
            return 2;
        }

        //Supprime le répertoire récursivement
        try {
            FileUtils.deleteDirectory(dirToRemoveBuild);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.format("Le répertoire build du site %s a été supprimé.\n",dirS.getName());
        return 0;
    }
}
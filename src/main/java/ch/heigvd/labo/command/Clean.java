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
    static File dir;

    @Override public Integer call() throws IOException{
        //Vérifie que le répertoire est renseigné
        if(dir == null ) {
            System.out.println("Merci de renseigner le nom du répertoire à créer : \n-d [nom du répertoire]");
            return 1;
        }

        //Vérifie que le répertoire du site est existant
        if (!dir.exists()) {
            System.out.format("Impossible d'accéder au répertoire %s. Celui-ci est inexistant.\n",dir.getName());
            return 1;
        }

        File dirToRemove = new File(dir.getPath() + DIR_TO_REMOVE);

        //Vérifie que le répertoire a supprimé est existant
        if(!dirToRemove.exists()){
            System.out.format("Impossible de supprimer %s. Celui-ci est inexistant.\n",dirToRemove.getName());
            return 2;
        }

        //Supprime le répertoire récursivement
        try {
            FileUtils.deleteDirectory(dirToRemove);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.format("Le répertoire build du site %s a été supprimé.",dir.getName());
        return 0;
    }
}
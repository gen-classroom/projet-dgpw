package ch.heigvd.labo.command;

import java.io.File;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "new", description ="Création d'une nouvelle page")
public class New implements Callable<Integer> {
    private final String DIR_RACINE = "www/";
    private String metaPath = "";

    @Override public Integer call(){
        File dir = new File(DIR_RACINE);
        searchPath(dir);
        // Création du dossier avec toutes les métadonnées.
        if(!metaPath.contains("metadonnee")) {
            metaPath += "/metadonnee/";
            File metadonnee = new File(metaPath);
            metadonnee.mkdirs();
        }

        return 0;
    }
    @CommandLine.Parameters(paramLabel = "FILE", description = "répertoire pour site statique")
    File file;

    /**
     * Fonction qui va chercher l'emplacement du site statique
     * @param file Répertoire racine
     */
    public void searchPath(File file) {
        File[] list = file.listFiles();

        if (list != null) {
            if(list.length < 1) {
                metaPath = file.getPath();
                return;
            }

            for (File f : list) {
                if (f.isDirectory()) {
                    searchPath(f);
                }
            }
        }
    }

}

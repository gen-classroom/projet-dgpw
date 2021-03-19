package ch.heigvd.labo.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "init", description ="Initialise un répertoire de site statique")
public class Init implements Callable<Integer> {
    // Répertoire racine de notre site statique
    static final String DIR_RACINE = "www/";

    @Override public Integer call() throws IOException {
        File dir = new File(DIR_RACINE + file.getPath());

        if (!dir.mkdirs()) {
            System.out.println("La création du répertoire a échoué ou le répertoire existe déjà");
            return 1;
        } else {
            System.out.println("Le répertoire a été créé");

            // Création du fichier de configuration
            try {
                File conf = new File(dir + "/config.yaml");
                boolean created = false;

                // Si le fichier de config existe deja, on ne le recrée pas
                if (conf.exists()) {
                    System.out.println("le fichier de configuration existe déjà");
                    return 0;
                } else {
                    created = conf.createNewFile();
                }

                if (created) {
                    System.out.println("Le fichier de config a été créé : " + conf.getAbsolutePath());

                    // Ajout du template dans le fichier de config créé
                    FileWriter writing = new FileWriter(conf, true);
                    PrintWriter printing = new PrintWriter(writing);
                    printing.append("indexfile: #inserer nom de fichier index\n" +
                            "title: #inserer titre \n" +
                            "sourcedir:#inserer repertoire source\n" +
                            "datadir:.\n" +
                            "filesdir:#inserer repertoire fichiers\n");
                    printing.close();

                } else {
                    System.out.println("Un problème a été rencontré et le fichier n'a pas été créé.");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    @Parameters(paramLabel = "FILE", description = "répertoire pour site statique")
    File file;
}
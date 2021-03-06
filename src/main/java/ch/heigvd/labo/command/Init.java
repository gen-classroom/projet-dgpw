package ch.heigvd.labo.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static ch.heigvd.labo.Utility.*;

@Command(name = "init", description ="Initialise un répertoire de site statique")
public class Init implements Callable<Integer> {

    @Option(names = "-d", description = "Répertoire du site statique")
    private File file;

    @Override public Integer call() throws IOException {
        //Vérifie que le répertoire est renseigné
        if(file == null ) {
            System.out.println("Merci de renseigner le nom du répertoire à créer : \n-d [nom du répertoire]");
            return 1;
        }

        File dir = new File(DIR_ROOT + file.getPath());
        dir.mkdirs();
        if (!dir.exists()) {
            System.out.println("La création du répertoire a échoué ou le répertoire existe déjà");
            return 1;
        } else {
            System.out.println("Le répertoire a été créé");

            // On ajoute un répertoire "metadonnee" dans lequel toutes les métadonnées seront stockées
            File metadataDir = new File(dir.toString() + DIR_METADATA);
            metadataDir.mkdirs();
            if (!metadataDir.exists()) {
                System.out.format("Le répertoire n'existe pas %s", metadataDir.getName());
                return 1;
            }

            // Création du fichier de configuration
            try {
                File conf = new File(dir + "/config.yaml");

                // Si le fichier de config existe deja, on ne le recrée pas
                if (conf.exists()) {
                    System.out.println("le fichier de configuration existe déjà");
                    return 1;
                }

                if (conf.createNewFile()) {
                    System.out.println("Le fichier de config a été créé : " + conf.getAbsolutePath());

                    // Ajout du template dans le fichier de config créé
                    FileWriter writing = new FileWriter(conf, true);
                    PrintWriter printing = new PrintWriter(writing);
                    printing.append(YAML_TEMPLATE);
                    printing.close();

                } else {
                    System.out.println("Un problème a été rencontré et le fichier n'a pas été créé.");
                    throw new IOException("La création du fichier de configuration a échoué.");
                }

                // Création du fichier index
                conf = new File(dir + "/index.md");

                // Si le fichier index existe deja, on ne le recrée pas
                if (conf.exists()) {
                    System.out.println("le fichier d'index existe déjà");
                    return 1;
                }

                if (conf.createNewFile()) {
                    System.out.println("Le fichier d'index a été créé : " + conf.getAbsolutePath());

                    // Ajout du template dans le fichier de config créé
                    FileWriter writing = new FileWriter(conf, true);
                    PrintWriter printing = new PrintWriter(writing);
                    printing.append(MD_INDEX + MD_TEMPLATE);
                    printing.close();

                } else {
                    System.out.println("Un problème a été rencontré et le fichier n'a pas été créé.");
                    throw new IOException("La création du fichier d'index a échoué.");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
}
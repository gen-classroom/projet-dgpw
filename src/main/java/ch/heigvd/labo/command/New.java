package ch.heigvd.labo.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static ch.heigvd.labo.Utility.*;


@Command(name = "new", description ="Création d'une nouvelle page")
public class New implements Callable<Integer> {
    @Option(names = "-f", description = "Nom de la page")
    static File filePage;

    @Option(names = "-d", description = "Répertoire du site statique")
    static File dirStatic;

    private static String path = "";

    @Override public Integer call() throws IOException {
        if(dirStatic != null && filePage != null) {

            path = DIR_ROOT + dirStatic.getPath();
            File dirTest = new File(path);

            // L'utilisateur doit préciser en paramètres le répertoire du site statique, s'il n'existe pas
            // on retourne une erreur
            if(!dirTest.exists()) {
                System.out.println("Ce répertoire de site statique n'existe pas");
                return 1;
            }

            // On ajoute un répertoire "metadonnee" dans lequel toutes les métadonnées seront stockées
            File dir = new File(path + DIR_METADATA);
            path = dir.getPath();

            if (!dir.exists()) {
                System.out.format("Le répertoire n'existe pas %s",dir.getName());
                return 1;
            }

            try {
                // Création du nouveau fichier en .md
                File page = new File(path + "/" + filePage.getName() + ".md");
                boolean isCreated = false;

                // Si la page existe déjà, on retourne une erreur
                if (page.exists()) {
                    System.out.println("Le fichier existe déjà");
                    return 1;
                } else {
                    isCreated = page.createNewFile();
                }

                if (isCreated) {
                    System.out.println("La page voulue, " + page.getName() + ", a été créée");

                    // Template d'une page, on écrit directement dans le fichier
                    FileWriter writing = new FileWriter(page, true);
                    PrintWriter printing = new PrintWriter(writing);
                    printing.append(MD_TEMPLATE);
                    printing.close();

                } else {
                    System.out.println("Erreur, le fichier n'a pas pu être créé");
                    return 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return 0;
        } else {
            System.out.println("Merci de renseigner le nom du répertoire et/ou du fichier à créer : \n-d [nom du répertoire] \n-f [nom du fichier]");
            return 1;
        }
    }
}

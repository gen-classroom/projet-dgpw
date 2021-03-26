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

    private final String DIR_RACINE = "www/";
    private static String path = "";

    @Override public Integer call() throws IOException {
        if(dirStatic != null && filePage != null) {
            File dir = new File(DIR_RACINE + dirStatic.getPath() + "/metadonnee");
            path = dir.getPath();

            if (!dir.mkdirs()) {
                System.out.println("Le création du répertoire a échoué ou le répertoire existe déjà");
                return 1;
            }

            try {
                // Création du nouveau fichier en .md
                File page = new File(path + "/" + filePage.getName() + ".md");
                boolean isCreated = false;

                if (page.exists()) {
                    System.out.println("Le fichier existe déjà");
                    return 1;
                } else {
                    isCreated = page.createNewFile();
                }

                if (isCreated) {
                    System.out.println("La page voulue, " + page.getName() + ", a été créée");

                    // Template d'une page
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
            System.out.println("Les paramètres -f ou/et -d n'ont pas été précisés !");
            return 1;
        }
    }

    /**
     * Getter qui renvoie le chemin jusqu'au répertoire avec les métadonnées
     * @return chemin www/mon/site/metadonnee
     */
    public String getMetaPath() {
        return path;
    }
}

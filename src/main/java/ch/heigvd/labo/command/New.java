package ch.heigvd.labo.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;


@Command(name = "new", description ="Création d'une nouvelle page")
public class New implements Callable<Integer> {

    private final String DIR_RACINE = "www/";
    private final String TEMPLATE = "<!--Template pour une page, à modifier comme vous" +
            " le souhaiter ! (format Markdown) \n" +
            "Syntaxe utile: \n *italique*\n **gras**\n tableau 2 lignes 2 colonnes:\n" +
            "|      |      |\n" +
            "| ---- | ---- |\n" +
            "|      |      | \n" +
            "`code` \n bloc de code: \n ````html \n une image (ne pas oublier de copier l'image dans le répértoire" +
            " ../metadonnee/image.png:\n ![Description](./image.png)-->\n\n" +
            "> titre:\n\n" + "> auteur:\n\n" + "> date:\n\n" + "-----\n" +
            "#TITRE 1\n" + "##TITRE 2\n" + "###TITRE 3\n" +
            "Contenu de l'article\n\n" + "![Une image](./image.png)\n";

    private static String metaPath = "";

    @Override public Integer call() throws IOException {
        metaPath = "";
        File dir = new File(DIR_RACINE);
        searchPath(dir);

        // Création du répertoire avec toutes les métadonnées.
        if(!metaPath.contains("metadonnee")) {
            metaPath += "/metadonnee/";
            File metadonnee = new File(metaPath);
            metadonnee.mkdirs();
        }

        try {
            // Création du nouveau fichier en .md
            File page = new File(metaPath + "/" + file.getName() + ".md");
            boolean isCreated = false;

            if(page.exists()){
                System.out.println("Le fichier existe déjà");
                return 1;
            } else {
                isCreated = page.createNewFile();
            }

            if(isCreated){
                System.out.println("La page voulue, " + page.getName() + ", a été créée");

                // Template d'une page
                FileWriter writing = new FileWriter(page, true);
                PrintWriter printing = new PrintWriter(writing);
                printing.append(TEMPLATE);
                printing.close();

            } else {
                System.out.println("Erreur, le fichier n'a pas pu être créé");
                return 1;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }
    @CommandLine.Parameters(paramLabel = "FILE", description = "répertoire pour site statique")
    File file;

    /**
     * Fonction qui va chercher l'emplacement du site statique
     * @param file Répertoire racine "www/"
     */
    public void searchPath(File file) {
        File[] list = file.listFiles();

        if (list != null) {
            if(list.length < 1 || list[0].getName().contains(".md") && list[0].getParent().contains("metadonnee")) {
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

    /**
     * Getter qui renvoie le chemin jusqu'au répertoire avec les métadonnées
     * @return chemin www/mon/site/metadonnee
     */
    public String getMetaPath() {
        return metaPath;
    }
}

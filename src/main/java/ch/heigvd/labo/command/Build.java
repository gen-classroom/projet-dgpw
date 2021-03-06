package ch.heigvd.labo.command;

import static ch.heigvd.labo.Utility.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import ch.heigvd.labo.Utility;
import ch.heigvd.labo.fileWatcher.FileWatcher;
import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openjdk.jmh.annotations.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;

// Outils CommonMark
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Command(name = "build", description ="Compile un site statique")

public class Build implements Callable<Integer> {

    @CommandLine.Option(names = "-d", description = "Répertoire du site statique")
    private File siteDir;


    @CommandLine.Option(names = "--watch", description = "Contrôle continu")
    private boolean watch;

    @Override public Integer call() throws IOException {
        if(siteDir == null) {
            System.out.println("Merci de renseigner le nom du répertoire à créer : \n-d [nom du répertoire]");
            return 1;
        }

        File dir = new File(DIR_ROOT + siteDir.getPath());
        // Vérifie que le répertoire du site est existant
        if (!dir.exists()) {
            System.out.format("Impossible d'accéder au répertoire %s. Celui-ci est inexistant.\n", dir.getName());
            return 1;
        }

        File dirBuild = new File(dir.getAbsolutePath() + "/build");

        // Teste si le repertoire build existe déjà et le supprime
        if (dirBuild.exists()) {
            // Appel fonction clean
            new CommandLine(new Clean()).execute("-d", siteDir.getPath());
        }
        else{
            // Création du répertoire "template" pour y insérer les fichiers .html
            try {
                FileUtils.copyDirectoryToDirectory(new File("src/main/resources"), dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dirBuild.mkdir();

        // Appel de la classe pour observer modifications sur système de fichiers
        //FileWatcher fileWatcher = new FileWatcher(siteDir + "/metadonnee");
        // Si le paramètre --watch est ajouté
        if(watch){
            Path path = Paths.get(dir.getPath());
            FileWatcher fw = new FileWatcher(path);
            int haveEvent = 0;
            while(true) {
                haveEvent = fw.processEvents();

                // Lorsqu'un fichier a été créé ou supprimé
                if(haveEvent == CREATE || haveEvent == DELETE){
                    updateMenu(dir);
                    buildFile(dirBuild, dir);
                }

                // Lorsqu'un contenu de fichier est modifié
                if(haveEvent == MODIFY) {
                    buildFile(dirBuild, dir);
                }
            }
        }
        else {
            // Build normal
            updateMenu(dir);
            return buildFile(dirBuild, dir);
        }
    }

    /**
     * Fonction qui effectue le build
     * @param dirBuild Répertoire build du site statique
     * @param dir Répertoire actuel
     * @return Retourne 1 s'il y a eu une erreur dans l'exécution
     */
    private int buildFile(File dirBuild, File dir){
        try {
            // Création de la structure build
            boolean creationStructure = this.listDirectory(dir, dirBuild);
            // Teste si la structure a pu être recréée
            if (!creationStructure) {
                System.out.format("La structure n'a pas pu être créée correctement.");
                return 1;
            }
        } catch(IOException e){
            e.printStackTrace();
            return 1;
        }

        System.out.format("Build - Compilation terminée !\n");
        return 0;

    }

    /**
     * Fonction qui permet de mettre à jours les menus.html
     * @param dir Répertoire actuel
     * @return Retourne 1 s'il y a eu une erreur dans l'exécution
     */
    private int updateMenu(File dir){
        try{
            // Création des menus pour les fichiers de metadonnee et le fichier index
            FileUtils.copyDirectoryToDirectory(new File("src/main/resources"), dir);

            // Ajout les liens des pages html
            boolean menu = configMenu(dir, "/resources/","menu.html");
            if (!menu) return 1;
            boolean menuIndex = configMenu(dir, "/resources/","menuIndex.html");
            if (!menuIndex) return 1;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Fonction qui va appeler la méthode de construction du menu
     * @param dir Répertoire actuel
     * @param pathMenu Chemin jusqu'au menu
     * @return Retourne vrai si il a été possible de créé menu.html
     * @throws IOException
     */
    private boolean configMenu(File dir, String pathMenu, String name) throws IOException {
        File menuFile = new File(dir + pathMenu+name);

        boolean menu = this.constructMenu(dir, menuFile);
        // Teste si le menu a pu être créé
        if (!menu){
            System.out.format("Le fichier menu.html n'a pas pu être créé correctement.");
            return false;
        }

        // Ecriture de la fermeture de la liste
        BufferedWriter writer = new BufferedWriter(new FileWriter(menuFile, true));
        writer.write("</ul>\n");
        writer.flush();
        writer.close();

        return true;
    }

    /**
     * Fichier qui construit le menu.html
     * @param root Répertoire dans lequel on se trouve
     * @param menu Fichier menu.html
     * @return Retourne vrai si la construction a réussi
     * @throws IOException
     */
    private boolean constructMenu(File root, File menu) throws IOException {
        File[] files = root.listFiles();
        if (files != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(menu, true));
            writer.write("\n");
            for (File file : files) {
                String fileName = file.getName();
                if (file.isDirectory() && !fileName.equals("build") && !fileName.equals("resources")) {
                    File newRoot = new File(root.getAbsolutePath() + "/" + fileName);
                    this.constructMenu(newRoot, menu);
                } else if (FilenameUtils.getExtension(fileName).equals("md") && !fileName.equals("index.md")) {
                    String page = fileName.substring(0, file.getName().length() - 3);
                    String line;

                    if(menu.getName().equals("menuIndex.html")){
                        line = "\t<li><a href=\"metadonnee/" + page + ".html\">" + page + "</a></li>\n";
                    } else {
                        line = "\t<li><a href=\"./" + page + ".html\">" + page + "</a></li>\n";
                    }

                    writer.write(line);
                    writer.flush();
                }
            }
            writer.close();

        } else {
            return false;
        }
        return true;
    }

    /**
     *
     * @param root - racine du répertoire
     * @param build - répertoire équivalent à la racine dans le répertoire build
     * @return - booléen permettant de savoir si tout s'est bien passé ou non
     * @throws IOException
     */
    public boolean listDirectory(File root, File build) throws IOException {
        File[] files = root.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                // Création des répertoires
                if (file.isDirectory() && !fileName.equals("build") && !fileName.equals("resources")) {
                    String dirName = fileName;

                    File dirBuild = new File(build.getAbsolutePath() + "/" + dirName);

                    if(!dirBuild.exists()){
                        boolean creationDir = dirBuild.mkdir();
                        if (!creationDir) {
                            System.out.println("Echec de création du répertoire build");
                            return false;
                        }
                    }

                    File newRoot = new File(root.getAbsolutePath() + "/" + dirName);
                    this.listDirectory(newRoot, dirBuild);
                    // Conversion des fichiers md en html
                } else if (FilenameUtils.getExtension(fileName).equals("md")) {
                    System.out.print("Conversion " + fileName + ": ");
                    File htmlFile = new File(build.getAbsolutePath() + "/" + fileName.substring(0, file.getName().length() - 3) + ".html");

                    // Création du fichier html
                    if(htmlFile.exists()){
                        if(!htmlFile.delete()){
                            System.out.println("Echec de suppression du fichier html");
                            return false;
                        }
                    }

                    if (!htmlFile.createNewFile()) {
                        System.out.println("Echec de création du fichier html");
                        return false;
                    }

                    // Appel à HandleBars pour le générateur de template
                    boolean handlebars = handleBars(file, htmlFile);
                    if(!handlebars){
                        System.out.format("Handlebars a échoué lors du build");
                        return false;
                    }

                    // Copie des autres fichiers (image par exemple)
                } else if (!FilenameUtils.getExtension(fileName).equals("yaml") && !fileName.equals("build") && !fileName.equals("resources") && !fileName.endsWith("~")) {
                    File newFile = new File(build.getAbsolutePath() + "/" + fileName);
                    FileUtils.copyFile(file, newFile);
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Fonction qui retourne le header et le contenu d'un fichier
     * @param file Fichier a parser
     * @return header et contenu
     */
    private Map<String,String> getParameters(File file) {
        Map<String,String> map = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
            String line;
            StringBuilder content = new StringBuilder();
            boolean header = false;
            // Lecture du fichier ligne par ligne
            while((line = reader.readLine()) != null) {
                if(line.contains("==")){
                    // Séparation du header et contenu
                    header = true;
                }
                else if(header){
                    // Contenu du fichier
                    content.append(line).append("\n");
                }else {
                    // Header du fichier -> title, ...
                    String[] s = line.split(":");
                    map.put(s[0], s[1]);
                }
            }
            reader.close();
            if(header) {
                map.put("content", String.valueOf(content));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Fonction qui utilise HandleBars et gèrent l'injection des données dans le fichier html
     * @param file Fichier à injecter dans la page html finale
     * @param htmlFile Fichier html
     * @return Retourne vrai si la passage dans handleBars a fonctionné
     */
    private boolean handleBars(File file,File htmlFile) {

        String fileName = file.getName();
        TemplateLoader loader = new FileTemplateLoader("www/"+ siteDir.getPath() + "/resources",".html");
        com.github.jknack.handlebars.Handlebars handlebars = new com.github.jknack.handlebars.Handlebars(loader);
        handlebars.setPrettyPrint(true);

        // Conversion du fichier md en fichier html
        handlebars.registerHelper("convertMd", new Helper<String>() {
            @Override
            public Object apply(String s, Options options) throws IOException {
                //Convert page.md to html
                Parser parser = Parser.builder().build();
                Node document = parser.parse(s);
                HtmlRenderer renderer = HtmlRenderer.builder().build();

                return renderer.render(document);
            }
        });

        try {
            var template = handlebars.compile("layout");
            if(fileName.equals("index.md")){
                template = handlebars.compile("layoutIndex");
            }

            Map<String,Map<String,String>> elements = new HashMap<>();

            Map<String,String> config = getParameters(new File("www/"+ siteDir.getPath() + "/config.yaml"));
            Map<String,String> page = getParameters(file);

            elements.put("config",config);
            elements.put("page", page);

            var context = Context
                    .newBuilder(elements)
                    .resolver(MapValueResolver.INSTANCE)
                    .build();

            var result = template.apply(context);
            Files.writeString(Path.of(htmlFile.getAbsolutePath()), result);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
package ch.heigvd.labo.command;
import static ch.heigvd.labo.Utility.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.HashMap;

import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;

// Outils CommonMark
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Command(name = "build", description ="Compile un site statique")
public class Build implements Callable<Integer> {

    @CommandLine.Option(names = "-d", description = "Répertoire du site statique")
    static File siteDir;

    @Override public Integer call() throws IOException {
        if(siteDir != null){
            File dir = new File(DIR_ROOT + siteDir.getPath());

            //Vérifie que le répertoire du site est existant
            if (!dir.exists()) {
                System.out.format("Impossible d'accéder au répertoire %s. Celui-ci est inexistant.\n", dir.getName());
                return 1;
            }

            File dirBuild = new File(dir.getAbsolutePath() + "/build");
            // Teste si le repertoire build exite déjà et le supprime
            if (dirBuild.exists()){
                // Appel fonction clean
                new CommandLine(new Clean()).execute("-d", siteDir.getPath());
            }

            // Création du répertoire "template" pour y insérer les fichiers .html
            try {
                FileUtils.copyDirectoryToDirectory(new File("src/main/resources"), dir);
            } catch (IOException e) {
                e.printStackTrace();
            }

            boolean creationBuild = dirBuild.mkdir();
            // Teste si le répertoire build s'est bien créé
            if(!creationBuild){
                System.out.format("Le répertoire build ne s'est pas créé.");
                return 1;
            } else {
                File menuFile = new File(dir + "/resources/menu.html");
                boolean menu = this.constructMenu(dir, menuFile);
                // Teste si le menu a pu être créé
                if (!menu){
                    System.out.format("Le fichier menu.html n'a pas pu être créé correctement.");
                    return 1;
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(menuFile, true));
                writer.write("</ul>\n");
                writer.flush();
                writer.close();

                boolean creationStructure = this.listDirectory(dir, dirBuild);
                // Teste si la structure a pu être recréée
                if (!creationStructure){
                    System.out.format("La structure n'a pas pu être créée correctement.");
                    return 1;
                }
            }
            System.out.format("Compilation terminée !\n");
            return 0;
        } else {
            System.out.println("Merci de renseigner le nom du répertoire à créer : \n-d [nom du répertoire]");
            return 1;
        }
    }

    /**
     *
     * @param root
     * @param menu
     * @return
     * @throws IOException
     */
    public boolean constructMenu(File root, File menu) throws IOException {
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
                    String line = "\t<li><a href=\"/" + page + ".html\">" + page + "</a></li>\n";

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
                    System.out.print("Creation " + dirName + ": ");

                    File dirBuild = new File(build.getAbsolutePath() + "/" + dirName);
                    boolean creationDir = dirBuild.mkdir();
                    if (!creationDir) {
                        System.out.println("Echec");
                        return false;
                    }

                    System.out.println("Reussi");
                    File newRoot = new File(root.getAbsolutePath() + "/" + dirName);
                    this.listDirectory(newRoot, dirBuild);
                    // Conversion des fichiers md en html
                } else if (FilenameUtils.getExtension(fileName).equals("md")) {
                    System.out.print("Conversion " + fileName + ": ");
                    File htmlFile = new File(build.getAbsolutePath() + "/" + fileName.substring(0, file.getName().length() - 3) + ".html");
                    // Création du fichier html
                    if (!htmlFile.createNewFile()) {
                        System.out.println("Echec");
                        return false;
                    }

                    String content = this.convertFile(file, htmlFile);
                    System.out.println("--------------" + content + "-------------");

                    //************************************************************* Handlebars
                    TemplateLoader loader = new FileTemplateLoader("www/"+ siteDir.getPath() + "/resources",".html");
                    com.github.jknack.handlebars.Handlebars handlebars = new com.github.jknack.handlebars.Handlebars(loader);
                    handlebars.setPrettyPrint(true);

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
                        Map<String,Map<String,String>> elements = new HashMap<>();

                        Map<String,String> config = getParameters(new File("www/"+ siteDir.getPath() + "/config.yaml"));
                        Map<String,String> page = getParameters(file);

                        String fileString = Files.readString(Path.of(file.getAbsolutePath()));
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
                    }

                    //************************************************************* Handlebars

                    System.out.println("Reussi");



                    // Copie des autres fichiers (image par exemple)
                } else if (!FilenameUtils.getExtension(fileName).equals("yaml") && !fileName.equals("build") && !fileName.equals("resources")) {
                    System.out.println("Copie " + fileName + ": ");
                    File newFile = new File(build.getAbsolutePath() + "/" + fileName);
                    FileUtils.copyFile(file, newFile);
                    System.out.println("Reussi");
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private Map<String,String> getParameters(File file) {
        Map<String,String> map = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
            String line;
            StringBuilder content = new StringBuilder();
            boolean header = false;
            while((line = reader.readLine()) != null) {

                while(line != null && !line.equals("==")) {
                    // On récupère le nom du champ et sa valeur (p.ex "auteur" : "Michel J.")
                    String[] s = line.split(":");
                    map.put(s[0], s[1]);
                    line = reader.readLine();
                    header = true;
                }
                // On récupère le contenu de l'article
                if(!line.equals("==")){
                    content.append(line);
                }

            }
            if(header) {
                map.put("content", String.valueOf(content));
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Fonction convertissant le markdown en html
     * @param fileToConvert - fichier md
     * @param newFile - fichier html
     * @throws IOException
     */
    public String convertFile(File fileToConvert, File newFile) throws IOException {
        Path filePath = Path.of(fileToConvert.getAbsolutePath());
        String fileString = Files.readString(filePath);

        Parser parser = Parser.builder().build();
        Node document = parser.parse(fileString);
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(document);
    }

}
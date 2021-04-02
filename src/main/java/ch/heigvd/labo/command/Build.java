package ch.heigvd.labo.command;

import java.util.concurrent.Callable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

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
    static final String DIR_ROOT = "www/";
    static final String DIR_TO_COMPILE = "/metadonnee";

    @CommandLine.Option(names = "-d", description = "Répertoire du site statique")
    static File siteDir;

    @Override public Integer call(){
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
                try {
                    FileUtils.deleteDirectory(dirBuild);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            boolean creationBuild = dirBuild.mkdir();
            // Teste si le répertoire build s'est bien créé
            if(!creationBuild){
                System.out.format("Le répertoire build ne s'est pas créé.");
                return 1;
            } else {
                boolean creationStructure = this.listDirectory(dir, dirBuild);
                // Teste si la structure a pu être recréée
                if (!creationStructure){
                    System.out.format("La structure n'a pas pu être créée correctement.");
                    return 1;
                }
            }

            return 0;
        } else {
            System.out.println("Le paramètre -d n'a pas été précisé !");
            return 1;
        }
    }

    public boolean listDirectory(File root, File build){
        File[] files = root.listFiles();
        if (files != null) {
            for (File file : files) {
                // Création des répertoires
                if (file.isDirectory() && !file.getName().equals("build")) {
                    System.out.println("Dossier: " + file.getPath());
                // Conversion des fichiers md en html
                } else if (FilenameUtils.getExtension(file.getName()).equals("md")) {
                    System.out.println("  Fichier: " + file.getName());
                // Copie des autres fichiers (image par exemple)
                } else if (!FilenameUtils.getExtension(file.getName()).equals("yaml")) {
                    System.out.println("  Fichier: " + file.getName());
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean convertFile(){
        Parser parser = Parser.builder().build();
        Node document = parser.parse("Yo");
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        System.out.format(renderer.render(document));
        return true;
    }
}
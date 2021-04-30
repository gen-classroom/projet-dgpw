package ch.heigvd.labo.command;

import java.io.File;
import java.util.concurrent.Callable;

import ch.heigvd.labo.Utility;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "serve", description ="Compilation du site dans un navigateur Web")
public class Serve implements Callable {
    @CommandLine.Option(names = "-new", description = "")
    static Boolean newOption = false;

    @CommandLine.Option(names = "-init", description = "")
    static Boolean initOption  = false;

    @CommandLine.Option(names = "-f", description = "Nom de la page")
    static File filePage;

    @CommandLine.Option(names = "-d", description = "Répertoire du site statique")
    static File dirStatic;

    @Override public Integer call(){
        int result = 0;
        if(dirStatic != null) {
            // Tous les autres tests sont effectués dans les classes des commandes directement
            // Nous appelons uniquement la commande en testant la valeur de retour de la commande.
            if (initOption) {
                result = new CommandLine(new Init()).execute("-d", dirStatic.getPath());
                if(result != 0) {
                    System.out.println("Une erreur s'est produite durant le new");
                    return 2;
                }
            }

            else if (newOption) {
                if(filePage != null) {
                    result = new CommandLine(new New()).execute("-d", dirStatic.getPath(), "-f", filePage.getName());
                    if (result != 0) {
                        System.out.println("Une erreur s'est produite durant le init");
                        return 2;
                    }
                }
                else {
                    System.out.println("Merci de renseigner le nom du répertoire et/ou du fichier à créer : \n-d [nom du répertoire] \n-f [nom du fichier]");
                    return 1;
                }
            }

            result = new CommandLine(new Build()).execute("-d", dirStatic.getPath());
            if(result != 0) {
                System.out.println("Une erreur s'est produite durant le build");
                return 2;
            }
            System.out.println("Vous pouvez accéder au site web via " + Utility.LOCALHOST+Utility.DIR_ROOT+dirStatic.getPath()+Utility.DIR_BUILD +"/index.html");
            return 0;
        }
        System.out.println("Merci de renseigner le nom du répertoire et/ou du fichier à créer : \n-d [nom du répertoire] \n-f [nom du fichier]");
        return 1;
    }
}

package ch.heigvd.labo.command;


import java.io.File;
import java.util.concurrent.Callable;

import ch.heigvd.labo.Utility;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "easyuse", description ="Commande qui facilite l'utilisation du site")
public class EasyUse implements Callable {
    @CommandLine.Option(names = "-new", description = "Création d'une nouvelle page")
    private Boolean newOption;

    @CommandLine.Option(names = "-init", description = "Création du site statique")
    private Boolean initOption;

    @CommandLine.Option(names = "-f", description = "Nom de la page")
    private File filePage;

    @CommandLine.Option(names = "-d", description = "Répertoire du site statique")
    private File dirStatic;

    @CommandLine.Option(names = "--watch", description = "Contrôle continu")
    private boolean watch;

    @Override public Integer call(){
        int result = 0;
        if(dirStatic != null) {
            // Tous les autres tests sont effectués dans les classes des commandes directement
            // Nous appelons uniquement la commande en testant la valeur de retour de la commande.
            if (initOption != null && initOption) {
                result = new CommandLine(new Init()).execute("-d", dirStatic.getPath());
                if(result != 0) {
                    System.out.println("Une erreur s'est produite durant le init");
                    return 1;
                }
            }

            else if (newOption != null && newOption) {
                if(filePage != null) {
                    result = new CommandLine(new New()).execute("-d", dirStatic.getPath(), "-f", filePage.getName());
                    if (result != 0) {
                        System.out.println("Une erreur s'est produite durant le new");
                        return 2;
                    }
                }
                else {
                    System.out.println("Merci de renseigner le nom du répertoire et/ou du fichier à créer : \n-d [nom du répertoire] \n-f [nom du fichier]");
                    return 3;
                }
            }

            if(watch){
                result = new CommandLine(new Build()).execute("-d", dirStatic.getPath(), "--watch");
            }
            else{
                result = new CommandLine(new Build()).execute("-d", dirStatic.getPath());
            }

            if(result != 0) {
                System.out.println("Une erreur s'est produite durant le build");
                return 4;
            }
            return 0;
        }
        System.out.println("Merci de renseigner le nom du répertoire et/ou du fichier à créer : \n-d [nom du répertoire] \n-f [nom du fichier]");
        return 3;
    }
}

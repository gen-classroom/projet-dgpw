package ch.heigvd.labo.command;

import java.io.File;
import java.util.concurrent.Callable;

import ch.heigvd.labo.Utility;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "serve", description ="Compilation du site dans un navigateur Web")
public class Serve implements Callable {
    @CommandLine.Option(names = "-new", description = "")
    private Boolean newOption;

    @CommandLine.Option(names = "-init", description = "")
    private Boolean initOption;

    @CommandLine.Option(names = "-f", description = "Nom de la page")
    private File filePage;

    @CommandLine.Option(names = "-d", description = "Répertoire du site statique")
    private File dirStatic;

    @Override public Integer call(){
        return 1;
    }
}

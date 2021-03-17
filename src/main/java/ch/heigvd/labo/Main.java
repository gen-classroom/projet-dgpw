package ch.heigvd.labo;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ch.heigvd.labo.command.Build;
import ch.heigvd.labo.command.Clean;
import ch.heigvd.labo.command.New;
import ch.heigvd.labo.command.Serve;


@Command(
        name="Main",
        description = "Programme DGPW - Website Generator" ,
        subcommands = {New.class, Clean.class, Build.class, Serve.class} )

public class Main implements Callable<Integer>{
    @Option(names = { "--version", "-v" }, description = "Display the version and exit")
    private boolean version;

    public static void main(String... args) {
    int exitCode = new CommandLine(new Main()).execute(args);
    System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception{
        if(version) {
            //Affichage de la version de notre application contenue dans pom.xml
            System.out.println("DGPW version " + Main.class.getPackage().getImplementationVersion());
        }
        else{
            CommandLine.usage(this, System.out);
        }
        return 0;
    }



}
package ch.heigvd.labo;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
//import ch.heigvd.labo.command.Build;
//import ch.heigvd.labo.command.Clean;
import ch.heigvd.labo.command.New;
//import ch.heigvd.labo.command.Serve;

@Command(
        name="Main",
        description = "New picocli command" //,
        /*subcommands = {New.class, Clean.class, Build.class, Serve.class}*/  )

public class Main implements Callable<Integer>{

    public static void main(String... args) {
    int exitCode = new CommandLine(new Main()).execute(args);
    System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception{
        CommandLine.usage(this, System.out);
        return 0;
    }

}
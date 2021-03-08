package ch.heigvd.labo.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;

@Command(name = "build", description ="Build .... ???")
public class Build implements Callable {
    @Override public Integer call(){
        System.out.printf("build");
        return 1;
    }
}
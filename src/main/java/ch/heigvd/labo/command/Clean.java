package ch.heigvd.labo.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;

@Command(name = "clean", description ="Clean .... ???")
public class Clean implements Callable<Integer> {
    @Override public Integer call(){
        System.out.printf("clean");
        return 1;
    }
}
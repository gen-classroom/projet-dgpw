package ch.heigvd.labo.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;

@Command(name = "new", description ="Création d'une nouvelle page")
public class New implements Callable<Integer> {

    @Override public Integer call(){
        System.out.printf("new");
        return 1;
    }
}

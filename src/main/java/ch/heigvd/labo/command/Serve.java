package ch.heigvd.labo.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;

@Command(name = "serve", description ="Serve .... ???")
public class Serve implements Callable {
    @Override public Integer call(){
        System.out.printf("serve");
        return 1;
    }
}

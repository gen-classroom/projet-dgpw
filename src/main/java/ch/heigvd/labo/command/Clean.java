package ch.heigvd.labo.command;

import java.io.File;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "clean", description ="Nettoie le répertoire build du site statique")
public class Clean implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "FILE", description = "répertoire du site statique qu'on veut clean")
    File dir;

    @Override public Integer call(){
        
        System.out.printf("clean");
        return 1;
    }
}
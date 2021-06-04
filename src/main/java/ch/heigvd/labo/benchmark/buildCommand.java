package ch.heigvd.labo.benchmark;

import ch.heigvd.labo.command.Build;
import ch.heigvd.labo.command.Init;
import ch.heigvd.labo.command.New;
import org.apache.commons.io.FileUtils;
import org.openjdk.jmh.annotations.*;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class buildCommand {

    static final String DIR_TO_BUILD = "www/test_benchmark";

    @Setup
    public static void setUp() throws IOException {
        File dir = new File(DIR_TO_BUILD);
        initializing();
        gettingNewPage();
        building();
    }

    public static void initializing(){
        // init a directory
        new CommandLine(new Init()).execute("-d", "test_benchmark");
    }

    public static void gettingNewPage(){
        // create a page
        new CommandLine(new New()).execute("-f", "mapremierepage", "-d", "test_benchmark");
    }

    @Benchmark
    public static void building(){
        // build the website
        new CommandLine(new Build()).execute("-d", "test_benchmark");
    }



}

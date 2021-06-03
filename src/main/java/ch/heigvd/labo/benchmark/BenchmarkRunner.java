package ch.heigvd.labo.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .forks(1)
                .warmupIterations(1)
                .measurementIterations(1)
                .build();

        new Runner(options).run();


    }
}

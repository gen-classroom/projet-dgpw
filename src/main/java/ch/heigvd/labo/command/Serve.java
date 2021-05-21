package ch.heigvd.labo.command;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

import ch.heigvd.labo.Utility;
import ch.heigvd.labo.server.StaticHttpHandler;
import com.sun.net.httpserver.HttpServer;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import static ch.heigvd.labo.Utility.DIR_ROOT;

@Command(name = "serve", description ="Servir un site statique")
public class Serve implements Callable {

    @CommandLine.Option(names = "-d", description = "Répertoire du site statique")
    private File dirStatic;

    private static final int PORT = 8080;
    private HttpServer server;

    @Override public Integer call() {
        try {
            String SITE = DIR_ROOT + dirStatic.getPath();
            String BUILD = DIR_ROOT + dirStatic.getPath() + "/build";

            File dirSite = new File(SITE);
            File dirBuild = new File(BUILD);

            // Le site statique n'existe pas, on lance une erreur
            if(!dirSite.exists()){
                System.out.println("Le site statique n'existe pas");
                return 1;
            }
            // Si le site n'a pas encore été buildé, on le build directement ici
            if(!dirBuild.exists()) {
                System.out.println("Le site statique n'a pas encore été buildé");
                new CommandLine(new Build()).execute("-d",  dirStatic.getPath());
            }

            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            System.out.println("Vous pouvez vous connectez sur votre site statique : http://localhost:8080/index.html");
            server.createContext("/", new StaticHttpHandler(BUILD));

            // Connexion au serveur
            server.start();
            while (true){}

        } catch (IOException e) {
            e.printStackTrace();
        }
      return 0;
    }
}

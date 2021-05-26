package ch.heigvd.labo.fileWatcher;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class FileWatcher {
    private String dirStatic;

    public FileWatcher(String dirStatic) {
        this.dirStatic = dirStatic;
    }

    //public static void main(String... args) {
    public void watch() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Map<WatchKey, Path> keyMap = new HashMap<>();
            Path path = Paths.get("www/mon/site/metadonnee");
            keyMap.put(path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY),
                    path);

            WatchKey watchKey;
            do {
                watchKey = watchService.take();
                Path eventDir = keyMap.get(watchKey);

                for(WatchEvent<?> event: watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path eventPath = (Path) event.context();
                    System.out.println(eventDir + ": " + kind + ": " + eventPath);
                }
            } while(watchKey.reset());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

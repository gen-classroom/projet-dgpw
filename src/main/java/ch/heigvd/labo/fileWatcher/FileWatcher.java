package ch.heigvd.labo.fileWatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class FileWatcher {

    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;


    public FileWatcher(Path dir) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();

        walkAndRegisterDirectories(dir);
    }


    private void registerDirectory(Path dir) throws IOException
    {
        WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        keys.put(key, dir);
    }

    private void walkAndRegisterDirectories(final Path start) throws IOException {

        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirectory(dir);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public int processEvents() {

        WatchKey key;
        try {
            key = watcher.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        }

        Path dir = keys.get(key);
        if (dir == null) {
            System.err.println("WatchKey not recognized!!");
            return 0;
        }

        for (WatchEvent<?> event : key.pollEvents()) {
            @SuppressWarnings("rawtypes")
            WatchEvent.Kind kind = event.kind();

            @SuppressWarnings("unchecked")
            Path name = ((WatchEvent<Path>)event).context();
            Path child = dir.resolve(name);


            System.out.format("%s: %s\n", event.kind().name(), child);

            if(child.toString().endsWith(".md")){

                boolean valid = key.reset();
                    if (!valid) {
                        keys.remove(key);

                        if (keys.isEmpty()) {
                            return 0;
                        }
                    }

                    if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY){
                        System.out.println("changement cc");
                        return 1;
                    }

                    if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE){
                        System.out.println("suppression ou création cc");
                        return 2;
                    }

                    if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE){
                        System.out.println("suppression ou création cc");
                        return 3;
                    }
                }

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child)) {
                            walkAndRegisterDirectories(child);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        return 0;
    }

}


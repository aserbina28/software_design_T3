package nl.vu.cs.softwaredesign;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
public class Collection {
    private HashMap<File, Metadata> fileMap = new HashMap<File, Metadata>();
    public Collection() {
        this.size = 0;
    }

    public int getSize(){
        return this.fileMap.size();
    }

    public void add(File f){
        try {
            BasicFileAttributes fileAttr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
            long size = fileAttr.size();
            String name = f.getName();
            //Optional<String> type = getFileExtension(name);

            Metadata m = new Metadata(name, size);
            fileMap.put(f, m);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(File f) {
        fileMap.remove(f);
    }

    public ArrayList<Metadata> getMetaData() {

        return new ArrayList<Metadata>(fileMap.values());

    }

//    public Archive compress(CompressionStrategy c){
//       return c.compress();
//    }

    public void extractToDirectory(File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException("Provided directory does not exist.");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("This path is not a directory.");
        }

        for (File file : new ArrayList<>(fileMap.keySet())) {
            File destination = new File(directory, file.getName());
            try {
                Files.move(file.toPath(), destination.toPath());
                Metadata metadata = fileMap.remove(file);
                fileMap.put(destination, metadata);
            } catch (IOException e) {
                System.err.println("File move failed");
            }
        }
    }


}
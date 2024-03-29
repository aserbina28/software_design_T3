package nl.vu.cs.softwaredesign;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
public class Collection {
    private int size;
    private HashMap<File, Metadata> fileMap = new HashMap<File, Metadata>();
    public Collection(){
        this.size = 0;
    }

//    /**
//     * Code sourced from baeldung.com
//     * @param filename
//     * @return
//     */
//    public Optional<String> getFileExtension(String filename) {
//        return Optional.ofNullable(filename)
//                .filter(f -> f.contains("."))
//                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
//    }

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
//        //compresses file
//
//        return new Archive(this);
//    }


}

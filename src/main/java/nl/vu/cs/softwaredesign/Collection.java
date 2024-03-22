package nl.vu.cs.softwaredesign;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
public class Collection {
    int size;
    HashMap<File, Metadata> fileMap = new HashMap<File, Metadata>();
    public Collection(){
        size = 0;
    }

    /**
     * Code sourced from baeldung.com
     * @param filename
     * @return
     */
    public Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public void add(File f){
        try {
            BasicFileAttributes fileAttr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
            long size = fileAttr.size();
            String name = f.getName();
            boolean isDir = fileAttr.isDirectory();
            Optional<String> type = getFileExtension(name);

            Metadata m = new Metadata(name, size, type);
            fileMap.put(f, m);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(File f) {
        fileMap.remove(f);
    }

    public ArrayList<Metadata> getMetaData() {

        ArrayList<Metadata> metas = new ArrayList<Metadata>(fileMap.values());

        return metas;
    }


}

/*This class serves as an intermediary between the user and the contents of an archive.
Although we could have chosen to create an Archive object directly from UserInterface,
we feel that this additional class will add organization to a potentially large number
of files and folders. Furthermore, we will need to perform an analysis of the files,
which would be much more difficult to do in a compressed form. Once a user has selected
the files they would like to add to an archive, a Collection object will be created
via UserInterface. The constructor will call getMetadata(), and this information will
later be passed on to the new Archive object formed by compress(), hence the associations
to both Metadata and Archive. Once the Archive is created, no further references to this
Collection object are necessary. The second use case of Collection will be to briefly store
 files from an archive after decryption and decompression. Note that this creates a new
 object, bearing no relation to the Collection object that created the archive. This is to
 avoid retaining the files and wasting memory.
Java.io.File: We will use Java’s File class to represent the multitude of files when not archived,
as well as to represent the compressed file. Because one or more instances of
 File compose a Collection, but can still exist independently, these two classes share an aggregation relationship.

*/
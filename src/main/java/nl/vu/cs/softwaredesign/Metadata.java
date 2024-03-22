package nl.vu.cs.softwaredesign;

import java.util.Optional;

public class Metadata {
    String name;
    long byteSize;
    Optional<String> fileType;

    public Metadata(String name, long size, Optional<String> fileType){
        this.name = name;
        this.byteSize = size;
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public long getByteSize() {
        return byteSize;
    }

    public Optional<String> getFileType() {
        return fileType;
    }
}

/**
 * Instances of the Metadata class will store the necessary information for the
 * “preview contents” feature. It stores the name, size (in bytes), and file type.
 * As mentioned in the Collection description, this data will be collected before
 * the files are compressed. Each file or folder will generate one Metadata object,
 * and a list of these objects will be stored in Archive’s metadata attribute, giving
 * rise to an aggregation relationship. When previewContents() is called, this list
 * will be returned and printed to the user through UserInterface. Maintaining this
 * information throughout the lifetime of an Archive object is a less complex
 * alternative than somehow extracting this information from the compressed file
 * upon request.
 */
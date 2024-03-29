package nl.vu.cs.softwaredesign;

import java.util.Optional;

public class Metadata {
    String name;
    long byteSize;
    Optional<String> fileType;

    public Metadata(String name, long size){
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
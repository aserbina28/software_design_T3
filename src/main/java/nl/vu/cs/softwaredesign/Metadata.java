package nl.vu.cs.softwaredesign;

public class Metadata {
    String name;
    long byteSize;
    //Optional<String> fileType;

    public Metadata(String name, long size){
        this.name = name;
        this.byteSize = size;
    }

    public String getName() {
        return name;
    }

    public long getByteSize() {
        return byteSize;
    }
}

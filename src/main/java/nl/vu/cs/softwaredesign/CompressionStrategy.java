package nl.vu.cs.softwaredesign;

import java.io.File;
import java.util.HashMap;

public abstract class CompressionStrategy {
    protected int compressionLevel;

    // Implementation in subclasses
    public abstract void compress(HashMap<File, Metadata> fileMap, String destination);
    // Implementation in subclasses
    public abstract void decompress(byte[] compressedData, String destination);

    // Getter for compression level
    public int getCompressionLevel(int compressionLevel) {
        return compressionLevel;
    }
    // Setter for compression level
    public void setCompressionLevel(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }
}

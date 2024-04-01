package nl.vu.cs.softwaredesign;

import java.io.File;
import java.util.HashMap;

public abstract class CompressionStrategy {
    protected int compressionLevel;

    public abstract byte[] compress(HashMap<File, Metadata> fileMap, String destination);
    public abstract void decompress(byte[] compressedData, String destination);

    public int getCompressionLevel(int compressionLevel) {
        return compressionLevel;
    }
    public void setCompressionLevel(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }
}

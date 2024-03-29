package nl.vu.cs.softwaredesign;

public abstract class compressionStrategy {
    protected int compressionLevel;

    // Implementation in subclasses
    public abstract Archive compress(nl.vu.cs.softwaredesign.Collection collection);
    // Implementation in subclasses
    public abstract void extractContents(Archive archive, String destination);

    // Getter for compression level
    public int getCompressionLevel(int compressionLevel) {
        return compressionLevel;
    }
    // Setter for compression level
    public void setCompressionLevel(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }
}

package nl.vu.cs.softwaredesign;
import java.util.Collection;

public class Zip extends CompressionStrategy {

    // Set compression level
    public Zip (int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    // Compresses a collection of objects into a ZIP archive format
    @Override
    public Archive compress(Collection<?> collection) {
        // TODO: Implement the actual ZIP compression logic here
        return new Archive(); // Replace with actual implementation.
    }
    // Extracts the contents of a ZIP archive to a specified destination
    @Override
    public void extractContents(String destination) {
        // TODO: Implement the actual ZIP extraction logic here. Not sure how to to do yet
    }
}
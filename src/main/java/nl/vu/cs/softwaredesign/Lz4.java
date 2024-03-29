package nl.vu.cs.softwaredesign;

import java.util.Collection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

public class Lz4 extends compressionStrategy {

    // Set compression level
    public Lz4 (int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    // Compresses a collection of objects into a ZIP archive format
    @Override
    public Archive compress(Collection<?> collection) {
        // LZ4Factory factory = new
        return new Archive(); // Replace with actual implementation.
    }
    // Extracts the contents of a ZIP archive to a specified destination
    @Override
    public void extractContents(Archive archive, String destination) {
        // TODO: Implement the actual ZIP extraction logic here. Not sure how to to do yet
    }
}
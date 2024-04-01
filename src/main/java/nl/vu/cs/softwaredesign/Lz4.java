package nl.vu.cs.softwaredesign;

import net.jpountz.lz4.*;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public class Lz4 extends CompressionStrategy {

    public Lz4(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    @Override
    public void compress(HashMap<File, Metadata> fileMap, String destination) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4Compressor compressor = factory.fastCompressor();

        try (ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream()) {
            for (File file : fileMap.keySet()) {
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                byte[] compressed = compressor.compress(fileBytes);

                // For simplicity, directly writing compressed bytes to the ByteArrayOutputStream
                byteOutStream.write(compressed);
            }

            // Save compressed data to file
            try (FileOutputStream fos = new FileOutputStream(destination)) {
                fos.write(byteOutStream.toByteArray());
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void decompress(byte[] compressedData, String destination) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4FastDecompressor decompressor = factory.fastDecompressor();

        try {

            byte[] restored = decompressor.decompress(compressedData, compressedData.length);

            try (FileOutputStream fos = new FileOutputStream(destination)) {
                fos.write(restored);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
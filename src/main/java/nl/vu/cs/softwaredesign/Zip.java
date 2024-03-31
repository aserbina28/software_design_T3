package nl.vu.cs.softwaredesign;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip extends CompressionStrategy {

    public Zip(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    @Override
    public void compress(HashMap<File, Metadata> fileMap, String destination) {
        try (ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutStream = new ZipOutputStream(byteOutStream)) {

            zipOutStream.setLevel(compressionLevel);

            for (File file : fileMap.keySet()) {
                ZipEntry entry = new ZipEntry(file.getName());
                zipOutStream.putNextEntry(entry);

                byte[] fileBytes = Files.readAllBytes(file.toPath());
                zipOutStream.write(fileBytes);

                zipOutStream.closeEntry();
            }

            zipOutStream.finish();
            byte[] compressedData = byteOutStream.toByteArray();

            // Save compressed data to file
            try (FileOutputStream fos = new FileOutputStream(destination)) {
                fos.write(compressedData);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void decompress(byte[] compressedData, String destination) {

        try (ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(compressedData))) {
            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                File outputFile = new File(destination, entry.getName());
                if (!entry.isDirectory()) {
                    // Ensure parent directories exist
                    outputFile.getParentFile().mkdirs();

                    // Extract the file
                    try (BufferedOutputStream byteOutStream = new BufferedOutputStream(new FileOutputStream(outputFile))) {
                        byte[] bytesIn = new byte[4096];
                        int read;
                        while ((read = zipIn.read(bytesIn)) != -1) {
                            byteOutStream.write(bytesIn, 0, read);
                        }
                    }
                } else {
                    // Ensure directory is created
                    outputFile.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
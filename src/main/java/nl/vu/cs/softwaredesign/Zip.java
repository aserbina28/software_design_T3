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
    public byte[] compress(HashMap<File, Metadata> fileMap, String destination) {
        try (ByteArrayOutputStream byOutstr = new ByteArrayOutputStream();
             ZipOutputStream zipOutsr = new ZipOutputStream(byOutstr)) {

            zipOutStream.setLevel(compressionLevel);

            for (File file : fileMap.keySet()) {
                ZipEntry entry = new ZipEntry(file.getName());
                zipOutStream.putNextEntry(entry);

                byte[] fileBytes = Files.readAllBytes(file.toPath());
                zipOutStream.write(fileBytes);

                zipOutStream.closeEntry();
            }

            zipOutsr.finish();
            byte[] compressedData = byOutstr.toByteArray();

            String fileName = "archive.zip";
            String fullPath = destination + File.separator + fileName;

            try (FileOutputStream fos = new FileOutputStream(fullPath)) {
                fos.write(compressedData);
            }
            return compressedData;

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void decompress(byte[] compressedData, String destination) {

        try (ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(compressedData))) {
            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                File outputFile = new File(destination, entry.getName());
                if (!entry.isDirectory()) {
                    outputFile.getParentFile().mkdirs();

                    try (BufferedOutputStream byteOutStream = new BufferedOutputStream(new FileOutputStream(outputFile))) {
                        byte[] bytesIn = new byte[4096];
                        int read;
                        while ((read = zipIn.read(bytesIn)) != -1) {
                            byteOutStream.write(bytesIn, 0, read);
                        }
                    }
                } else {
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
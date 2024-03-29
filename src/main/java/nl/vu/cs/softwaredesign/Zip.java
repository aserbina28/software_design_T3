package nl.vu.cs.softwaredesign;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip extends compressionStrategy {

    // Constructor set compression level
    public Zip(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    // Compresses a collection of objects into a ZIP archive format
    @Override
    public Archive compress(nl.vu.cs.softwaredesign.Collection collection) {
        // Prepare a byte array output stream to hold the compressed data
        try (ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutStream = new ZipOutputStream(byteOutStream)) {

            // Set the compression level
            zipOutStream.setLevel(compressionLevel);

            // Retrieve the metadata list from the collection
            ArrayList<Metadata> metadataList = collection.getMetaData();

            for (Metadata metadata : metadataList) {
                // For each file in the collection, create a zip entry
                ZipEntry entry = new ZipEntry(metadata.getName());
                zipOutStream.putNextEntry(entry);

                // Read the file's bytes and write them to the zip output stream
                File file = new File("/Users/jacobroberts/IdeaProjects/software_design_T3/test/" + metadata.getName());
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                zipOutStream.write(fileBytes);

                // Close the current entry
                zipOutStream.closeEntry();
            }

            // Finish the zip process and get the compressed data
            zipOutStream.finish();
            byte[] compressedData = byteOutStream.toByteArray();

            // Assuming the Archive constructor accepts a byte array
            // You might need to adjust this to fit your Archive class's constructor
            return new Archive(compressedData);

        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    // Extracts the contents of a ZIP archive to a specified destination
    @Override
    public void extractContents(Archive archive, String destination) {
        // Ensure the destination directory exists
        // If the directory does not exist, it's created.
        File destinationDirectory = new File(destination);
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }

        // Retrieve the compressed data from the archive.
        byte[] compressedData = archive.getCompressedData();

        // Initialize the streams for reading the compressed data.
        // ByteArrayInputStream reads the byte array as an InputStream.
        // ZipInputStream reads and decompresses the zip data from the input stream.
        try (ByteArrayInputStream byteInStream = new ByteArrayInputStream(compressedData);
             ZipInputStream zipInStream = new ZipInputStream(byteInStream)) {

            // Process each entry in the ZIP file
            ZipEntry zipEntry = zipInStream.getNextEntry();
            while (zipEntry != null) {
                // Construct a file path for current entry
                String filePath = destination + File.separator + zipEntry.getName();
                File newFile = new File(filePath);
                System.out.println("Extracting: " + newFile.getPath()); // Debugging print statement

                if (zipEntry.isDirectory()) {
                    // If the ZIP entry is a directory, then attempt to create it
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // If the ZIP entry is a file, make sure parent directories are created
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // Extract the file's contents to  specified location
                    // Contents are written from ZipInputStream to a FileOutputStream
                    try (FileOutputStream fileOutStream = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = zipInStream.read(buffer)) > 0) {
                            fileOutStream.write(buffer, 0, len);
                        }
                    }
                }
                // Close current ZIP entry and proceed to next
                zipInStream.closeEntry();
                zipEntry = zipInStream.getNextEntry();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

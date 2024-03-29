package nl.vu.cs.softwaredesign;

import java.io.*;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;
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
    public Archive compress(Collection<?> collection) {
        try {
            // Write compressed data into byte array in memory
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutStream = new ZipOutputStream(byteOutStream);
            zipOutStream.setLevel(compressionLevel); // Use the compression level set in the class

            // Iterate through each file and its metadata in the collection
            for (Map.Entry<File, Metadata> entry : collection.getFileMap().entrySet()) {
                File file = entry.getKey();
                // Metadata metadata = entry.getValue();

                // Add a ZIP entry for each file
                ZipEntry zipEntry = new ZipEntry(metadata.getName());
                zipOutStream.putNextEntry(zipEntry);

                // Read the bytes and write to ZIP output stream
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                zipOutStream.write(fileBytes);

                // Close the current entry
                zipOutStream.closeEntry();
            }

            //
            // Finish and close the ZIP output stream
            zipStream.finish();
            zipStream.close();

            // Create an Archive with the compressed data
            ByteArrayWrapper compressedDataWrapper = new ByteArrayWrapper(byteOutStream.toByteArray());
            return new Archive(compressedDataWrapper);

        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    // Extracts the contents of a ZIP archive to a specified destination
    @Override
    public void extractContents(Archive archive, String destination) {
        // Hypothetical method to get compressed data from the archive as a byte array
        byte[] compressedData = archive.getCompressedContents();

        // Use try-with-resources for the ByteArrayInputStream and ZipInputStream to ensure they're properly closed
        try (ByteArrayInputStream byteInStream = new ByteArrayInputStream(compressedData);
             ZipInputStream zipInStream = new ZipInputStream(byteInStream)) {

            ZipEntry zipEntry;
            // Process each ZIP file entry
            while ((zipEntry = zipInStream.getNextEntry()) != null) {
                // Construct the file path for the current ZIP entry
                String filePath = destination + File.separator + zipEntry.getName();
                File newFile = new File(filePath);

                if (zipEntry.isDirectory()) {
                    // If the entry is a directory, attempt to create it along with any necessary parent directories
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // If the entry is a file, ensure parent directories are created
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // Extract the file's contents
                    try (FileOutputStream fileOutStream = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zipInStream.read(buffer)) > 0) {
                            fileOutStream.write(buffer, 0, len);
                        }
                    }
                }
                zipInStream.closeEntry(); // Ensure the current ZIP entry is closed
            }
        } catch (IOException exception) {
            // Handle any IOExceptions encountered during extraction
            exception.printStackTrace();
        }
    }
}

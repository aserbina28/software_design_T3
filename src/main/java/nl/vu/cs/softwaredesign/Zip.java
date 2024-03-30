package nl.vu.cs.softwaredesign;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip extends CompressionStrategy {

    public Zip(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    @Override
    public Archive compress(Collection collection) {
        try (ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutStream = new ZipOutputStream(byteOutStream)) {

            zipOutStream.setLevel(compressionLevel);
            ArrayList<File> fileList = collection.getFiles();

            for (File file : fileList) {
                ZipEntry entry = new ZipEntry(file.getName());
                zipOutStream.putNextEntry(entry);

                byte[] fileBytes = Files.readAllBytes(file.toPath());
                zipOutStream.write(fileBytes);

                zipOutStream.closeEntry();
            }

            zipOutStream.finish();
            byte[] compressedData = byteOutStream.toByteArray();

            return new Archive(compressedData, collection.getMetaData(), this);

        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    //ZIP SHOULD HAVE DECOMPRESS THAT RETURNS A COLLECTION
    @Override
    public Collection decompress(Archive archive) {
        File destinationDirectory = new File();
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }

        byte[] compressedData = archive.getCompressedData();

        try (ByteArrayInputStream byteInStream = new ByteArrayInputStream(compressedData);
             ZipInputStream zipInStream = new ZipInputStream(byteInStream)) {

            ZipEntry zipEntry = zipInStream.getNextEntry();
            while (zipEntry != null) {
                String filePath = destination + File.separator + zipEntry.getName();
                File newFile = new File(filePath);
                System.out.println("Extracting: " + newFile.getPath());

                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    try (FileOutputStream fileOutStream = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = zipInStream.read(buffer)) > 0) {
                            fileOutStream.write(buffer, 0, len);
                        }
                    }
                    //MAKE NEW COMPRESSION OBJECT AND ADD FILES TO IT
//                    Collection c = new Collection;
//                    c.add(File);
                }
                zipInStream.closeEntry();
                zipEntry = zipInStream.getNextEntry();
            }

            return
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
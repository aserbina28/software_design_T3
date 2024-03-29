package nl.vu.cs.softwaredesign;

import java.io.File;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) {
        try {
            nl.vu.cs.softwaredesign.Collection fileCollection = new nl.vu.cs.softwaredesign.Collection();
            File testFilesDir = new File("/Users/jacobroberts/IdeaProjects/software_design_T3/test");

            if (testFilesDir.listFiles() != null) {
                for (File file : testFilesDir.listFiles()) {
                    if (file.isFile()) {
                        fileCollection.add(file);
                    }
                }
            }

            Zip zip = new Zip(5);
            Archive compressedArchive = zip.compress(fileCollection);

            String extractionPath = "/Users/jacobroberts/IdeaProjects/software_design_T3/extr";
            new File(extractionPath).mkdirs(); // Ensure the extraction directory exists

            // Adjusted to save the compressed.zip file in the specified extraction directory
            if (compressedArchive != null && compressedArchive.getCompressedData() != null) {
                File compressedFile = new File(extractionPath, "compressed.zip");
                try (FileOutputStream fos = new FileOutputStream(compressedFile)) {
                    fos.write(compressedArchive.getCompressedData());
                }

                // zip.extractContents(compressedArchive, extractionPath);

                System.out.println("Compression and extraction have been completed successfully.");
            } else {
                System.out.println("Compression failed or produced no data.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

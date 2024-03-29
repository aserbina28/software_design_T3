package nl.vu.cs.softwaredesign;

import java.io.File;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) {
        try {

            Collection filegroup = new Collection();
            File fileA = new File("/Users/jacobroberts/IdeaProjects/software_design_T3/test/review.cpp");
            File fileB = new File("/Users/jacobroberts/IdeaProjects/software_design_T3/test/testerMain.cpp");

            filegroup.add(fileA);
            filegroup.add(fileB);
            System.out.println(filegroup.toString());
            filegroup.add(fileA);
            System.out.println(filegroup.toString());
            filegroup.remove(fileA);
            System.out.println(filegroup.toString());

            Zip z = new Zip(5);
            Archive arch = filegroup.compress(z);
            System.out.println(arch.previewContents());
            arch.decompress();

            Zip zipStrat = new Zip(5);
            Collection c = new Collection();
            Archive compressedArchive = c.compress(zipStrat);

            String extractionPath = "/Users/jacobroberts/IdeaProjects/software_design_T3/extr";
            new File(extractionPath).mkdirs();

            if (compressedArchive != null && compressedArchive.getCompressedData() != null) {
                File compressedFile = new File(extractionPath, "compressed.zip");
                try (FileOutputStream fos = new FileOutputStream(compressedFile)) {
                    fos.write(compressedArchive.getCompressedData());
                }
                System.out.println("Compression and extraction have been completed successfully.");
            } else {
                System.out.println("Compression failed or produced no data.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

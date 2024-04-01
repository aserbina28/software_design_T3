package nl.vu.cs.softwaredesign;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;

public class Archive {
    //private Collection contents;
    private byte[] compressedData; // store compressed data
    private double dateCreated;
    private int compressedSize;
    private HashMap<File, Metadata> fileMap = new HashMap<File, Metadata>();
    private boolean isEncrypted = true;
    private CompressionStrategy compressor;


    public Archive() {
        this.dateCreated = System.currentTimeMillis();
    }

    public byte[] getCompressedData() {
        return compressedData;
    }

    public void setEncrypted(boolean encrypted){
        this.isEncrypted = encrypted;
    }

    public void add(File f){
        try {
            BasicFileAttributes fileAttr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
            long size = fileAttr.size();
            String name = f.getName();
            //Optional<String> type = getFileExtension(name);

            Metadata m = new Metadata(name, size);
            fileMap.put(f, m);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(File f){
        fileMap.remove(f);
    }
    public void compress(CompressionStrategy c, String destination){
        this.compressor = c;
        this.compressedData = compressor.compress(fileMap, destination);
    }
    public void decompress(String destination){
        compressor.decompress(this.compressedData, destination);
    }
    public ArrayList<Metadata> getMetadata(){
        return (ArrayList<Metadata>) fileMap.values();
    }

    public String toString(){
        String output = "";
        for(Metadata h : fileMap.values()){
            output += ": " + h.getName() + " :";
        }
        return output;
    }


}
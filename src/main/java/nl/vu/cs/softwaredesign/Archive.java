package nl.vu.cs.softwaredesign;

import java.util.ArrayList;

public class Archive {
    //private Collection contents;
    private byte[] compressedData; // store compressed data
    private double dateCreated;
    private int compressedSize;
    private boolean isEncrypted = true;
    private ArrayList<Metadata> metadataList;


    // Constructor for compressed data
    public Archive(byte[] data) {
        this.compressedData = data;
        this.dateCreated = System.currentTimeMillis();
        this.compressedSize = data.length;
        this.isEncrypted = false; // Assuming new archives start as not encrypted
        this.metadataList = new ArrayList<>(); // Initialize metadata list
    }
    public Archive(ArrayList<Metadata> mList){
        //this.contents = collect;
        this.dateCreated = System.currentTimeMillis();
        isEncrypted = false;
        this.metadataList = mList;
    }

    // Method to retrieve compressed data
    public byte[] getCompressedData() {
        return compressedData;
    }

    // Method to set compressed data
    public void setCompressedData(byte[] compressedData) {
        this.compressedData = compressedData;
        // Update compressedSize based on the new data
        this.compressedSize = compressedData != null ? compressedData.length : 0;
    }


    public void setEncrypted(boolean encrypted){
        this.isEncrypted = encrypted;
    }

//    public Collection decompress(CompressionStrategy c){
//        //turns archive into collection by unencrypting it
//        return new Collection;
//    }

    public ArrayList<Metadata> previewContents(){
        return metadataList;
    }
}
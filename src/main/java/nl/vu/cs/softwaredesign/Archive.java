package nl.vu.cs.softwaredesign;

import java.util.ArrayList;

public class Archive {
    //private Collection contents;
    private double dateCreated;
    private int compressedSize;
    private boolean isEncrypted = true;
    private ArrayList<Metadata> metadataList;

    public Archive(ArrayList<Metadata> mList){
        //this.contents = collect;
        this.dateCreated = System.currentTimeMillis();
        isEncrypted = false;
        this.metadataList = mList;
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
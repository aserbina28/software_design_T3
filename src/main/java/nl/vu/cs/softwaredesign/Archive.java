package nl.vu.cs.softwaredesign;

public class Archive {
    private Collection contents;
    private double dateCreated;
    private int compressedSize;
    boolean isEncrypted = true;
    //private ArrayList<Metadata> metadataList;

    public Archive(Collection collect){
        this.contents = collect;
        this.dateCreated = System.currentTimeMillis();
        isEncrypted = false;
        //this.metadataList = collect.getMetaData();
    }

//    public Collection decompress(CompressionStrategy c){
//        //turns archive into collection by unencrypting it
//        return new Collection;
//    }

    public String previewContents(){
        String print = "";
        for(Metadata m : contents.getMetaData()){
            print += "[" + m.getName() + ", " + String.valueOf(m.getByteSize()) + "],";
        }
        return print;
    }
}

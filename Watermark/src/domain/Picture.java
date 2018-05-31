package domain;

import java.sql.Blob;
import java.util.Arrays;

import tool.Mytool;

public class Picture {
    private String id = Mytool.get8UUID();
    private String uid = null;
    private String filename;
    private byte[] picture;
    private int width;
    private int height;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public byte[] getPicture() {
        return picture;
    }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    @Override
    public String toString() {
        return "Picture [id=" + id + ", uid=" + uid + ", filename=" + filename + ", picture=" + Arrays.toString(picture)
                + ", width=" + width + ", height=" + height + "]";
    }
    
    
    
    
    
}

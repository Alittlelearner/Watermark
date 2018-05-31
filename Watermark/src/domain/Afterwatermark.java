package domain;

import java.util.Arrays;

import tool.Mytool;

public class Afterwatermark {
    private String id = Mytool.get8UUID();
    private String uid;
    private String pid = null;
    private String wid;
    private String message = null;
    private byte[] picture;
    private String filename;
    private int width;
    private int height;
    private String mode;
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
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getWid() {
        return wid;
    }
    public void setWid(String wid) {
        this.wid = wid;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public byte[] getPicture() {
        return picture;
    }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
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
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    @Override
    public String toString() {
        return "Afterwatermark [id=" + id + ", uid=" + uid + ", pid=" + pid + ", wid=" + wid + ", message=" + message
                + ", picture=" + Arrays.toString(picture) + ", filename=" + filename + ", width=" + width + ", height="
                + height + ", mode=" + mode + "]";
    }
    
    
    

}

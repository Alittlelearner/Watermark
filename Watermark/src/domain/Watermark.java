package domain;

import java.util.Arrays;

import tool.Mytool;

public class Watermark {
    public String id = Mytool.get8UUID();
    public String uid;
    public String filename;
    public float width;
    public float height;
    public byte[] picture;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Watermark [id=" + id + ", uid=" + uid + ", filename=" + filename + ", width=" + width + ", height="
                + height + ", picture=" + Arrays.toString(picture) + "]";
    }

}

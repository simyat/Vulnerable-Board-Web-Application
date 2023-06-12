package model;

import java.sql.Blob;


public class FileDTO {
    private Blob uploads;
    private Blob downloads;
    private String downloadsString;

    public Blob getUploads() {
        return uploads;
    }
    public void setUploads(Blob uploads) {
        this.uploads = uploads;
    }
    public Blob getDownloads() {
        return downloads;
    }
    public void setDownloads(Blob downloads) {
        this.downloads = downloads;
    }
    public String getDownloadsString() {
        return downloadsString;
    }
    public void setDownloadsString(String downloadsString) {
        this.downloadsString = downloadsString;
    }
}

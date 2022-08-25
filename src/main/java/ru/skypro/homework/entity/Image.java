package ru.skypro.homework.entity;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue
    Long id;

    @Column(name = "file_size")
    private long fileSize;
    @Column(name = "media_type")
    private String mediaType;

    @Lob
    private byte[] data;

    public Image() {
    }

    public Image(Long id, long fileSize, String mediaType, byte[] data) {
        this.id = id;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

package ru.skypro.homework.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ads_id")
    private Long adsId;

    @Column(name = "user_profile_id")
    private Long author;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "text")
    private String text;

    public Comment() {
    }

    public Comment(Long id, Long adsId, Long author, Date createdAt, String text) {
        this.id = id;
        this.adsId = adsId;
        this.author = author;
        this.createdAt = createdAt;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", adsId=" + adsId +
                ", author=" + author +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (!id.equals(comment.id)) return false;
        if (!adsId.equals(comment.adsId)) return false;
        if (!author.equals(comment.author)) return false;
        if (!createdAt.equals(comment.createdAt)) return false;
        return text.equals(comment.text);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + adsId.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdsId() {
        return adsId;
    }

    public void setAdsId(Long adsId) {
        this.adsId = adsId;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

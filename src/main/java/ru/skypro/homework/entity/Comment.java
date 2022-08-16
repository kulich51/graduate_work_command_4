package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
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
}

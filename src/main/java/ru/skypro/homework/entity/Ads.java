package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id")
    private UserProfile author;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;

    public Ads() {

    }

    public Ads(Long id, UserProfile author, String title, String image, int price, String description) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.image = image;
        this.price = price;
        this.description = description;
    }
}

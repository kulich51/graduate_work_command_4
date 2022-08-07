package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_profile_id")
    private Long author;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private int price;

    public Ads() {

    }

    public Ads(Long id, String image, Long author, int price, String title) {
        this.id = id;
        this.image = image;
        this.author = author;
        this.price = price;
        this.title = title;
    }
}

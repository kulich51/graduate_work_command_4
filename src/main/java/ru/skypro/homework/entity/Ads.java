package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    @Column(name = "image")
    private String image;
    @Column(name = "author")
    private Long author;
    @Column(name = "price")
    private int price;
    @Column(name = "pk")
    private Long pk;
    @Column(name = "title")
    private String title;

    public Ads() {

    }

    public Ads(String image, Long author, int price, Long pk, String title) {
        this.image = image;
        this.author = author;
        this.price = price;
        this.pk = pk;
        this.title = title;
    }
}

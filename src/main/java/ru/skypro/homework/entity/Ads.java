package ru.skypro.homework.entity;

import javax.persistence.*;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getAuthor() {
        return author;
    }

    public void setAuthor(UserProfile author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "id=" + id +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ads ads = (Ads) o;

        if (price != ads.price) return false;
        if (!id.equals(ads.id)) return false;
        if (!author.equals(ads.author)) return false;
        if (!title.equals(ads.title)) return false;
        if (!image.equals(ads.image)) return false;
        return description.equals(ads.description);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

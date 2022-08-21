package ru.skypro.homework.entity;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@SecondaryTable(name = "users_profiles", pkJoinColumns = @PrimaryKeyJoinColumn(
        name = "user_profile_id", referencedColumnName = "id")
)
public class FullAds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private int price;

    @Column(name = "email", table = "users_profiles")
    private String email;

    @Column(name = "first_name", table = "users_profiles")
    private String firstName;

    @Column(name = "last_name", table = "users_profiles")
    private String lastName;

    @Column(name = "phone", table = "users_profiles")
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "FullAds{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FullAds fullAds = (FullAds) o;

        if (price != fullAds.price) return false;
        if (!id.equals(fullAds.id)) return false;
        if (!title.equals(fullAds.title)) return false;
        if (!image.equals(fullAds.image)) return false;
        if (!email.equals(fullAds.email)) return false;
        if (!firstName.equals(fullAds.firstName)) return false;
        if (!lastName.equals(fullAds.lastName)) return false;
        return phone.equals(fullAds.phone);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + image.hashCode();
        result = 31 * result + price;
        result = 31 * result + email.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }
}

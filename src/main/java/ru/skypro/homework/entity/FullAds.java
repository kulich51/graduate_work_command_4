package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@SecondaryTable(name = "users_profiles", pkJoinColumns = @PrimaryKeyJoinColumn(
        name = "user_profile_id", referencedColumnName = "id")
)
@Data
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
}

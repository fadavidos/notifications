package com.fabian.osorio.gila.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserEntity {

    private @Id
    @GeneratedValue Long id;
    private String name;

    private String email;

    private String phone;

    @ElementCollection(targetClass = CategoryEnum.class)
    @CollectionTable(name = "user_categories")
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private List<CategoryEnum> categories;

    private List<ChannelEnum> channels;

    public UserEntity() {
    }

    public UserEntity(Long id, String name, String email, String phone, List<CategoryEnum> categories, List<ChannelEnum> subscribed) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.categories = categories;
        this.channels = subscribed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<CategoryEnum> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEnum> categories) {
        this.categories = categories;
    }

    public List<ChannelEnum> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelEnum> channels) {
        this.channels = channels;
    }
}

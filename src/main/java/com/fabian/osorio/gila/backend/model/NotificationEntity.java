package com.fabian.osorio.gila.backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class NotificationEntity {

    private @Id
    @GeneratedValue Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Enumerated(EnumType.STRING)
    private ChannelEnum channel;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public NotificationEntity() {
        this.creationDate = new Date();
    }

    public NotificationEntity(String message, CategoryEnum category, ChannelEnum channel, UserEntity user) {
        this.message = message;
        this.category = category;
        this.channel = channel;
        this.user = user;
        this.creationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public ChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(ChannelEnum channel) {
        this.channel = channel;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}

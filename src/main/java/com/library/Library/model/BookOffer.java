package com.library.Library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "book_offer", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookOffer extends GenericModel {
    @Column(nullable = false)
    private String isbn;
    private String isbn13;
    private String title;
    private String originalTitle;
    private String originalPublicationYear;
    private String name;
    private String langCode;
    private String imageUrl;
    private String smallImageUrl;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Status status;
    private String comments;
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "suggested_user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK__SUGGESTED__USER_ID"))
    private User suggestedUser;
    @Version
    private Integer version;
}

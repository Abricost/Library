package com.library.Library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class Book extends GenericModel {

    private String isbn;
    private String isbn13;
    private String title;
    private String originalTitle;
    private String originalPublicationYear;
    private String name;
    private String langCode;
    private String imageUrl;
    private String smallImageUrl;
    @OneToMany(mappedBy = "book", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Rating> ratings;
    @Version
    private Integer version;


}

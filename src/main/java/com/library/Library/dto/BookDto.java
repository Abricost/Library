package com.library.Library.dto;

import com.library.Library.model.Rating;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.OptionalDouble;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto extends GenericDto {
    private String isbn;
    private String isbn13;
    private String title;
    private String originalTitle;
    private String originalPublicationYear;
    private String name;
    private String langCode;
    private String imageUrl;
    private String smallImageUrl;
    private Integer ratingCount;
    private Double ratingAvg;
    private List<Long> ratingIds;
}

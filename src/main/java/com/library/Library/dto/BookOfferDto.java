package com.library.Library.dto;

import com.library.Library.model.Status;
import com.library.Library.model.User;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookOfferDto extends GenericDto {
    private String isbn;
    private String isbn13;
    private String title;
    private String originalTitle;
    private String originalPublicationYear;
    private String name;
    private String langCode;
    private String imageUrl;
    private String smallImageUrl;
    private Status status;
    private String comments;
    private Long suggestedUserId;
    private Integer version;

}

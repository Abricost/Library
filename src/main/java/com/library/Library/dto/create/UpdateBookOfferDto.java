package com.library.Library.dto.create;

import com.library.Library.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookOfferDto {
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
    private String bookName;
    private String comments;
}

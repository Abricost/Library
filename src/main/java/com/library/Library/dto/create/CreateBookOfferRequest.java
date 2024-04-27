package com.library.Library.dto.create;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@Setter
public class CreateBookOfferRequest extends CreateBookRequest {
    private String comments;

    public CreateBookOfferRequest(String isbn,
                                  String isbn13,
                                  String title,
                                  String originalTitle,
                                  String originalPublicationYear,
                                  String name,
                                  String langCode,
                                  String imageUrl,
                                  String smallImageUrl,
                                  String comments) {
        super(isbn, isbn13, title, originalTitle, originalPublicationYear, name, langCode, imageUrl, smallImageUrl);
        this.comments = comments;
    }
}

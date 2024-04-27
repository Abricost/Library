package com.library.Library.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    protected String isbn;
    protected String isbn13;
    protected String title;
    protected String originalTitle;
    protected String originalPublicationYear;
    protected String name;
    protected String langCode;
    protected String imageUrl;
    protected String smallImageUrl;
}

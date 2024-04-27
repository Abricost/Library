package com.library.Library.dto.create;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CreateRatingRequest {
    private Integer rating;
    private Long bookId;
}

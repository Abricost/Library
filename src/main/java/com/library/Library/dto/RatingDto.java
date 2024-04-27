package com.library.Library.dto;


import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto extends GenericDto {
    private Integer rating;
    private Long userId;
    private Long bookId;
}

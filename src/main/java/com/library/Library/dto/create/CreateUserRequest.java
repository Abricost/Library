package com.library.Library.dto.create;

import lombok.*;

import java.util.List;
@ToString
@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private List<Long> roleIds;
    private List<Long> ratingIds;
}

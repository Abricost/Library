package com.library.Library.dto;

import com.library.Library.model.Rating;
import com.library.Library.model.Role;
import com.library.Library.model.User;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends GenericDto{
    protected Long id;
    private String username;
    private String password;
    private List<Long> roleIds;
    private List<Long> ratingIds;

}

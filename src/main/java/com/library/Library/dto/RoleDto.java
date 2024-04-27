package com.library.Library.dto;

import com.library.Library.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends GenericDto {
    private String name;
    private List<Long> userIds;
}

package com.library.Library.mapper;

import com.library.Library.dto.UserDto;
import com.library.Library.dto.create.CreateUserRequest;
import com.library.Library.model.Rating;
import com.library.Library.model.Role;
import com.library.Library.model.User;
import com.library.Library.repository.RoleRepository;
import org.mapstruct.*;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "ratingIds", source = "ratings", qualifiedByName = "toRatingIds")
    @Mapping(target = "roleIds", source = "roles", qualifiedByName = "toRoleIds")
    UserDto toDTO(User user);

    @Mapping(target = "ratings", source = "ratingIds", qualifiedByName = "fromRatingIds")
    @Mapping(target = "roles", source = "roleIds", qualifiedByName = "fromRoleIds")
    User toModel(UserDto userDto);

    List<User> toModelList(List<UserDto> questionDTOS);

    List<UserDto> toDTOList(List<User> questionsEntities);

    @Named("toRatingIds")
    static List<Long> toRatingIds(List<Rating> ratings) {
        if (ratings != null && !ratings.isEmpty()) {
            return ratings.stream().map(Rating::getId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Named("toRoleIds")
    static List<Long> toRoleIds(List<Role> roles) {
        if (roles != null && !roles.isEmpty()) {
            return roles.stream().map(Role::getId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Named("fromRatingIds")
    static List<Rating> fromRatingIds(List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            List<Rating> entityList = new ArrayList<>(ids.size());
            for (Long id : ids) {
                Rating rating = new Rating();
                rating.setId(id);
                entityList.add(rating);
            }
            return entityList;
        } else return Collections.emptyList();
    }

    @Named("fromRoleIds")
    static List<Role> fromRoleIds(List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            List<Role> roles = new ArrayList<>(ids.size());
            for (Long id : ids) {
                Role role = new Role();
                role.setId(id);
                roles.add(role);
            }
            return roles;
        } else return Collections.emptyList();
    }
}

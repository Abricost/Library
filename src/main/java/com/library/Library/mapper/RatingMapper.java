package com.library.Library.mapper;

import com.library.Library.dto.RatingDto;
import com.library.Library.dto.create.CreateRatingRequest;
import com.library.Library.model.Book;
import com.library.Library.model.Rating;
import com.library.Library.model.Role;
import com.library.Library.model.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper {
    @Mapping(target = "bookId", source = "book", qualifiedByName = "toBookId")
    @Mapping(target = "userId", source = "user", qualifiedByName = "toUserId")
    RatingDto toDTO(Rating rating);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "fromBookId")
    @Mapping(target = "user", source = "userId", qualifiedByName = "fromUserId")
    public Rating toModel(RatingDto ratingDto);

   // Rating toModelCreate(CreateRatingRequest createRatingRequest);

    Rating toModelUpdate(CreateRatingRequest createRatingRequest, Long id);

    List<Book> toModelList(List<RatingDto> ratingDtos);

    List<RatingDto> toDTOList(List<Rating> ratings);
    @Named("toBookId")
    static Long toBookId(Book book) {
        if (book != null) {
            return book.getId();
        }
        return null;
    }

    @Named("toUserId")
    static Long toUserId(User user) {
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    @Named("fromBookId")
    static Book fromBookId(Long id) {
        if (id != null) {
            Book book = new Book();
            book.setId(id);

            return book;
        } else return null;
    }

    @Named("fromUserId")
    static User fromUserId(Long id) {
        if (id != null) {
            User user = new User();
            user.setId(id);

            return user;
        } else return null;
    }
}

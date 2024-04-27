package com.library.Library.mapper;

import com.library.Library.dto.BookDto;
import com.library.Library.dto.create.CreateBookRequest;
import com.library.Library.model.Book;
import com.library.Library.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    // @Mapping(target = "ratingIds", expression = "java(book.getRatings().stream().map(Rating::getId).collect(Collectors.toList()))")
    @Mapping(target = "ratingIds", source = "ratings", qualifiedByName = "toIds")
    BookDto toDTO(Book book);

    @Mapping(target = "ratings", source = "ratingIds", qualifiedByName = "fromIds")
    Book toModel(BookDto bookDto);

    Book toModelCreate(CreateBookRequest bookRequest);

    Book toModelUpdate(CreateBookRequest bookRequest, Long id);

    List<Book> toModelList(List<BookDto> questionDTOS);

    List<BookDto> toDTOList(List<Book> questionsEntities);

    @Named("toIds")
    static List<Long> toIds(List<Rating> userTestingEntities) {
        if (userTestingEntities != null && !userTestingEntities.isEmpty()) {
            return userTestingEntities.stream().map(Rating::getId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Named("fromIds")
    static List<Rating> fromIds(List<Long> ids) {
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
}

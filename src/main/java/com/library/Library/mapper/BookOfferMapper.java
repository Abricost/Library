package com.library.Library.mapper;

import com.library.Library.dto.BookOfferDto;
import com.library.Library.dto.create.CreateBookOfferRequest;
import com.library.Library.dto.create.UpdateBookOfferDto;
import com.library.Library.model.Book;
import com.library.Library.model.BookOffer;
import com.library.Library.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookOfferMapper {
    @Mapping(target = "suggestedUserId", source = "suggestedUser", qualifiedByName = "toSuggestedUserId")
    BookOfferDto toDTO(BookOffer bookOffer);
    @Mapping(target = "suggestedUser", source = "suggestedUserId", qualifiedByName = "fromSuggestedUserId")
    BookOffer toModel(BookOfferDto bookOfferDto);

    BookOffer toModelCreate(CreateBookOfferRequest bookOfferRequest);

    BookOffer toModelUpdate(UpdateBookOfferDto bookOfferRequest, Long id);

    List<BookOffer> toModelList(List<BookOfferDto> bookOfferDtos);

    List<BookOfferDto> toDTOList(List<BookOffer> bookOffers);
    @Named("toSuggestedUserId")
    static Long toSuggestedUserId(User user) {
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    @Named("fromSuggestedUserId")
    static User fromSuggestedUserId(Long id) {
        if (id != null) {
            User user = new User();
            user.setId(id);

            return user;
        } else return null;
    }
}

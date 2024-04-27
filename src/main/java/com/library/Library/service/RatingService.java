package com.library.Library.service;

import com.library.Library.dto.RatingDto;
import com.library.Library.dto.create.CreateRatingRequest;
import com.library.Library.exception.NotFoundException;
import com.library.Library.exception.RatingIsEmptyException;
import com.library.Library.mapper.RatingMapper;
import com.library.Library.model.Book;
import com.library.Library.model.Rating;
import com.library.Library.model.User;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.RatingRepository;
import com.library.Library.repository.UserRepository;
import com.library.Library.service.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {
    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    public RatingDto create(CreateRatingRequest createRatingRequest) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User foundUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        Book foundBook = bookRepository.findById(createRatingRequest.getBookId()).orElseThrow(() -> new NotFoundException("Book not found"));
        if (createRatingRequest.getRating() != null) {
            Rating rating = ratingRepository.findOneByUserIdAndBookId(foundUser.getId(), createRatingRequest.getBookId());
            if (rating == null) {
                Rating newRating = new Rating();
                newRating.setRating(createRatingRequest.getRating());
                newRating.setUser(foundUser);
                newRating.setBook(foundBook);
                ratingRepository.save(newRating);
                return ratingMapper.toDTO(newRating);
            } else {
                throw new RatingIsEmptyException("Оценка существует");
            }
        } else {
            throw new RatingIsEmptyException("Оценка пустая");
        }
    }

    public RatingDto update(CreateRatingRequest createRatingRequest, Long id) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User foundUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        Book foundBook = bookRepository.findById(createRatingRequest.getBookId()).orElseThrow(() -> new NotFoundException("Book not found"));
        if (Objects.equals(foundUser.getId(), id)) {
            RatingDto ratingDto = new RatingDto();
            ratingDto.setRating(createRatingRequest.getRating());
            ratingDto.setId(id);
            ratingDto.setUserId(foundUser.getId());
            ratingDto.setBookId(createRatingRequest.getBookId());
            return ratingMapper.toDTO(ratingRepository.save(ratingMapper.toModel(ratingDto)));
        }
        return null;
    }

    @Transactional(readOnly = true)
    public RatingDto getOne(Long id) {
        return ratingMapper.toDTO(ratingRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных не найдено по заданному id")));
    }

    @Transactional
    public RatingDto deleteRating(Long id) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (Objects.equals(ratingRepository.findById(id).orElseThrow().getUser().getId(), user.getId())) {
            return ratingMapper.toDTO(ratingRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных не найдено по заданному id")));
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Page<RatingDto> listAll(final Pageable pageable) {
        Page<Rating> ratings = ratingRepository.findAll(pageable);
        List<RatingDto> result = ratingMapper.toDTOList(ratings.getContent());
        return new PageImpl<>(result, pageable, ratings.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<RatingDto> getAllRatingsByBookId(final Pageable pageable, Long id) {
        Page<Rating> ratings = Objects.requireNonNull(ratingRepository.findAllByBookId(id, pageable));
        if (!ratings.isEmpty()) {
            return new PageImpl<>(ratingMapper.toDTOList(ratings.getContent()), pageable, ratings.getTotalElements());
        }
        return null;
    }

}

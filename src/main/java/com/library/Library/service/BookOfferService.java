package com.library.Library.service;

import com.library.Library.dto.BookDto;
import com.library.Library.dto.BookOfferDto;
import com.library.Library.dto.create.CreateBookOfferRequest;
import com.library.Library.dto.create.UpdateBookOfferDto;
import com.library.Library.exception.MyValidationException;
import com.library.Library.exception.NotFoundException;
import com.library.Library.mapper.BookMapper;
import com.library.Library.mapper.BookOfferMapper;
import com.library.Library.model.Book;
import com.library.Library.model.BookOffer;
import com.library.Library.model.Status;
import com.library.Library.model.User;
import com.library.Library.repository.BookOfferRepository;
import com.library.Library.repository.BookRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.library.Library.model.Status.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookOfferService {
    private final BookOfferMapper mapstructMapper;
    private final BookOfferRepository bookOfferRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    @Transactional(readOnly = true)
    public Page<BookOfferDto> getBookOfferByUser(final Pageable pageable) {

        Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        List<BookOffer> bookOffers = user.getBookOffers();
        if (!bookOffers.isEmpty()) {
            List<BookOfferDto> bookOfferDtos = mapstructMapper.toDTOList(bookOffers);
            return new PageImpl<>(bookOfferDtos, pageable, bookOfferDtos.size());
        }
        return null;
    }

    @Transactional(readOnly = true)
    public BookOfferDto getOne(Long id) {
        BookOffer bookOffer = bookOfferRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных не найдено по заданному id"));
        Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        if (Objects.equals(user.getId(), bookOffer.getId()) || user.getRoles().stream().map(u -> Objects.equals(u.getName(), "ADMIN")).isParallel()) {
            return mapstructMapper.toDTO(bookOffer);
        }
        return null;
    }

    @Transactional
    public BookOfferDto addBookOffer(CreateBookOfferRequest newBookOffer) {
        BookOffer bookOffer = new BookOffer();
        Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
        bookOffer.setSuggestedUser(user);
        bookOffer.setStatus(Status.ADMIN_REVIEW);
        bookOffer.setName(newBookOffer.getName());
        bookOffer.setIsbn(newBookOffer.getIsbn());
        bookOffer.setIsbn13(newBookOffer.getIsbn13());
        bookOffer.setTitle(newBookOffer.getTitle());
        bookOffer.setOriginalTitle(newBookOffer.getOriginalTitle());
        bookOffer.setImageUrl(newBookOffer.getImageUrl());
        bookOffer.setLangCode(newBookOffer.getLangCode());
        bookOffer.setSmallImageUrl(newBookOffer.getSmallImageUrl());
        return mapstructMapper.toDTO(bookOfferRepository.save(bookOffer));
    }

    @Transactional
    public BookOfferDto updateBookOffer(UpdateBookOfferDto updateBookOfferRequest, Long id) {
        if (updateBookOfferRequest.getStatus() == null) {
            throw new MyValidationException("Статус должен быть заполнен");
        }
        BookOffer bookOffer = new BookOffer();
        bookOffer.setId(id);
        Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        if (Objects.equals(user.getId(), bookOffer.getId()) || user.getRoles().stream().anyMatch(s -> s.getName().equals("ADMIN"))) {
            bookOffer.setSuggestedUser(user);
            bookOffer.setStatus(updateBookOfferRequest.getStatus());
            bookOffer.setName(updateBookOfferRequest.getName());
            bookOffer.setIsbn(updateBookOfferRequest.getIsbn());
            bookOffer.setIsbn13(updateBookOfferRequest.getIsbn13());
            bookOffer.setTitle(updateBookOfferRequest.getTitle());
            bookOffer.setOriginalTitle(updateBookOfferRequest.getOriginalTitle());
            bookOffer.setImageUrl(updateBookOfferRequest.getImageUrl());
            bookOffer.setLangCode(updateBookOfferRequest.getLangCode());
            bookOffer.setSmallImageUrl(updateBookOfferRequest.getSmallImageUrl());
            if (updateBookOfferRequest.getStatus().equals(Status.ADDED)) {
                Book book = new Book();
                book.setName(updateBookOfferRequest.getName());
                book.setIsbn(updateBookOfferRequest.getIsbn());
                book.setIsbn13(updateBookOfferRequest.getIsbn13());
                book.setTitle(updateBookOfferRequest.getTitle());
                book.setOriginalTitle(updateBookOfferRequest.getOriginalTitle());
                book.setImageUrl(updateBookOfferRequest.getImageUrl());
                book.setLangCode(updateBookOfferRequest.getLangCode());
                book.setSmallImageUrl(updateBookOfferRequest.getSmallImageUrl());
                bookRepository.save(book);
            }
            return mapstructMapper.toDTO(bookOfferRepository.save(bookOffer));
        }
        else {
            throw new MyValidationException("Вы не можете изменить предложение!");
        }
    }

    @Transactional
    public void deleteBookOffer(Long id) {
        mapstructMapper.toDTO(bookOfferRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных не найдено по заданному id")));
    }

    @Transactional(readOnly = true)
    public Page<BookOfferDto> listAll(final Pageable pageable) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User foundUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found"));

        if (foundUser.getRoles().stream().anyMatch(s -> s.getName().equals("ADMIN"))) {
            log.info("We are in IF admin");
            List<BookOfferDto> bookOfferDtos = mapstructMapper.toDTOList(bookOfferRepository.findAllByStatus(ADMIN_REVIEW));
            log.info(String.valueOf(bookOfferDtos.size()));
            return new PageImpl<>(bookOfferDtos, pageable, bookOfferDtos.size());
        } else {
            List<BookOfferDto> bookOfferDtos = mapstructMapper.toDTOList(bookOfferRepository.findAllRejectedByUserId(user.getId()));
            return new PageImpl<>(bookOfferDtos, pageable, bookOfferDtos.size());
        }
    }
}

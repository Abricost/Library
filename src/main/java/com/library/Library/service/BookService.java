package com.library.Library.service;

import com.library.Library.dto.BookDto;
import com.library.Library.dto.RatingDto;
import com.library.Library.dto.create.CreateBookRequest;
import com.library.Library.exception.NotFoundException;
import com.library.Library.mapper.BookMapper;
import com.library.Library.mapper.RatingMapper;
import com.library.Library.model.Book;
import com.library.Library.model.Rating;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final RatingRepository ratingRepository;

    @Transactional(readOnly = true)
    public Page<BookDto> getAllBooks(final Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        List<BookDto> result = bookMapper.toDTOList(books.getContent());
        for (BookDto book : result) {
            book.setRatingCount(book.getRatingIds().size());
            book.setRatingAvg(book.getRatingIds().stream().mapToDouble(r -> ratingRepository.findById(r).get().getRating()).average().orElse(0.0));
        }
        return new PageImpl<>(result, pageable, books.getTotalElements());
    }

    @Transactional(readOnly = true)
    public BookDto getOne(Long id) {
        BookDto bookDto = bookMapper.toDTO(bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных не найдено по заданному id")));
        bookDto.setRatingCount(bookDto.getRatingIds().size());
        bookDto.setRatingAvg(bookDto.getRatingIds().stream().mapToDouble(r -> ratingRepository.findById(r).get().getRating()).average().orElse(0.0));
        return bookDto;
    }

    @Transactional
    public BookDto addBook(CreateBookRequest newBook) {
        return bookMapper.toDTO(bookRepository.save(bookMapper.toModelCreate(newBook)));
    }

    public BookDto updateBook(CreateBookRequest updatedBook, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        book.setImageUrl(updatedBook.getImageUrl());
        book.setTitle(updatedBook.getTitle());
        book.setSmallImageUrl(updatedBook.getSmallImageUrl());
        book.setName(updatedBook.getName());
        book.setIsbn(updatedBook.getIsbn());
        book.setIsbn13(updatedBook.getIsbn13());
        book.setLangCode(updatedBook.getLangCode());
        book.setOriginalPublicationYear(updatedBook.getOriginalPublicationYear());
        book.setOriginalTitle(updatedBook.getOriginalTitle());
        return bookMapper.toDTO(bookRepository.save(book));
    }

    @Transactional
    public BookDto deleteBook(Long id) {
        return bookMapper.toDTO(bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных не найдено по заданному id")));
    }

    @Transactional(readOnly = true)
    public Page<BookDto> search(Pageable pageable, String title, String isbn) {
        Page<Book> booksSearchRes = bookRepository.searchBooks(title, isbn, pageable);
        List<BookDto> result = bookMapper.toDTOList(booksSearchRes.getContent());
        return new PageImpl<>(result, pageable, booksSearchRes.getTotalElements());
    }
}

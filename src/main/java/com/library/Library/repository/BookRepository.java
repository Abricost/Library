package com.library.Library.repository;

import com.library.Library.model.Book;
import io.micrometer.common.lang.NonNullApi;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@NonNullApi
public interface BookRepository extends GenericRepository<Book> {

    @Query(nativeQuery = true,
            value = """
                    select distinct b.*
                    from book b
                    where b.title ilike '%' || coalesce(:title, '%')  || '%'
                    and b.isbn ilike '%' || coalesce(:isbn, '%')  || '%'
                                    
                    """)
    Page<Book> searchBooks(String title, String isbn, Pageable pageable);
}

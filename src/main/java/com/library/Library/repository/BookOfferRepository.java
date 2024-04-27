package com.library.Library.repository;

import com.library.Library.model.BookOffer;
import com.library.Library.model.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOfferRepository extends GenericRepository<BookOffer> {

    List<BookOffer> findAllByStatus(Status status);
    @Query(nativeQuery = true,
    value = """
        select *
        from book_offer bo
        where status = 2 and suggested_user_id = :userId
        """)
    List<BookOffer> findAllRejectedByUserId(Long userId);
}

package com.library.Library.repository;

import com.library.Library.dto.create.CreateRatingRequest;
import com.library.Library.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends GenericRepository<Rating> {
    Rating findOneByUserIdAndBookId(Long userId, Long bookId);
    Page<Rating> findAllByBookId(Long id, Pageable pageable);
}

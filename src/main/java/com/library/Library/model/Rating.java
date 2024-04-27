package com.library.Library.model;

import jakarta.persistence.*;
import liquibase.datatype.core.SmallIntType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

@Entity
@Table(name = "rating", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Rating extends GenericModel {
    @Column(columnDefinition = "int2")
    private Integer rating;
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK__RATINGS__USER_ID"))
    private User user;
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK__RATINGS__BOOK_ID"))
    private Book book;

}

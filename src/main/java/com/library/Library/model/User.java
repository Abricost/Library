package com.library.Library.model;

import com.library.Library.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends GenericModel {

    private String username;
    private String password;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_to_role",
            joinColumns = @JoinColumn(name = "user_id"), foreignKey = @ForeignKey(name = "FK__USER_ROLES__USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "role_id"), inverseForeignKey = @ForeignKey(name = "FK__USER_ROLES__ROLE_ID")
    )
    private List<Role> roles;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Rating> ratings;
    @OneToMany(mappedBy = "suggestedUser", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<BookOffer> bookOffers;

}

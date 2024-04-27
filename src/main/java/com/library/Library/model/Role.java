package com.library.Library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@Table(name = "role", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class Role extends GenericModel implements GrantedAuthority {

    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_to_role",
            joinColumns = @JoinColumn(name = "role_id"), foreignKey = @ForeignKey(name = "FK__USER_ROLES__ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "user_id"), inverseForeignKey = @ForeignKey(name = "FK__USER_ROLES__USER_ID")
    )
    private List<User> users;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name;
    }
}

package com.example.Enotes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String mobNo;

    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER) //Hibernate maps the primary keys of both
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER) //Hibernate maps the primary keys of AccountStatus
    @JoinColumn(name = "status_id")
    private AccountStatus status;
}

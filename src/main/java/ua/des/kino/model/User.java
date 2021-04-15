package ua.des.kino.model;

import lombok.Data;
import ua.des.kino.model.submodel.UserContact;
import ua.des.kino.model.submodel.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long id;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private UserDetails details;

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserContact contact;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private Set<Ticket> ticket;
}

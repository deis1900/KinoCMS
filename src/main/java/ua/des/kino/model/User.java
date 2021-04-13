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
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserDetails details;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserContact contact;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private List<Booking> booking;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private Set<Ticket> ticket;
}

package ua.des.kino.model.submodel;

import lombok.Data;
import ua.des.kino.model.User;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users_contacts")
public class UserContact implements Serializable {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String address;

    @Column(unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private String card;

    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

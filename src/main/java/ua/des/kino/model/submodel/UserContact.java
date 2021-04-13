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
    @Column(name = "user_id")
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

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}

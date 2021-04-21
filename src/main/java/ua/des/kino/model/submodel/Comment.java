package ua.des.kino.model.submodel;

import ua.des.kino.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String comment;

    @Column(name = "date_comment")
    private LocalDateTime dateComment;

}

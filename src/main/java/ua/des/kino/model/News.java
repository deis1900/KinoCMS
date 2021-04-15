package ua.des.kino.model;

import lombok.Data;
import ua.des.kino.model.submodel.Comment;
import ua.des.kino.model.submodel.NewsType;
import ua.des.kino.model.submodel.Photo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String info;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "news_id")
    private List<Photo> photos;

    @Column
    private Boolean state;

    @Column
    private Date startDate;

    @Column
    private Date finishDate;

    @OneToMany
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private List<Comment> comments;

    @Enumerated
    private NewsType newsType;

}

package ua.des.kino.model.mysql;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.model.mysql.submodel.NewsType;
import ua.des.kino.model.mysql.Photo;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "news")
public class News implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String info;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "news_id")
    private List<Photo> photos;

    @Column
    private Boolean state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @Column
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @Column
    private LocalDateTime finishDate;

    @Enumerated
    private NewsType newsType;

}

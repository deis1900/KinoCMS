package ua.des.kino.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.des.kino.model.submodel.FilmDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "films")
public class Film implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "main_photo")
    private String mainPhoto;

    @Column
    private Time duration;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private Timestamp startDate;

    @Column(name = "finish_date", columnDefinition = "TIMESTAMP")
    private Timestamp finishDate;

    @Column(name = "video_type")
    private String videoType;

    @OneToOne(mappedBy = "film", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private FilmDetails filmDetails;
}

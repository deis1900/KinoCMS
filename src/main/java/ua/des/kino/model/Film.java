package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.des.kino.config.Views;
import ua.des.kino.model.submodel.FilmDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "films")
@SecondaryTable(name = "films_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class Film implements Serializable {

    @JsonView({Views.Public.class})
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.Public.class)
    @Column
    private String name;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "main_photo")
    private String mainPhoto;

    @Column(columnDefinition = "TIME")
    private LocalTime duration;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private LocalDate startDate;

    @Column(name = "finish_date", columnDefinition = "TIMESTAMP")
    private LocalDate finishDate;

    @Column(name = "video_type")
    private String videoType;

    @JsonView({Views.Internal.class})
    @Embedded
    private FilmDetails filmDetails;
}

package ua.des.kino.model.submodel;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "photos")
public class Photo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column(name = "type")
    private String typePhotos;

    @Column
    private String url;
}
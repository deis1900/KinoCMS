package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ua.des.kino.config.Views;
import ua.des.kino.model.submodel.UserContact;
import ua.des.kino.model.submodel.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
@SecondaryTables({
        @SecondaryTable(name = "users_contacts", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id")),
        @SecondaryTable(name = "users_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))})
public class User implements Serializable {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long id;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Login is mandatory")
    @Size(min=4, max=32)
    @Column(unique = true)
    private String login;

    @Min(8)
    @Column
    private String password;

    @JsonView(Views.Internal.class)
    @Embedded
    private UserDetails details;

    @JsonView(Views.Internal.class)
    @Embedded
    private UserContact contact;
}

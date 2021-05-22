package ua.des.kino.model.audience;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.config.Views;
import ua.des.kino.model.audience.submodel.CustomerContact;
import ua.des.kino.model.audience.submodel.CustomerDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "customers", schema = "audience")
@SecondaryTables({
        @SecondaryTable(name = "customers_contacts", schema = "audience",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "customer_id")
        ),
        @SecondaryTable(name = "customers_details", schema = "audience",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "customer_id")
        )
})
public class Customer implements Serializable {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", updatable = false, nullable = false)
    private Long id;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Login is mandatory")
    @Size(min = 4, max = 32)
    @Column(unique = true)
    private String login;

    @JsonView(Views.Internal.class)
    @Embedded
    private CustomerDetails details;

    @JsonView(Views.Internal.class)
    @Embedded
    private CustomerContact contact;
}

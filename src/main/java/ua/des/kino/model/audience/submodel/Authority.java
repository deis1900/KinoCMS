package ua.des.kino.model.audience.submodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name="authority", schema = "audience")
public class Authority implements GrantedAuthority {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    Role role;

    @Override
    public String getAuthority() {
        return role.name();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonIgnore
    public Role getRole() {
        return role;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

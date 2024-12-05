package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;
    @Column(name = "role",unique = true,nullable = false)
    private String role;

    @Override
    public String getAuthority() {
        return this.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(role);
    }
}

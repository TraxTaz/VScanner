package com.example.vscanner.Persistence.Entity;

import com.example.vscanner.Domain.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@Builder
@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @NotBlank
    @Column(name = "Username")
    @Length(min = 4)
    private String username;

    @NotBlank
    @Column(name = "Password")
    private String password;

    @NotBlank
    @Column(name = "FirstName")
    private String FirstName;

    @NotBlank
    @Column(name = "LastName")
    private String LastName;

    @NotBlank
    @Column(name = "Email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private Role role;
}

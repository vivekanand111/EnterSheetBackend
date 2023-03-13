package com.example.demo2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "The username is required.")
    @Size(min = 2, max = 100, message = "The length of  username must be between 2 and 100 characters.")
    private String username;
    @NotEmpty(message = "The email address is required.")
    @Email(message = "Invalid email address", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    @NotBlank(message = "The phone number is required.")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Invalid phone number")
    private String mobileNumber;
    @NotBlank(message = "The password is required.")
    private String password;
    @NotNull(message = "The role is required.")
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Report> reports;

}

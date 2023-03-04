package net.foxtam.hyperskillorg.recipes.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {

    private static final String EMAIL_PATTERN = ".+@.+\\..+";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private long id;
    
    @Pattern(regexp = EMAIL_PATTERN)
    @Column(name = "email")
    private String email;
    
    @Size(min = 8)
    @NotBlank
    @Column(name = "password")
    private String password;
}

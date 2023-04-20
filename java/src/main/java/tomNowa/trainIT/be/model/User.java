package tomNowa.trainIT.be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(name = "username")
    private String userName;

    @NotNull
    private String password;

}

package tomNowa.trainIT.be.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "runs")
@AllArgsConstructor
@NoArgsConstructor
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "run_id")
    private int runId;

    @NotNull
    @Column(name = "user_id")
    private int userId;

    @NotNull
    @Column(name = "km_number")
    private double kmNumber;

    @NotNull
    private int seconds;

    @NotNull
    private Date date;

    public Run(final int userId, final double kmNumber, final int seconds, final Date date) {
        this.userId = userId;
        this.kmNumber = kmNumber;
        this.seconds = seconds;
        this.date = date;
    }
}
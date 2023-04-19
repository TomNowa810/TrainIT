package tomNowa.trainIT.be.model;
import lombok.Data;
import tomNowa.trainIT.be.Duration;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table
public class Run {

    private final int runnerId;
    private final double kmNumber;
    private final Duration duration;
    private final Date date;
}
package com.example.trainIT;
import lombok.Data;

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
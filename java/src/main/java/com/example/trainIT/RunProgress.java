package com.example.trainIT;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RunProgress {

    private int runnerID;

    private int totalRuns;
    private double processValueFromFirstRunToBest;
    private double processPerRun;
    private double valueOfBestRun;
    private double valueOfBadestRun;
    private double deterioration;
    private Duration average;
}

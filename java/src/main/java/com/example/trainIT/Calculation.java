package com.example.trainIT;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class Calculation {

    public RunProgress processByLastRuns(final List<Run> runs ){

        if (runs.isEmpty()){
            throw new RuntimeException();
        }

        double minutesByBestRun = minutesPerKm(runs.get(0));
        double minutesByBadestRun = 0;
        int positionByBestRun = 0;

        for (int i = 0; i < runs.size(); i++){
            final double currentValue = minutesPerKm(runs.get(i));

            if (minutesByBestRun > currentValue){
                minutesByBestRun = currentValue;
                positionByBestRun = i;
            }

            if (minutesByBadestRun < currentValue){
                minutesByBadestRun = currentValue;
            }
        }

        final Duration averageDuration = average(runs);
        final double processValue = minutesPerKm(runs.get(0)) - minutesByBestRun;
        final double averageMinPerKmFromStartToBestRun = calculateSumFromList(positionByBestRun, runs) / (positionByBestRun+1);
        final double processPerRun = averageMinPerKmFromStartToBestRun - minutesByBestRun;

        final int indexOfLastRun = runs.size() - 1;

        double deterioration = 0;

        if (indexOfLastRun - positionByBestRun >= 3 && (minutesPerKm(runs.get(indexOfLastRun)) - minutesByBestRun) >= 0.33){
            deterioration = minutesPerKm(runs.get(indexOfLastRun)) - minutesByBestRun;
        }

        return RunProgress.builder()
                .runnerID(runs.get(0).getRunnerId())
                .totalRuns(runs.size())
                .processValueFromFirstRunToBest(processValue)
                .processPerRun(processPerRun)
                .average(averageDuration)
                .valueOfBestRun(minutesByBestRun)
                .valueOfBadestRun(minutesByBadestRun)
                .deterioration(deterioration)
                .build();
    }

    public Duration average(final List<Run> runs){
        double summationMinutes = 0;
        int summationHours = 0;

        for (Run current : runs){
            summationHours += current.getDuration().getHours();
            summationMinutes += current.getDuration().getMinutes();
        }

        if (summationMinutes >= 60) {
            double dividedMinutes = summationMinutes / 60;
            int hoursToAdd = Integer.parseInt(String.valueOf(dividedMinutes));
            summationHours += hoursToAdd;
            summationMinutes += (dividedMinutes - hoursToAdd) * 60;
        }

        return new Duration(Integer.parseInt(String.valueOf(summationHours / runs.size()))
                ,Integer.parseInt(String.valueOf(summationMinutes / runs.size())));
    }

    private double minutesPerKm(final Run run){
        return run.getDuration().getWholeDurationInMinutes() / run.getKmNumber();
    }

    private double calculateSumFromList(final int endValue, final List<Run> runs){
        double sum = 0;

        for (int i = 0; i < endValue; i++){
            sum += minutesPerKm(runs.get(i));
        }
        return sum;
    }
}

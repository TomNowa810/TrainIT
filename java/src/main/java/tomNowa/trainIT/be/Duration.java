package tomNowa.trainIT.be;

import lombok.Data;

@Data
public class Duration {

    private final int hours;
    private final int minutes;

    public Duration(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getWholeDurationInMinutes(){
        return (hours * 60) + minutes;
    }
}

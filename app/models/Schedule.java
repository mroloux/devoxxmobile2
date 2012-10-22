package models;

import java.util.List;

public class Schedule {

    List<Talk> talks;

    @Override
    public String toString() {
        return talks.toString();
    }
}

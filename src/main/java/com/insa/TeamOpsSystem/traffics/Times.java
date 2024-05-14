package com.insa.TeamOpsSystem.traffics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Times {
    EIGHT("8O'CLOCK"), FORTIETH("14O'CLOCK"), EIGHTEEN("18O'CLOCK");
    private final String times;

    Times(String times) {
        this.times = times;
    }

    @JsonValue
    public String getTimes() {
        return times;
    }

    @JsonCreator
    public static Times decode(final String times) {
        return Stream.of(Times.values()).filter(targetEnum -> targetEnum.times.equals(times)).findFirst().orElse(null);
    }
}

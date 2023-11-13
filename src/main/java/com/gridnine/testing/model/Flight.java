package com.gridnine.testing.model;

import java.util.List;
import java.util.stream.Collectors;

public class Flight {
    private final List<Segment> segments;

    private final Integer id;

    public Flight(final List<Segment> segs, Integer id) {
        segments = segs;
        this.id = id;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}

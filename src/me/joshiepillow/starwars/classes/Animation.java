package me.joshiepillow.starwars.classes;

import java.util.ArrayList;
import java.util.List;

public class Animation<T> {
    private int current;
    private List<T> frames = new ArrayList<>();
    public void add(T frame, int length) {
        for (int i = 0; i < length; i++) frames.add(frame);
    }
    public T next() {
        T frame = frames.get(current);
        current++;
        if (current == frames.size()) current = 0;
        return frame;
    }
    public void reset() {
        current = 0;
    }
}

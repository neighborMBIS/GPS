package wj.animationtest;

import java.util.ArrayList;

/**
 * Created by user on 2016-09-22.
 */
public class Buf {
    private static Buf ourInstance = new Buf();

    public static Buf getInstance() {
        return ourInstance;
    }
    ArrayList<Integer> station;

    private Buf() {
        station = new ArrayList<Integer>();

        station.add(0);
        station.add(10);
        station.add(20);
        station.add(30);
        station.add(40);
        station.add(50);
        station.add(60);
        station.add(70);
        station.add(80);
        station.add(90);
        station.add(100);
    }
}

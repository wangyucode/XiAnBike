package cn.wycode.bike.model;

import java.util.Comparator;

/**
 * Created by wy
 * on 2017/1/8.
 */

public class LocationComparator implements Comparator<BikeStation> {

    @Override
    public int compare(BikeStation o1, BikeStation o2) {

        return (int) (o1.getDistance()-o2.getDistance());
    }
}

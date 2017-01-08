package cn.wycode.bike.model;

/**
 * Created by wy
 * on 2017/1/8.
 */

public class BikeStation{
    public int getEmptynum() {
        return emptynum;
    }

    public void setEmptynum(int emptynum) {
        this.emptynum = emptynum;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getLocknum() {
        return locknum;
    }

    public void setLocknum(int locknum) {
        this.locknum = locknum;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    private int emptynum;
    private int siteid;
    private int locknum;
    private double latitude;
    private double longitude;
    private String location;
    private String sitename;

    private float distance;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}

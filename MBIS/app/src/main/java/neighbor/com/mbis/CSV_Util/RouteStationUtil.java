package neighbor.com.mbis.CSV_Util;

/**
 * Created by user on 2016-08-29.
 */
public class RouteStationUtil {

    private String route_id;
    private int station_order;
    private String station_id;
    private String link_order;
    private String remark;
    private String direction;

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public int getStation_order() {
        return station_order;
    }

    public void setStation_order(int station_order) {
        this.station_order = station_order;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getLink_order() {
        return link_order;
    }

    public void setLink_order(String link_order) {
        this.link_order = link_order;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}

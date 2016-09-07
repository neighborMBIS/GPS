package neighbor.com.mbis.CSV_Util;

/**
 * Created by user on 2016-08-29.
 */
public class RouteUtil {

    private String id;
    private String route_id;
    private String st_sta_id;
    private String ed_sta_id;
    private String company_nm;
    private String admin_nm;
    private String company_id;
    private String direction;


    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getSt_sta_id() {
        return st_sta_id;
    }

    public void setSt_sta_id(String st_sta_id) {
        this.st_sta_id = st_sta_id;
    }

    public String getEd_sta_id() {
        return ed_sta_id;
    }

    public void setEd_sta_id(String ed_sta_id) {
        this.ed_sta_id = ed_sta_id;
    }

    public String getCompany_nm() {
        return company_nm;
    }

    public void setCompany_nm(String company_nm) {
        this.company_nm = company_nm;
    }

    public String getAdmin_nm() {
        return admin_nm;
    }

    public void setAdmin_nm(String admin_nm) {
        this.admin_nm = admin_nm;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}

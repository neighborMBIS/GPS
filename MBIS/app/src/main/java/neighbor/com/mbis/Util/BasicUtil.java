package neighbor.com.mbis.Util;

import neighbor.com.mbis.function.TransByte;

/**
 * Created by user on 2016-08-24.
 */
public class BasicUtil {
    private static BasicUtil ourInstance = new BasicUtil();

    public static BasicUtil getInstance() {
        return ourInstance;
    }

    private BasicUtil() {
    }
    TransByte tb = TransByte.getInstance();


    private byte[] body = new byte[47];
    //default
    private byte[] sendDate = new byte[6];
    private byte[] sendTime = new byte[6];
    private byte[] eventDate = new byte[6];
    private byte[] eventTime = new byte[6];
    private byte[] routeInfo = new byte[10];
    private byte[] GPSInfo = new byte[12];
    private byte[] diviceState = new byte[1];


    public byte[] getSendDate() {
        return sendDate;
    }

    public void setSendDate(String date) {
        this.sendDate = tb.StoB(date);
    }

    public byte[] getSendTime() {
        return sendTime;
    }

    public void setSendTime(String date) {
        this.sendTime = tb.StoB(date);
    }

    public byte[] getEventDate() {
        return eventDate;
    }

    public void setEventDate(String date) {
        this.eventDate = tb.StoB(date);
    }

    public byte[] getEventTime() {
        return eventTime;
    }

    public void setEventTime(String date) {
        this.eventTime = tb.StoB(date);
    }

    public byte[] getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(String frontNum, String backNum, byte[] routeForm, String routeDivision) {
        byte[] a = tb.StoB(frontNum);
        byte[] b = tb.StoB(backNum);
        byte[] c = routeForm;
        byte[] d = tb.StoB(routeDivision);
        this.routeInfo = tb.mergyByte(tb.mergyByte(a, b), tb.mergyByte(c, d));
    }

    public byte[] getGPSInfo() {
        return GPSInfo;
    }

    public void setGPSInfo(int x, int y, int bearing, int speed) {
        byte[] a = tb.ItoB(x, 4);
        byte[] b = tb.ItoB(y, 4);
        byte[] c = tb.ItoB(bearing, 2);
        byte[] d = tb.ItoB(speed, 2);

        this.GPSInfo = tb.mergyByte(tb.mergyByte(a, b), tb.mergyByte(c, d));
    }

    public byte[] getDiviceState() {
        return diviceState;
    }

    public void setDiviceState(String deviceState) {
        this.diviceState = tb.StoB(deviceState);
    }
}

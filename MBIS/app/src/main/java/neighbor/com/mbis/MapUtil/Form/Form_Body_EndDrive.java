package neighbor.com.mbis.MapUtil.Form;


import neighbor.com.mbis.Function.Func;

/**
 * Created by user on 2016-09-01.
 */
public class Form_Body_EndDrive {
    private static Form_Body_EndDrive ourInstance = new Form_Body_EndDrive();

    public static Form_Body_EndDrive getInstance() {
        return ourInstance;
    }

    private Form_Body_EndDrive() {
    }

    private byte[] stationId;
    private byte[] stationTurn;
    private byte[] adjacentTravelTime;
    private byte[] reservation;

    public void setStationId(byte[] stationId) {
        this.stationId = stationId;
    }
    public void setStationId(long stationId) {
        this.stationId = Func.longToByte(stationId, 10);
    }
    public void setStationId(String stationId) {
        this.stationId = Func.longToByte(Long.parseLong(stationId), 10);
    }
    public void setStationTurn(byte[] stationNum) {
        this.stationTurn = stationNum;
    }
    public void setStationTurn(int stationNum) {
        this.stationTurn = Func.integerToByte(stationNum, 2);
    }
    public void setAdjacentTravelTime(byte[] adjacentTravelTime) {
        this.adjacentTravelTime = adjacentTravelTime;
    }
    public void setAdjacentTravelTime(int adjacentTravelTime) {
        this.adjacentTravelTime = Func.integerToByte(adjacentTravelTime, 2);
    }
    public void setAdjacentTravelTime(String adjacentTravelTime) {
        this.adjacentTravelTime = Func.integerToByte(Integer.parseInt(adjacentTravelTime), 2);
    }
    public void setReservation(byte[] reservation) {
        this.reservation = reservation;
    }
    public void setReservation(int reservation) {
        this.reservation = Func.integerToByte(reservation, 2);
    }
    public void setReservation(String reservation) {
        this.reservation = Func.integerToByte(Integer.parseInt(reservation), 2);
    }
}

package neighbor.com.mbis.Util;

import neighbor.com.mbis.function.TransByte;

/**
 * Created by user on 2016-08-25.
 */
public class U_0x31 {
    private static U_0x31 ourInstance = new U_0x31();

    public static U_0x31 getInstance() {
        return ourInstance;
    }

    private U_0x31() {
    }

    byte[] driveDate = new byte[6];
    byte[] startTime = new byte[6];
    byte[] stationID = new byte[10];
    byte[] stationNum = new byte[2];
    byte[] driveNum = new byte[2];
    byte[] eventNum = new byte[2];

    TransByte tb = TransByte.getInstance();

    public byte[] getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(String driveDate) {
        this.driveDate = tb.StoB(driveDate);
    }

    public byte[] getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = tb.StoB(startTime);
    }

    public byte[] getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = tb.ItoB(stationID, 10);
    }

    public byte[] getStationNum() {
        return stationNum;
    }

    public void setStationNum(int stationNum) {
        this.stationNum = tb.ItoB(stationNum);
    }

    public byte[] getDriveNum() {
        return driveNum;
    }

    public void setDriveNum(int driveNum) {
        this.driveNum = tb.ItoB(driveNum);
    }

    public byte[] getEventNum() {
        return eventNum;
    }

    public void setEventNum(int eventNum) {
        this.eventNum = tb.ItoB(eventNum);
    }

}

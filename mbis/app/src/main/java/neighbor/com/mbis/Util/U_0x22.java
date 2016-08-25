package neighbor.com.mbis.Util;

import neighbor.com.mbis.function.TransByte;

/**
 * Created by user on 2016-08-24.
 */
public class U_0x22{
    private static U_0x22 ourInstance = new U_0x22();

    public static U_0x22 getInstance() {
        return ourInstance;
    }

    private U_0x22() {
    }
    TransByte tb = TransByte.getInstance();
    //0x22 (출발)
    private byte[] stationID = new byte[10];
    private byte[] stationNum = new byte[2];
    private byte[] driveNum = new byte[2];
    private byte[] serviceTime = new byte[2];
    private byte[] beforeToAfterSec = new byte[2];
    private byte[] etc = new byte[2];

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
        this.stationNum = tb.ItoB(stationNum, 2);
    }

    public byte[] getDriveNum() {
        return driveNum;
    }

    public void setDriveNum(int driveNum) {
        this.driveNum = tb.ItoB(driveNum, 2);
    }

    public byte[] getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = tb.ItoB(serviceTime, 2);
    }

    public byte[] getBeforeToAfterSec() {
        return beforeToAfterSec;
    }

    public void setBeforeToAfterSec(int beforeToAfterSec) {
        this.beforeToAfterSec = tb.ItoB(beforeToAfterSec, 2);
    }

    public byte[] getEtc() {
        return etc;
    }

    public void setEtc(int etc) {
        this.etc = tb.ItoB(etc, 2);
    }

}

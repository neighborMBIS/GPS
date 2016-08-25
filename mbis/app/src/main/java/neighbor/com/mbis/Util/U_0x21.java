package neighbor.com.mbis.Util;


import neighbor.com.mbis.function.TransByte;

/**
 * Created by user on 2016-08-24.
 */
public class U_0x21{
    private static U_0x21 ourInstance = new U_0x21();

    public static U_0x21 getInstance() {
        return ourInstance;
    }

    private U_0x21() {
    }
    TransByte tb = TransByte.getInstance();
    //0x21 (도착)
    private byte[] stationID = new byte[10];
    private byte[] stationNum = new byte[2];
    private byte[] beforeStationToAfterStationSec = new byte[2];
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

    public byte[] getBeforeStationToAfterStationSec() {
        return beforeStationToAfterStationSec;
    }

    public void setBeforeStationToAfterStationSec(int beforeStationToAfterStationSec) {
        this.beforeStationToAfterStationSec = tb.ItoB(beforeStationToAfterStationSec, 2);
    }

    public byte[] getEtc() {
        return etc;
    }

    public void setEtc(int etc) {
        this.etc = tb.ItoB(etc, 2);
    }

}

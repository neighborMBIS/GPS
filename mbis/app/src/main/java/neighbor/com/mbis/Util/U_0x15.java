package neighbor.com.mbis.Util;

import neighbor.com.mbis.function.TransByte;

/**
 * Created by user on 2016-08-24.
 */
public class U_0x15{
    private static U_0x15 ourInstance = new U_0x15();

    public static U_0x15 getInstance() {
        return ourInstance;
    }

    private U_0x15() {
    }
    TransByte tb = TransByte.getInstance();
    //0x15(시작)
    private byte[] driverDivision = new byte[1];
    private byte[] etc = new byte[3];

    public byte[] getDriverDivision() {
        return driverDivision;
    }

    public void setDriverDivision(int division) {
        this.driverDivision = tb.ItoB(division, 1);
    }

    public byte[] getEtc() {
        return etc;
    }

    public void setEtc(int etc) {
        this.etc = tb.ItoB(etc, 3);
    }
}

package neighbor.com.test321.Function;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/**
 * Created by user on 2016-08-31.
 */
public class Func {
    private static Func ourInstance = new Func();

    public static Func getInstance() {
        return ourInstance;
    }

    private Func() {
    }

    public static byte[] integerToByte(int in, int size) {
        ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE / 8);
        buff.putInt(in);
        buff.order(ByteOrder.BIG_ENDIAN);

        byte[] rtn = new byte[size];
        if(size == Integer.SIZE / 8) {
            return buff.array();
        } else if(size < Integer.SIZE / 8 ) {
            System.arraycopy(buff.array(), buff.array().length - size, rtn, 0, size);
            return rtn;
        } else {
            System.arraycopy(buff.array(), 0, rtn, size - buff.array().length, buff.array().length);
            return rtn;
        }
    }

    public static byte[] longToByte(long lng, int size) {
        ByteBuffer buff = ByteBuffer.allocate(Long.SIZE / 8);
        buff.putLong(lng);
        buff.order(ByteOrder.BIG_ENDIAN);

        byte[] rtn = new byte[size];
        if(size == Long.SIZE / 8) {
            return buff.array();
        } else if(size < Long.SIZE / 8 ) {
            System.arraycopy(buff.array(), buff.array().length - size, rtn, 0, size);
            return rtn;
        } else {
            System.arraycopy(buff.array(), 0, rtn, size - buff.array().length, buff.array().length);
            return rtn;
        }
    }

    public static byte[] stringToByte(String s) {
        byte[] b = new byte[s.length()];
        try {
            b = s.getBytes("ksc5601");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static byte[] mergyByte(byte[] b1, byte[] b2) {
        byte[] temp = new byte[b1.length + b2.length];

        System.arraycopy(b1, 0, temp, 0, b1.length);
        System.arraycopy(b2, 0, temp, b1.length, b2.length);

        return temp;
    }

    // 두 지점 구하는 알고리즘 적용
    public static double getDistance(double P1_latitude, double P1_longitude, double P2_latitude, double P2_longitude) {
        if ((P1_latitude == P2_latitude) && (P1_longitude == P2_longitude)) {
            return 0;
        }
        double e10 = P1_latitude * Math.PI / 180;
        double e11 = P1_longitude * Math.PI / 180;
        double e12 = P2_latitude * Math.PI / 180;
        double e13 = P2_longitude * Math.PI / 180;
  /* 타원체 GRS80 */
        double c16 = 6356752.314140910;
        double c15 = 6378137.000000000;
        double c17 = 0.0033528107;
        double f15 = c17 + c17 * c17;
        double f16 = f15 / 2;
        double f17 = c17 * c17 / 2;
        double f18 = c17 * c17 / 8;
        double f19 = c17 * c17 / 16;
        double c18 = e13 - e11;
        double c20 = (1 - c17) * Math.tan(e10);
        double c21 = Math.atan(c20);
        double c22 = Math.sin(c21);
        double c23 = Math.cos(c21);
        double c24 = (1 - c17) * Math.tan(e12);
        double c25 = Math.atan(c24);
        double c26 = Math.sin(c25);
        double c27 = Math.cos(c25);
        double c29 = c18;
        double c31 = (c27 * Math.sin(c29) * c27 * Math.sin(c29))
                + (c23 * c26 - c22 * c27 * Math.cos(c29))
                * (c23 * c26 - c22 * c27 * Math.cos(c29));
        double c33 = (c22 * c26) + (c23 * c27 * Math.cos(c29));
        double c35 = Math.sqrt(c31) / c33;
        double c36 = Math.atan(c35);
        double c38 = 0;
        if (c31 == 0) {
            c38 = 0;
        } else {
            c38 = c23 * c27 * Math.sin(c29) / Math.sqrt(c31);
        }
        double c40 = 0;
        if ((Math.cos(Math.asin(c38)) * Math.cos(Math.asin(c38))) == 0) {
            c40 = 0;
        } else {
            c40 = c33 - 2 * c22 * c26
                    / (Math.cos(Math.asin(c38)) * Math.cos(Math.asin(c38)));
        }
        double c41 = Math.cos(Math.asin(c38)) * Math.cos(Math.asin(c38))
                * (c15 * c15 - c16 * c16) / (c16 * c16);
        double c43 = 1 + c41 / 16384
                * (4096 + c41 * (-768 + c41 * (320 - 175 * c41)));
        double c45 = c41 / 1024 * (256 + c41 * (-128 + c41 * (74 - 47 * c41)));
        double c47 = c45
                * Math.sqrt(c31)
                * (c40 + c45
                / 4
                * (c33 * (-1 + 2 * c40 * c40) - c45 / 6 * c40
                * (-3 + 4 * c31) * (-3 + 4 * c40 * c40)));
        double c50 = c17
                / 16
                * Math.cos(Math.asin(c38))
                * Math.cos(Math.asin(c38))
                * (4 + c17
                * (4 - 3 * Math.cos(Math.asin(c38))
                * Math.cos(Math.asin(c38))));
        double c52 = c18
                + (1 - c50)
                * c17
                * c38
                * (Math.acos(c33) + c50 * Math.sin(Math.acos(c33))
                * (c40 + c50 * c33 * (-1 + 2 * c40 * c40)));
        double c54 = c16 * c43 * (Math.atan(c35) - c47);
        // return distance in meter
        return c54;
    }


    public static byte[] getDirection(double bearing) {
        //A -> E 상행(0x01)
        //E -> A 하행(0x02)
        //예외처리(0x00)
        if (85 <= bearing && bearing <= 145) {
            return new byte[]{0x02};
        } else if (265 <= bearing && bearing <= 325) {
            return new byte[]{0x01};
        } else {
            return new byte[]{0x00};
        }
    }

}

package neighbor.com.mbis.Packet;

/**
 * Created by user on 2016-08-25.
 */
public class B_0x31 extends Body {
    public B_0x31(byte[] sendDate, byte[] sendTime, byte[] eventDate, byte[] eventTime, byte[] routeInfo, byte[] GPSInfo, byte[] diviceState) {
        super(sendDate, sendTime, eventDate, eventTime, routeInfo, GPSInfo, diviceState);
        fullBody = new byte[75];

    }

    @Override
    public byte[] getBody() {
        return fullBody;
    }
    public byte[] addEtcBody(byte[] driveDate, byte[] startTime, byte[] stationID, byte[] stationNum, byte[] driveNum, byte[] eventNum) {


        if(checkByteLength_0x31(driveDate, startTime, stationID, stationNum, driveNum, eventNum)) {
            byte[] buf = tb.mergyByte(tb.mergyByte(tb.mergyByte(driveDate, startTime), tb.mergyByte(stationID, stationNum)), tb.mergyByte(driveNum, eventNum));
            fullBody = tb.mergyByte(defaultBody, buf);
            return fullBody;
        } else return errcode;
    }

    private boolean checkByteLength_0x31(byte[] driveDate, byte[] startTime, byte[] stationID, byte[] stationNum, byte[] driveNum, byte[] eventNum) {
        if (driveDate.length == 6 &&
                startTime.length == 6 &&
                stationID.length == 10 &&
                stationNum.length == 2 &&
                driveNum.length == 2 &&
                eventNum.length == 2
                ) {
            return true;
        } else return false;
    }
}
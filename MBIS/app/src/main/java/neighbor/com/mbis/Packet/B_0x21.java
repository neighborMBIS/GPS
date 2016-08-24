package neighbor.com.mbis.Packet;

//역 도착
public class B_0x21 extends Body{
    public B_0x21(byte[] sendDate, byte[] sendTime, byte[] eventDate, byte[] eventTime, byte[] routeInfo, byte[] GPSInfo, byte[] diviceState) {
        super(sendDate, sendTime, eventDate, eventTime, routeInfo, GPSInfo, diviceState);
        fullBody = new byte[63];
    }

    @Override
    public byte[] getBody() {
        return fullBody;
    }

    public byte[] addEtcBody(byte[] stationID, byte[] stationNum, byte[] beforeToAfterSec, byte[] etc) {
        if(checkByteLength_0x21(stationID, stationNum, beforeToAfterSec, etc)) {
            byte[] buf = tb.mergyByte(tb.mergyByte(stationID, stationNum), tb.mergyByte(beforeToAfterSec, etc));
            fullBody = tb.mergyByte(defaultBody, buf);
            return fullBody;
        } else return errcode;
    }


    private boolean checkByteLength_0x21(byte[] stationID, byte[] stationNum, byte[] beforeToAfterSec, byte[] etc) {
        if (stationID.length == 10 &&
                stationNum.length == 2 &&
                beforeToAfterSec.length == 2 &&
                etc.length == 2
                ) {
            return true;
        } else return false;
    }

}

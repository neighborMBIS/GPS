package neighbor.com.mbis.Packet;

//운행 시작
public class B_0x15 extends Body{
    public B_0x15(byte[] sendDate, byte[] sendTime, byte[] eventDate, byte[] eventTime, byte[] routeInfo, byte[] GPSInfo, byte[] diviceState) {
        super(sendDate, sendTime, eventDate, eventTime, routeInfo, GPSInfo, diviceState);
        fullBody = new byte[51];
    }

    @Override
    public byte[] getBody() {
        return fullBody;
    }

    public byte[] addEtcBody(byte[] driverDivision, byte[] etc) {
        if(checkByteLength_0x15(driverDivision, etc)) {
            fullBody = tb.mergyByte(tb.mergyByte(defaultBody, driverDivision), etc);
            return fullBody;
        } else return errcode;
    }
    private boolean checkByteLength_0x15(byte[] driverDivision, byte[] etc) {
        if (driverDivision.length ==1 &&
                etc.length == 3
                ) {
            return true;
        } else return false;
    }
}

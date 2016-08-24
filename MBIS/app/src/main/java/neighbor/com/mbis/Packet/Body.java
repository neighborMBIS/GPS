package neighbor.com.mbis.Packet;

/*
sendDate : 전송일자(6)
sendTime : 전송시각(6)
eventDate : 발생일자(6)
eventTime : 발생시각(6)
routeInfo : 노선정보(10)
GPSInfo : GPS정보(12)
diviceState : 기기상태(1)
 */

import neighbor.com.mbis.function.TransByte;

public abstract class Body{

    //defaultBody : 기본정보에 들어가는 내용
    protected byte[] defaultBody;
    //만약 잘못된 크기의 값을 입력하면 -128을 리턴하게 했음(모든 Body 하위 클래스 동일)
    protected byte[] errcode = {-128};
    //fullBody : 기본정보 이외의 운행구분, 지점ID, 지점 순번 등의 추가 항목이 포함된 값
    //운행 시작, 도착, 출발에 따라 크기가 가변적이다.
    //정의는 구상클래스에서 정의됨.
    protected byte[] fullBody;

    TransByte tb = TransByte.getInstance();

    public Body(byte[] sendDate, byte[] sendTime, byte[] eventDate, byte[] eventTime, byte[] routeInfo, byte[] GPSInfo, byte[] diviceState) {
        defaultBody = new byte[45];
        addDefaultBody(sendDate, sendTime, eventDate, eventTime, routeInfo, GPSInfo, diviceState);
    }
    //fullBody를 리턴하는 메서드 여기선 추상화만 해놓음.
    public abstract byte[] getBody();

    //기본정보 합치는 코드.
    public byte[] addDefaultBody(byte[] sendDate, byte[] sendTime, byte[] eventDate, byte[] eventTime, byte[] routeInfo, byte[] GPSInfo, byte[] diviceState) {

        byte[] buf1 = tb.mergyByte(tb.mergyByte(sendDate, sendTime), tb.mergyByte(eventDate, eventTime));
        byte[] buf2 = tb.mergyByte(tb.mergyByte(routeInfo, GPSInfo), diviceState);

        if(checkByteLength(sendDate, sendTime, eventDate, eventTime, routeInfo, GPSInfo, diviceState)) {
            defaultBody = tb.mergyByte(buf1, buf2);
            return defaultBody;
        } else return errcode;
    }

    private boolean checkByteLength(byte[] sendDate, byte[] sendTime, byte[] eventDate, byte[] eventTime, byte[] routeInfo, byte[] GPSInfo, byte[] diviceState) {
        if (sendDate.length ==6 &&
                sendTime.length == 6 &&
                eventDate.length == 6 &&
                eventTime.length == 6 &&
                routeInfo.length == 10 &&
                GPSInfo.length == 12 &&
                diviceState.length == 1
                ) {
            return true;
        } else return false;
    }

}

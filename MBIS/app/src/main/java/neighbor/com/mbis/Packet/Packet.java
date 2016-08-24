package neighbor.com.mbis.Packet;

import android.util.Log;

import neighbor.com.mbis.function.TransByte;


public class Packet {
    //전송 할 전체 패킷
    private byte[] packet;

    //싱글톤
    private static Packet ourInstance = new Packet();
    public static Packet getInstance() {
        return ourInstance;
    }

    //헤더
    private byte[] header;
    //바이트 통합하기 위하여 세팅
    TransByte tb = TransByte.getInstance();

    //헤더는 10바이트이므로 객체 생성되자마자 10바이트 줌
    private Packet() {
        header = new byte[10];
    }
    //현재 헤더 가져오기
    public byte[] getHeader() {
        return header;
    }
    //여기서 헤더 지정 해 주어야 함.
    //만약에 헤더 입력 할 바이트의 길이가 1,1,2,2,4가 맞지 않는다면 -128을 리턴
    //맞으면 그걸 다 합친 10바이트 header 리턴
    public byte[] setHeader(byte[] version, byte[] opcode, byte[] sr_cnt, byte[] device_id, byte[] data_length) {
        byte[] errcode = {-128};

        if(checkByteLength(version, opcode, sr_cnt, device_id, data_length)) {
            header = tb.mergyByte(tb.mergyByte(tb.mergyByte(tb.mergyByte(version, opcode), sr_cnt), device_id), data_length);
            return header;
        } else {
            Log.d("setHeaderError", "byte length error");
            return errcode;
        }
    }

    //길이 검사하는 코드
    public boolean checkByteLength(byte[] version, byte[] opcode, byte[] sr_cnt, byte[] device_id, byte[] data_length) {
        if(version.length == 1 && opcode.length == 1 && sr_cnt.length == 2 && device_id.length == 2 && data_length.length == 4) {
            return true;
        } else return false;
    }

    //바디의 바이트를 붙여 패킷을 만든다.
    //그 후 리턴
    public byte[] addBodyPacket(byte[] b) {
        packet = tb.mergyByte(getHeader(), b);
        return packet;
    }

}

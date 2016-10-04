package neighbor.com.test321.Util;

import java.text.Normalizer;

import neighbor.com.test321.Function.Func;

/**
 * Created by user on 2016-09-01.
 */
public class OP_code {

    private static OP_code ourInstance = null;

    public static OP_code getInstance(byte[] b) {
        if(ourInstance == null) {
            ourInstance = new OP_code(b);
            return ourInstance;
        } else {
            return ourInstance;
        }
    }


    Form_Header h = Form_Header.getInstance();
    Form_Body_default bd = Form_Body_default.getInstance();
    Form_Body_ArriveStation bas = Form_Body_ArriveStation.getInstance();

    byte[] headerBuf = null;
    byte[] bodyBuf_Default = null;
    byte[] bodyBuf_ArriveStation = null;

    private OP_code(byte[] op) {
        makeHeader();
        makeBodyDefault();

        if(op[0] == 0x15) {
            //drive start
        } else if (op[0] == 0x21) {
            //station arrive
            SendData.sendData = makeBodyArriveStation();
        } else if(op[0] == 0x22) {
            //station start
        } else if(op[0] == 0x31) {
            //drive end
        }
    }

    private byte[] makeHeader() {
        headerBuf = new byte[Position.HEADER_SIZE];

        putHeader(h.getVersion(), Position.HEADER_VERSION_START);
        putHeader(h.getOp_code(), Position.HEADER_OPCODE);
        putHeader(h.getSr_cnt(), Position.HEADER_SRCNT);
        putHeader(h.getDeviceID(), Position.HEADER_DEVICEID);
        putHeader(h.getLocalCode(), Position.HEADER_LOCALCODE);
        putHeader(h.getDataLength(), Position.HEADER_DATALENGTH);

        return headerBuf;
    }
    private byte[] makeBodyDefault() {
        bodyBuf_Default = new byte[Position.BODY_DEFAULT_SIZE];

        putBody_Default(headerBuf, Position.HEADER_VERSION_START);

        putBody_Default(bd.getSendDate(), Position.BODY_DEFAULT_SENDYEAR_DATE);
        putBody_Default(bd.getSendTime(), Position.BODY_DEFAULT_SENDHOUR_TIME);
        putBody_Default(bd.getEventDate(), Position.BODY_DEFAULT_EVENTYEAR_DATE);
        putBody_Default(bd.getEventTime(), Position.BODY_DEFAULT_EVENTHOUR_TIME);
        putBody_Default(bd.getRouteInfo(), Position.BODY_DEFAULT_ROUTEID_INFO);
        putBody_Default(bd.getGpsInfo(), Position.BODY_DEFAULT_LOCATIONX_GPS);
        putBody_Default(bd.getDeviceState(), Position.BODY_DEFAULT_DEVICESTATE);

        return bodyBuf_Default;
    }
    private byte[] makeBodyArriveStation() {
        bodyBuf_ArriveStation = new byte[Position.BODY_STATION_ARRIVE_SIZE];

        putBody_ArriveStation(bodyBuf_Default, Position.HEADER_VERSION_START);
        putBody_ArriveStation(bas.getStationId(), Position.BODY_STATION_ARRIVE_STATIONID);
        putBody_ArriveStation(bas.getStationTurn(), Position.BODY_STATION_ARRIVE_STATIONTURN);
        putBody_ArriveStation(bas.getAdjacentTravelTime(), Position.BODY_STATION_ARRIVE_ADJACENTTRAVELTIME);
        putBody_ArriveStation(bas.getReservation(), Position.BODY_STATION_ARRIVE_RESERVATION);

        return bodyBuf_ArriveStation;
    }

    private void putHeader(byte[] b, int position) {
        System.arraycopy(b, 0, headerBuf, position, b.length);
    }
    private void putBody_Default(byte[] b, int position) {
        System.arraycopy(b, 0, bodyBuf_Default, position, b.length);
    }
    private void putBody_ArriveStation(byte[] b, int position) {
        System.arraycopy(b, 0, bodyBuf_ArriveStation, position, b.length);
    }
}

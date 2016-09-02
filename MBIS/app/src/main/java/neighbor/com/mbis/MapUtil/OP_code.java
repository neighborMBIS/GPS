package neighbor.com.mbis.MapUtil;

import neighbor.com.mbis.MapUtil.Form.Form_Body_ArriveStation;
import neighbor.com.mbis.MapUtil.Form.Form_Body_EndDrive;
import neighbor.com.mbis.MapUtil.Form.Form_Body_StartDrive;
import neighbor.com.mbis.MapUtil.Form.Form_Body_StartStation;
import neighbor.com.mbis.MapUtil.Form.Form_Body_default;
import neighbor.com.mbis.MapUtil.Form.Form_Header;

/**
 * Created by user on 2016-09-01.
 */
public class OP_code {
    Form_Header h = Form_Header.getInstance();
    Form_Body_default bd = Form_Body_default.getInstance();
    Form_Body_ArriveStation bas = Form_Body_ArriveStation.getInstance();
    Form_Body_StartDrive bsd = Form_Body_StartDrive.getInstance();
    Form_Body_StartStation bss = Form_Body_StartStation.getInstance();

    static byte[] headerBuf = null;
    static byte[] bodyBuf_Default = null;
    static byte[] bodyBuf_ArriveStation = null;
    static byte[] bodyBuf_StartStation = null;
    static byte[] bodyBuf_StartDrive = null;


    public OP_code(byte[] op) {
        h.setOp_code(op);
        makeHeader();
        makeBodyDefault();

        if(op[0] == 0x15) {
            //drive start
            SendData.sendData = makeBodyStartDrive();
//            SendData.sendData = op;
        } else if (op[0] == 0x21) {
            //station arrive
            SendData.sendData = makeBodyArriveStation();
//            SendData.sendData = op;
        } else if(op[0] == 0x22) {
            //station start
            SendData.sendData = makeBodyStartStation();
//            SendData.sendData = op;
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
    private byte[] makeBodyStartStation() {
        bodyBuf_StartStation = new byte[Position.BODY_STATION_START_SIZE];

        putBody_StartStation(bodyBuf_Default, Position.HEADER_VERSION_START);
        putBody_StartStation(bss.getStationId(), Position.BODY_STATION_START_STATIONID);
        putBody_StartStation(bss.getStationTurn(), Position.BODY_STATION_START_STATIONTURN);
        putBody_StartStation(bss.getDriveTurn(), Position.BODY_STATION_START_DRIVETURN);
        putBody_StartStation(bss.getServiceTime(), Position.BODY_STATION_START_SERVICETIME);
        putBody_StartStation(bss.getAdjacentTravelTime(), Position.BODY_STATION_START_ADJACENTTRAVELTIME);
        putBody_StartStation(bss.getReservation(), Position.BODY_STATION_START_RESERVATION);

        return bodyBuf_StartStation;
    }
    private byte[] makeBodyStartDrive() {
        bodyBuf_StartDrive = new byte[Position.BODY_DRIVE_START_SIZE];

        putBody_StartDrive(bodyBuf_Default, Position.HEADER_VERSION_START);
        putBody_StartDrive(bsd.getDriveDivision(), Position.BODY_DRIVE_START_DRIVEDIVISION);
        putBody_StartDrive(bsd.getReservation(), Position.BODY_DRIVE_START_RESERVATION);

        return bodyBuf_StartDrive;
    }

    private void putHeader(byte[] b, int position) {
        System.arraycopy(b, 0, headerBuf, position, b.length);
    }
    private void putBody_Default(byte[] b, int position) {
        System.arraycopy(b, 0, bodyBuf_Default, position, b.length);
    }
    private void putBody_StartDrive(byte[] b, int position) {
        System.arraycopy(b, 0, bodyBuf_StartDrive, position, b.length);
    }
    private void putBody_ArriveStation(byte[] b, int position) {
        System.arraycopy(b, 0, bodyBuf_ArriveStation, position, b.length);
    }
    private void putBody_StartStation(byte[] b, int position) {
        System.arraycopy(b, 0, bodyBuf_StartStation, position, b.length);
    }
}

package neighbor.com.mbis.Function;

import neighbor.com.mbis.Activity.MapActivity;
import neighbor.com.mbis.MapUtil.Form.Form_Body_ArriveStation;
import neighbor.com.mbis.MapUtil.Form.Form_Body_Default;
import neighbor.com.mbis.MapUtil.Form.Form_Body_Emergency;
import neighbor.com.mbis.MapUtil.Form.Form_Body_EndDrive;
import neighbor.com.mbis.MapUtil.Form.Form_Body_Offence;
import neighbor.com.mbis.MapUtil.Form.Form_Body_StartDrive;
import neighbor.com.mbis.MapUtil.Form.Form_Body_StartStation;
import neighbor.com.mbis.MapUtil.Form.Form_Header;
import neighbor.com.mbis.MapUtil.Value.MapVal;

/**
 * Created by user on 2016-09-12.
 */
public class Setter {
    static Form_Header hd = Form_Header.getInstance();
    static Form_Body_Default bd = Form_Body_Default.getInstance();
    static Form_Body_ArriveStation bas = Form_Body_ArriveStation.getInstance();
    static Form_Body_StartStation bss = Form_Body_StartStation.getInstance();
    static Form_Body_StartDrive bsd = Form_Body_StartDrive.getInstance();
    static Form_Body_EndDrive bed = Form_Body_EndDrive.getInstance();
    static Form_Body_Offence bof = Form_Body_Offence.getInstance();
    static Form_Body_Emergency beg = Form_Body_Emergency.getInstance();


    static MapVal mv = MapVal.getInstance();

    public static void setHeader() {
        hd.setVersion(mv.getVersion());
        hd.setSr_cnt(mv.getSr_cnt());
        hd.setDeviceID(mv.getDeviceID());
        hd.setLocalCode(mv.getLocalCode());
        hd.setDataLength(mv.getDataLength());
    }

    public static void setBody_Default() {
        bd.setSendDate(mv.getSendYear(), mv.getSendMonth(), mv.getSendDay());
        bd.setEventDate(mv.getEventYear(), mv.getEventMonth(), mv.getEventDay());
        bd.setSendTime(mv.getSendHour(), mv.getSendMin(), mv.getSendSec());
        bd.setEventTime(mv.getEventHour(), mv.getEventMin(), mv.getEventSec());
        bd.setRouteInfo(mv.getRouteID(), mv.getRouteNum(), mv.getRouteForm(), mv.getRouteDivision());
        bd.setGpsInfo(mv.getLocationX(), mv.getLocationY(), mv.getBearing(), mv.getSpeed());
        bd.setDeviceState(0);

    }

    public static void setBody_ArriveStation() {
        bas.setStationId(mv.getArriveStationID());
        bas.setStationTurn(mv.getArriveStationTurn());
        bas.setAdjacentTravelTime(mv.getAdjacentTravelTime());
        bas.setReservation(mv.getReservation());
    }

    public static void setBody_StartStation() {
        bss.setStationId(mv.getArriveStationID());
        bss.setStationTurn(mv.getArriveStationTurn());
        bss.setDriveTurn(mv.getDriveTurn());
        bss.setServiceTime(mv.getServiceTime());
        bss.setAdjacentTravelTime(mv.getAdjacentTravelTime());
        bss.setReservation(mv.getReservation());
        MapActivity.arriveStationTurn++;
    }

    public static void setBody_StartDrive() {
        bsd.setDriveDivision(mv.getDriveDivision());
        bsd.setReservation(mv.getReservation());
    }

    public static void setBody_EndDrive() {
        bed.setDriveDate(mv.getDriveDate());
        bed.setStartTime(mv.getDriveStartTime());
        bed.setStationId(mv.getArriveStationID());
        bed.setStationTurn(mv.getArriveStationTurn());
        bed.setDriveTurn(mv.getDriveTurn());
        bed.setDetectStationNum(mv.getDetectStationNum());
        bed.setUndetectStationNum(mv.getUndetectStationNum());
        bed.setDetectCrossRoadNum(mv.getDetectCrossRoadNum());
        bed.setUndetectCrossRoadNum(mv.getUndetectCrossRoadNum());
        bed.setReservation(mv.getReservation());
    }

    public static void setBody_Offence() {
        bof.setPassStationId(mv.getArriveStationID());
        bof.setPassStationNum(mv.getArriveStationTurn());
        bof.setArriveStationId(mv.getAfterArriveStationId());
        bof.setArriveStationNum(mv.getAfterArriveStationTurn());
        bof.setOffenceCode(mv.getOffenceCode());
        bof.setSpeeding_ending(mv.getSpeeding_ending());
        bof.setReservation(mv.getReservation());
    }

    public static void setBody_Emergency() {
        beg.setPassStationId(mv.getArriveStationID());
        beg.setPassStationNum(mv.getArriveStationTurn());
        beg.setArriveStationId(mv.getAfterArriveStationId());
        beg.setArriveStationNum(mv.getAfterArriveStationTurn());
        beg.setEmergencyCode(mv.getEmergencyCode());
        beg.setReservation(mv.getReservation());
    }
}

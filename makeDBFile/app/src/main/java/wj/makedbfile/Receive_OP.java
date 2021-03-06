package wj.makedbfile;


import wj.makedbfile.Function.Func;

/**
 * Created by user on 2016-09-30.
 */

public class Receive_OP {
    MapVal mv = MapVal.getInstance();
    public Receive_OP(byte op_code) {
        switch (op_code) {
            case OPUtil.OP_ACK:
                break;
            case OPUtil.OP_NACK:
                break;
            case OPUtil.OP_USER_CERTIFICATION_AFTER_DEVICEID_SEND:
                addUtilUserCertificationAfterDeviceIdSend();
                break;
            case OPUtil.OP_OTHER_BUS_INFO:
                addUtilOtherBusInfo();
                break;
            case OPUtil.OP_ROUTE_STATION_DATA_INFO:
                addUtilRouteStationDataInfo();
                break;
            case OPUtil.OP_ROUTE_DATA_INFO:
                addUtilRouteDataInfo();
                break;
            case OPUtil.OP_STATION_DATA_INFO:
                addUtilStationDataInfo();
                break;
        }
    }

    private void addUtilUserCertificationAfterDeviceIdSend() {
        byte[] deviceID = new byte[8];
        for(int i=0 ; i < deviceID.length ; i++) {
            deviceID[i] = Data.readData[i+BytePosition.BODY_USER_CERTIFICATION_AFTER_DEVICEID];
        }
        mv.setDeviceID(Func.byteToLong(deviceID));
    }

    private void addUtilOtherBusInfo() {
        byte[] beforeBusDis = new byte[1];
        byte[] beforeBusTime = new byte[1];
        byte[] afterBusDis = new byte[1];
        byte[] afterBusTime = new byte[1];

        byte[] beforeBusNum = new byte[2];
        byte[] afterBusNum = new byte[2];
        beforeBusDis[0] = Data.readData[BytePosition.BODY_OTHER_BUS_INFO_BEFORE_BUS_DISTANCE];
        beforeBusTime[0] = Data.readData[BytePosition.BODY_OTHER_BUS_INFO_BEFORE_BUS_TIME];
        afterBusDis[0] = Data.readData[BytePosition.BODY_OTHER_BUS_INFO_AFTER_BUS_DISTANCE];
        afterBusTime[0] = Data.readData[BytePosition.BODY_OTHER_BUS_INFO_AFTER_BUS_TIME];

        mv.setBeforeBusDistance(Func.byteToInteger(beforeBusDis, 1));
        mv.setBeforeBusTime(Func.byteToInteger(beforeBusTime, 1));

        mv.setAfterBusDistance(Func.byteToInteger(afterBusDis, 1));
        mv.setAfterBusTime(Func.byteToInteger(afterBusTime, 1));

        beforeBusNum[0] = Data.readData[BytePosition.BODY_OTHER_BUS_INFO_BEFORE_BUS_NUM];
        beforeBusNum[1] = Data.readData[BytePosition.BODY_OTHER_BUS_INFO_BEFORE_BUS_NUM+1];
        afterBusNum[0] = Data.readData[BytePosition.BODY_OTHER_BUS_INFO_AFTER_BUS_NUM];
        afterBusNum[1] = Data.readData[BytePosition.BODY_OTHER_BUS_INFO_AFTER_BUS_NUM+1];
        mv.setBeforeBusNum(Func.byteToInteger(beforeBusNum, 2));
        mv.setAfterBusNum(Func.byteToInteger(afterBusNum, 2));
    }

    private void addUtilRouteStationDataInfo() {
        byte[] revisionNum = new byte[2];

        byte[] routeID = new byte[8];
        byte[] routeNum1 = new byte[5];
        byte[] routeNum2 = new byte[2];
        byte[] routeForm = new byte[1];
        byte[] routeDivision = new byte[2];

        byte[] applyDate = new byte[6];
        byte[] applyTime = new byte[6];

        byte[] totalStationNum = new byte[2];


        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_STATION_REVISION_NUM, revisionNum, 0,revisionNum.length);

        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_STATION_ROUTEID_INFO, routeID, 0,routeID.length);
        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_STATION_ROUTENUM, routeNum1, 0,routeNum1.length);
        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_STATION_ROUTENUMEXPANSION, routeNum2, 0,routeNum2.length);
        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_STATION_ROUTEFORM, routeForm, 0,routeForm.length);
        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_STATION_ROUTEDIVISION, routeDivision, 0,routeDivision.length);

        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_STATION_APPLY_SENDYEAR_DATE, applyDate, 0,applyDate.length);
        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_STATION_APPLY_SENDHOUR_TIME, applyTime, 0,applyTime.length);

        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_STATION_TOTALSTATIONNUM, totalStationNum, 0,totalStationNum.length);

        mv.setRevisionNum_RS(Func.byteToInteger(revisionNum, 2));

        mv.setRouteID_RS(Func.byteToLong(routeID));
        mv.setRouteNum_RS(new String(routeNum1) + "-" + new String(routeNum2));
        mv.setRouteForm_RS(new String(routeForm));
        mv.setRouteDivision_RS(new String(routeDivision));

        mv.setApplyDate_RS(new String(applyDate));
        mv.setApplyTime_RS(new String(applyTime));

        mv.setTotalStationNum_RS(Func.byteToInteger(totalStationNum, 2));

    }

    private void addUtilRouteDataInfo() {
        byte[] revisionNum = new byte[2];

        byte[] applyDate = new byte[6];
        byte[] applyTime = new byte[6];

        byte[] totalRouteNum = new byte[1];


        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_REVISION_NUM, revisionNum, 0,revisionNum.length);

        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_APPLY_SENDYEAR_DATE, applyDate, 0,applyDate.length);
        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_APPLY_SENDHOUR_TIME, applyTime, 0,applyTime.length);

        System.arraycopy(Data.readData, BytePosition.BODY_ROUTE_TOTALROUTENUM, totalRouteNum, 0,totalRouteNum.length);

        mv.setRevisionNum_R(Func.byteToInteger(revisionNum, 2));

        mv.setApplyDate_R(new String(applyDate));
        mv.setApplyTime_R(new String(applyTime));

        mv.setTotalRouteNum_R(Func.byteToInteger(totalRouteNum, 1));

    }

    private void addUtilStationDataInfo() {
        byte[] revisionNum = new byte[2];

        byte[] applyDate = new byte[6];
        byte[] applyTime = new byte[6];

        byte[] totalStationNum = new byte[2];


        System.arraycopy(Data.readData, BytePosition.BODY_STATION_REVISION_NUM, revisionNum, 0,revisionNum.length);

        System.arraycopy(Data.readData, BytePosition.BODY_STATION_APPLY_SENDYEAR_DATE, applyDate, 0,applyDate.length);
        System.arraycopy(Data.readData, BytePosition.BODY_STATION_APPLY_SENDHOUR_TIME, applyTime, 0,applyTime.length);

        System.arraycopy(Data.readData, BytePosition.BODY_STATION_TOTALSTATIONNUM, totalStationNum, 0,totalStationNum.length);

        mv.setRevisionNum_S(Func.byteToInteger(revisionNum, 2));

        mv.setApplyDate_S(new String(applyDate));
        mv.setApplyTime_S(new String(applyTime));

        mv.setTotalStationNum_S(Func.byteToInteger(totalStationNum, 2));

    }

}

package neighbor.com.test123.ddd;

/**
 * Created by user on 2016-09-02.
 */
public class MapVal {

    private static MapVal ourInstance = null;

    public static MapVal getInstance() {
        if (ourInstance == null) {
            ourInstance = new MapVal();
            return ourInstance;
        } else {
            return ourInstance;
        }
    }

    //자동로그인 유무
    public static boolean autoLoginFlag = false;

    //헤더
    private int version = 1, sr_cnt = 1, localCode = 1, dataLength;
    private long deviceID;

    //기본정보
    private int sendYear, sendMonth, sendDay, sendHour, sendMin, sendSec,
            eventYear, eventMonth, eventDay, eventHour, eventMin, eventSec,
            locationX, locationY, bearing, speed, gpsState;
    private long routeID;
    private String routeNum, routeForm, routeDivision;

    //시 도 출 종 위반 돌발 앞뒤
    private int reservation;

    //도출종위반
    private long arriveStationID;
    private int arriveStationTurn;

    //도출
    private int adjacentTravelTime;

    //출
    private int serviceTime;

    //시도출종
    private int driveDivision;

    //종
    private String driveDate;
    private String driveStartTime;
    private int detectStationArriveNum;
    private int detectStationStartNum;

    //위반, 돌발
    private long afterArriveStationId;
    private int afterArriveStationTurn;
    private int speeding_ending;

    //위반
    private int offenceCode;

    //돌발
    private int emergencyCode;


    //앞뒤차정보
    private int beforeBusDistance, beforeBusTime, beforeBusNum, afterBusDistance, afterBusTime, afterBusNum,
                remainDistance, remainTime;




    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSr_cnt() {
        return sr_cnt;
    }

    public void setSr_cnt(int sr_cnt) {
        this.sr_cnt = sr_cnt;
    }

    public int getLocalCode() {
        return localCode;
    }

    public void setLocalCode(int localCode) {
        this.localCode = localCode;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(long deviceID) {
        this.deviceID = deviceID;
    }

    public int getSendYear() {
        return sendYear;
    }

    public void setSendYear(int sendYear) {
        this.sendYear = sendYear;
    }

    public int getSendMonth() {
        return sendMonth;
    }

    public void setSendMonth(int sendMonth) {
        this.sendMonth = sendMonth;
    }

    public int getSendDay() {
        return sendDay;
    }

    public void setSendDay(int sendDay) {
        this.sendDay = sendDay;
    }

    public int getSendHour() {
        return sendHour;
    }

    public void setSendHour(int sendHour) {
        this.sendHour = sendHour;
    }

    public int getSendMin() {
        return sendMin;
    }

    public void setSendMin(int sendMin) {
        this.sendMin = sendMin;
    }

    public int getSendSec() {
        return sendSec;
    }

    public void setSendSec(int sendSec) {
        this.sendSec = sendSec;
    }

    public int getEventYear() {
        return eventYear;
    }

    public void setEventYear(int eventYear) {
        this.eventYear = eventYear;
    }

    public int getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(int eventMonth) {
        this.eventMonth = eventMonth;
    }

    public int getEventDay() {
        return eventDay;
    }

    public void setEventDay(int eventDay) {
        this.eventDay = eventDay;
    }

    public int getEventHour() {
        return eventHour;
    }

    public void setEventHour(int eventHour) {
        this.eventHour = eventHour;
    }

    public int getEventMin() {
        return eventMin;
    }

    public void setEventMin(int eventMin) {
        this.eventMin = eventMin;
    }

    public int getEventSec() {
        return eventSec;
    }

    public void setEventSec(int eventSec) {
        this.eventSec = eventSec;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public int getBearing() {
        return bearing;
    }

    public void setBearing(int bearing) {
        this.bearing = bearing;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGpsState() {
        return gpsState;
    }

    public void setGpsState(int gpsState) {
        this.gpsState = gpsState;
    }

    public long getRouteID() {
        return routeID;
    }

    public void setRouteID(long routeID) {
        this.routeID = routeID;
    }

    public String getRouteNum() {
        return routeNum;
    }

    public void setRouteNum(String routeNum) {
        this.routeNum = routeNum;
    }

    public String getRouteForm() {
        return routeForm;
    }

    public void setRouteForm(String routeForm) {
        this.routeForm = routeForm;
    }

    public String getRouteDivision() {
        return routeDivision;
    }

    public void setRouteDivision(String routeDivision) {
        this.routeDivision = routeDivision;
    }

    public int getReservation() {
        return reservation;
    }

    public void setReservation(int reservation) {
        this.reservation = reservation;
    }

    public long getArriveStationID() {
        return arriveStationID;
    }

    public void setArriveStationID(long arriveStationID) {
        this.arriveStationID = arriveStationID;
    }

    public int getArriveStationTurn() {
        return arriveStationTurn;
    }

    public void setArriveStationTurn(int arriveStationTurn) {
        this.arriveStationTurn = arriveStationTurn;
    }

    public int getAdjacentTravelTime() {
        return adjacentTravelTime;
    }

    public void setAdjacentTravelTime(int adjacentTravelTime) {
        this.adjacentTravelTime = adjacentTravelTime;
    }


    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getDriveDivision() {
        return driveDivision;
    }

    public void setDriveDivision(int driveDivision) {
        this.driveDivision = driveDivision;
    }

    public String getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(String driveDate) {
        this.driveDate = driveDate;
    }

    public String getDriveStartTime() {
        return driveStartTime;
    }

    public void setDriveStartTime(String driveStartTime) {
        this.driveStartTime = driveStartTime;
    }

    public int getDetectStationArriveNum() {
        return detectStationArriveNum;
    }

    public void setDetectStationArriveNum(int detectStationArriveNum) {
        this.detectStationArriveNum = detectStationArriveNum;
    }

    public int getDetectStationStartNum() {
        return detectStationStartNum;
    }

    public void setDetectStationStartNum(int detectStationStartNum) {
        this.detectStationStartNum = detectStationStartNum;
    }

    public long getAfterArriveStationId() {
        return afterArriveStationId;
    }

    public void setAfterArriveStationId(long afterArriveStationId) {
        this.afterArriveStationId = afterArriveStationId;
    }

    public int getAfterArriveStationTurn() {
        return afterArriveStationTurn;
    }

    public void setAfterArriveStationTurn(int afterArriveStationTurn) {
        this.afterArriveStationTurn = afterArriveStationTurn;
    }

    public int getOffenceCode() {
        return offenceCode;
    }

    public void setOffenceCode(int offenceCode) {
        this.offenceCode = offenceCode;
    }

    public int getSpeeding_ending() {
        return speeding_ending;
    }

    public void setSpeeding_ending(int speeding_ending) {
        this.speeding_ending = speeding_ending;
    }

    public int getEmergencyCode() {
        return emergencyCode;
    }

    public void setEmergencyCode(int emergencyCode) {
        this.emergencyCode = emergencyCode;
    }

    public int getBeforeBusDistance() {
        return beforeBusDistance;
    }

    public void setBeforeBusDistance(int beforeBusDistance) {
        this.beforeBusDistance = beforeBusDistance;
    }

    public int getBeforeBusTime() {
        return beforeBusTime;
    }

    public void setBeforeBusTime(int beforeBusTime) {
        this.beforeBusTime = beforeBusTime;
    }

    public int getBeforeBusNum() {
        return beforeBusNum;
    }

    public void setBeforeBusNum(int beforeBusNum) {
        this.beforeBusNum = beforeBusNum;
    }

    public int getAfterBusDistance() {
        return afterBusDistance;
    }

    public void setAfterBusDistance(int afterBusDistance) {
        this.afterBusDistance = afterBusDistance;
    }

    public int getAfterBusTime() {
        return afterBusTime;
    }

    public void setAfterBusTime(int afterBusTime) {
        this.afterBusTime = afterBusTime;
    }

    public int getAfterBusNum() {
        return afterBusNum;
    }

    public void setAfterBusNum(int afterBusNum) {
        this.afterBusNum = afterBusNum;
    }

    public int getRemainDistance() {
        return remainDistance;
    }

    public void setRemainDistance(int remainDistance) {
        this.remainDistance = remainDistance;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }
}

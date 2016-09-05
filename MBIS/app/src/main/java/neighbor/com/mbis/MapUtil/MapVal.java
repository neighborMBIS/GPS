package neighbor.com.mbis.MapUtil;

/**
 * Created by user on 2016-09-02.
 */
public class MapVal {

    private static MapVal ourInstance = null;

    public static MapVal getInstance() {
        if(ourInstance == null) {
            ourInstance = new MapVal();
            return ourInstance;
        } else {
            return ourInstance;
        }
    }

    //계산용 버퍼
    int arriveTimeBuf, startTimeBuf;

    //헤더
    private int version = 1, sr_cnt = 0, localCode = 0, dataLength = 0;
    private long deviceID = 0;

    //기본정보
    private int sendYear, sendMonth, sendDay, sendHour, sendMin, sendSec,
            eventYear, eventMonth, eventDay, eventHour, eventMin, eventSec,
            locationX, locationY, bearing, speed, gpsState;
    private long routeID;
    private String routeNum, routeForm, routeDivision;

    //시도출종
    private int reservation;

    //도출종
    private long arriveStationID;
    private int arriveStationTurn;

    //도출
    public static int bufTime;
    private int adjacentTravelTime;

    //출종
    private int driveTurn;

    //출
    private int serviceTime;

    //시
    private int driveDivision;

    //종
    private String driveDate;
    private String driveStartTime;
    private int detectStationNum;
    private int undetectStationNum;
    private int detectCrossRoadNum;
    private int undetectCrossRoadNum;


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

    public int getDriveTurn() {
        return driveTurn;
    }

    public void setDriveTurn(int driveTurn) {
        this.driveTurn = driveTurn;
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

    public int getDetectStationNum() {
        return detectStationNum;
    }

    public void setDetectStationNum(int detectStationNum) {
        this.detectStationNum = detectStationNum;
    }

    public int getUndetectStationNum() {
        return undetectStationNum;
    }

    public void setUndetectStationNum(int undetectStationNum) {
        this.undetectStationNum = undetectStationNum;
    }

    public int getDetectCrossRoadNum() {
        return detectCrossRoadNum;
    }

    public void setDetectCrossRoadNum(int detectCrossRoadNum) {
        this.detectCrossRoadNum = detectCrossRoadNum;
    }

    public int getUndetectCrossRoadNum() {
        return undetectCrossRoadNum;
    }

    public void setUndetectCrossRoadNum(int undetectCrossRoadNum) {
        this.undetectCrossRoadNum = undetectCrossRoadNum;
    }


    public int getArriveTimeBuf() {
        return arriveTimeBuf;
    }

    public void setArriveTimeBuf(int arriveTimeBuf) {
        this.arriveTimeBuf = arriveTimeBuf;
    }

    public int getStartTimeBuf() {
        return startTimeBuf;
    }

    public void setStartTimeBuf(int startTimeBuf) {
        this.startTimeBuf = startTimeBuf;
    }

}

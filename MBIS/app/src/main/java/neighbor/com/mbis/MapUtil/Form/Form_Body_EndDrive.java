package neighbor.com.mbis.MapUtil.Form;


import neighbor.com.mbis.Function.Func;

/**
 * Created by user on 2016-09-01.
 */
public class Form_Body_EndDrive {
    private static Form_Body_EndDrive ourInstance = new Form_Body_EndDrive();

    public static Form_Body_EndDrive getInstance() {
        return ourInstance;
    }

    private Form_Body_EndDrive() {
    }
    private byte[] driveDate;
    private byte[] startTime;
    private byte[] stationId;
    private byte[] stationTurn;
    private byte[] driveTurn;

    private byte[] detectStationNum;
    private byte[] undetectStationNum;
    private byte[] detectCrossRoadNum;
    private byte[] undetectCrossRoadNum;

    private byte[] reservation;

    public void setStationId(byte[] stationId) {
        this.stationId = stationId;
    }
    public void setStationId(long stationId) {
        this.stationId = Func.longToByte(stationId, 10);
    }
    public void setStationId(String stationId) {
        this.stationId = Func.longToByte(Long.parseLong(stationId), 10);
    }
    public void setStationTurn(byte[] stationNum) {
        this.stationTurn = stationNum;
    }
    public void setStationTurn(int stationNum) {
        this.stationTurn = Func.integerToByte(stationNum, 2);
    }

    public byte[] getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(byte[] driveDate) {
        this.driveDate = driveDate;
    }
    public void setDriveDate(String sendDate) {
        this.driveDate = Func.stringToByte(sendDate);
    }
    public void setDriveDate(String year, String month, String day) {
        String ymd = year + month + day;
        this.driveDate = Func.stringToByte(ymd);
    }
    public void setDriveDate(int year, int month, int day) {
        String y = String.format("%02d", year);
        String m = String.format("%02d", month);
        String d = String.format("%02d", day);
        String ymd = y + m + d;
        this.driveDate = Func.stringToByte(ymd);
    }

    public byte[] getStartTime() {
        return startTime;
    }

    public void setStartTime(byte[] startTime) {
        this.startTime = startTime;
    }
    public void setStartTime(String eventTime) {
        this.startTime = Func.stringToByte(eventTime);
    }
    public void setStartTime(String hour, String min, String sec) {
        String hms = hour + min + sec;
        this.startTime = Func.stringToByte(hms);
    }
    public void setStartTime(int hour, int min, int sec) {
        String h = String.format("%02d", hour);
        String m = String.format("%02d", min);
        String s = String.format("%02d", sec);
        String hms = h + m + s;
        this.startTime = Func.stringToByte(hms);
    }

    public byte[] getDriveTurn() {
        return driveTurn;
    }

    public void setDriveTurn(byte[] driveTurn) {
        this.driveTurn = driveTurn;
    }
    public void setDriveTurn(int driveTurn) {
        this.driveTurn = Func.integerToByte(driveTurn, 2);
    }
    public void setDriveTurn(String driveTurn) {
        this.driveTurn = Func.integerToByte(Integer.parseInt(driveTurn), 2);
    }

    public byte[] getDetectStationNum() {
        return detectStationNum;
    }

    public void setDetectStationNum(byte[] detectStationNum) {
        this.detectStationNum = detectStationNum;
    }
    public void setDetectStationNum(int detectStationNum) {
        this.detectStationNum = Func.integerToByte(detectStationNum, 2);
    }
    public void setDetectStationNum(String detectStationNum) {
        this.detectStationNum = Func.integerToByte(Integer.parseInt(detectStationNum), 2);
    }


    public byte[] getUndetectStationNum() {
        return undetectStationNum;
    }

    public void setUndetectStationNum(byte[] undetectStationNum) {
        this.undetectStationNum = undetectStationNum;
    }
    public void setUndetectStationNum(int undetectStationNum) {
        this.undetectStationNum = Func.integerToByte(undetectStationNum, 2);
    }
    public void setUndetectStationNum(String undetectStationNum) {
        this.undetectStationNum = Func.integerToByte(Integer.parseInt(undetectStationNum), 2);
    }

    public byte[] getDetectCrossRoadNum() {
        return detectCrossRoadNum;
    }

    public void setDetectCrossRoadNum(byte[] detectCrossRoadNum) {
        this.detectCrossRoadNum = detectCrossRoadNum;
    }
    public void setDetectCrossRoadNum(int detectCrossRoadNum) {
        this.detectCrossRoadNum = Func.integerToByte(detectCrossRoadNum, 2);
    }
    public void setDetectCrossRoadNum(String detectCrossRoadNum) {
        this.detectCrossRoadNum = Func.integerToByte(Integer.parseInt(detectCrossRoadNum), 2);
    }

    public byte[] getUndetectCrossRoadNum() {
        return undetectCrossRoadNum;
    }

    public void setUndetectCrossRoadNum(byte[] undetectCrossRoadNum) {
        this.undetectCrossRoadNum = undetectCrossRoadNum;
    }
    public void setUndetectCrossRoadNum(int undetectCrossRoadNum) {
        this.undetectCrossRoadNum = Func.integerToByte(undetectCrossRoadNum, 2);
    }
    public void setUndetectCrossRoadNum(String undetectCrossRoadNum) {
        this.undetectCrossRoadNum = Func.integerToByte(Integer.parseInt(undetectCrossRoadNum), 2);
    }

    public void setReservation(byte[] reservation) {
        this.reservation = reservation;
    }
    public void setReservation(int reservation) {
        this.reservation = Func.integerToByte(reservation, 2);
    }
    public void setReservation(String reservation) {
        this.reservation = Func.integerToByte(Integer.parseInt(reservation), 2);
    }

    public byte[] getStationId() {
        return stationId;
    }

    public byte[] getStationTurn() {
        return stationTurn;
    }

    public byte[] getReservation() {
        return reservation;
    }
}

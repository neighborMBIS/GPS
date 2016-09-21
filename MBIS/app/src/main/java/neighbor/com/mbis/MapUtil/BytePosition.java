package neighbor.com.mbis.MapUtil;

/**
 * Created by user on 2016-09-01.
 */
public class BytePosition {

    //HEADER
    public final static int HEADER_SIZE = 18;
    public final static int HEADER_VERSION_START = 0;
    public final static int HEADER_OPCODE = 1;
    public final static int HEADER_SRCNT = 2;
    public final static int HEADER_DEVICEID = 4;
    public final static int HEADER_LOCALCODE = 12;
    public final static int HEADER_DATALENGTH = 14;

    //BODY(DEFAULT)
    public final static int BODY_DEFAULT_SIZE = 73;
    public final static int BODY_DEFAULT_SENDYEAR_DATE = 18;
    public final static int BODY_DEFAULT_SENDMONTH = 20;
    public final static int BODY_DEFAULT_SENDDAY = 22;
    public final static int BODY_DEFAULT_SENDHOUR_TIME = 24;
    public final static int BODY_DEFAULT_SENDMIN = 26;
    public final static int BODY_DEFAULT_SENDSEC = 28;

    public final static int BODY_DEFAULT_EVENTYEAR_DATE = 30;
    public final static int BODY_DEFAULT_EVENTMONTH = 32;
    public final static int BODY_DEFAULT_EVENTDAY = 34;
    public final static int BODY_DEFAULT_EVENTHOUR_TIME = 36;
    public final static int BODY_DEFAULT_EVENTMIN = 38;
    public final static int BODY_DEFAULT_EVENTSEC = 40;

    public final static int BODY_DEFAULT_ROUTEID_INFO = 42;
    public final static int BODY_DEFAULT_ROUTENUM = 50;
    public final static int BODY_DEFAULT_ROUTENUMEXPANSION = 55;
    public final static int BODY_DEFAULT_ROUTEFORM = 57;
    public final static int BODY_DEFAULT_ROUTEDIVISION = 58;

    public final static int BODY_DEFAULT_LOCATIONX_GPS = 60;
    public final static int BODY_DEFAULT_LOCATIONY = 64;
    public final static int BODY_DEFAULT_BEARING = 68;
    public final static int BODY_DEFAULT_SPEED = 70;

    public final static int BODY_DEFAULT_DEVICESTATE = 72;


    //BODY(STATION_ARRIVE)
    public final static int BODY_STATION_ARRIVE_SIZE = 87;
    public final static int BODY_STATION_ARRIVE_STATIONID = 73;
    public final static int BODY_STATION_ARRIVE_STATIONTURN = 78;
    public final static int BODY_STATION_ARRIVE_ADJACENTTRAVELTIME = 80;
    public final static int BODY_STATION_ARRIVE_DRIVEDIVISION = 82;
    public final static int BODY_STATION_ARRIVE_RESERVATION = 83;

    //BODY(STATION_START)
    public final static int BODY_STATION_START_SIZE = 89;
    public final static int BODY_STATION_START_STATIONID = 73;
    public final static int BODY_STATION_START_STATIONTURN = 78;
    public final static int BODY_STATION_START_SERVICETIME = 80;
    public final static int BODY_STATION_START_ADJACENTTRAVELTIME = 82;
    public final static int BODY_STATION_START_DRIVEDIVISION = 84;
    public final static int BODY_STATION_START_RESERVATION = 85;

    //BODY(DRIVE_START)
    public final static int BODY_DRIVE_START_SIZE = 78;
    public final static int BODY_DRIVE_START_DRIVEDIVISION = 73;
    public final static int BODY_DRIVE_START_RESERVATION = 74;

    //BODY(DRIVE_END)
    public final static int BODY_DRIVE_END_SIZE = 99;
    public final static int BODY_DRIVE_END_DRIVEDATE = 73;
    public final static int BODY_DRIVE_END_STARTTIME = 79;
    public final static int BODY_DRIVE_END_STATIONID = 85;
    public final static int BODY_DRIVE_END_STATIONTURN = 90;
    public final static int BODY_DRIVE_END_DETECTSTATION_ARRIVENUM = 92;
    public final static int BODY_DRIVE_END_DETECTSTATION_STARTNUM = 93;
    public final static int BODY_DRIVE_END_DRIVEDIVISION = 94;
    public final static int BODY_DRIVE_END_RESERVATION = 95;

    //BODY(OFFENCE)
    public final static int BODY_OFFENCE_SIZE = 85;
    public final static int BODY_OFFENCE_PASS_STATIONID = 73;
    public final static int BODY_OFFENCE_PASS_STATIONTURN = 78;
    public final static int BODY_OFFENCE_OFFENCECODE = 80;
    public final static int BODY_OFFENCE_RESERVATION = 81;

    //BODY(EMERGENCY)
    public final static int BODY_EMERGENCY_SIZE = 85;
    public final static int BODY_EMERGENCY_PASS_STATIONID = 73;
    public final static int BODY_EMERGENCY_PASS_STATIONTURN = 78;
    public final static int BODY_EMERGENCY_EMERGENCYCODE = 80;
    public final static int BODY_EMERGENCY_RESERVATION = 81;

    //BODY(BUS LOCATION)
    public final static int BODY_BUSLOCATION_SIZE = 92;
    public final static int BODY_BUSLOCATION_PASS_STATIONID = 73;
    public final static int BODY_BUSLOCATION_PASS_STATIONTURN = 78;
    public final static int BODY_BUSLOCATION_ARRIVE_STATIONID = 80;
    public final static int BODY_BUSLOCATION_ARRIVE_STATIONTURN = 85;
    public final static int BODY_BUSLOCATION_DRIVEDIVISION = 87;
    public final static int BODY_BUSLOCATION_RESERVATION = 88;

}

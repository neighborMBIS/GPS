package neighbor.com.mbis.MapUtil;

/**
 * Created by user on 2016-09-01.
 */
public class Position {

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
    public final static int BODY_STATION_ARRIVE_SIZE = 89;
    public final static int BODY_STATION_ARRIVE_STATIONID = 73;
    public final static int BODY_STATION_ARRIVE_STATIONTURN = 83;
    public final static int BODY_STATION_ARRIVE_ADJACENTTRAVELTIME = 85;
    public final static int BODY_STATION_ARRIVE_RESERVATION = 87;

    //BODY(STATION_START)
    public final static int BODY_STATION_START_SIZE = 93;
    public final static int BODY_STATION_START_STATIONID = 73;
    public final static int BODY_STATION_START_STATIONTURN = 83;
    public final static int BODY_STATION_START_DRIVETURN = 85;
    public final static int BODY_STATION_START_SERVICETIME = 87;
    public final static int BODY_STATION_START_ADJACENTTRAVELTIME = 89;
    public final static int BODY_STATION_START_RESERVATION = 91;

    //BODY(DRIVE_START)
    public final static int BODY_DRIVE_START_SIZE = 77;
    public final static int BODY_DRIVE_START_DRIVEDIVISION = 73;
    public final static int BODY_DRIVE_START_RESERVATION = 74;

    //BODY(DRIVE_END)
    public final static int BODY_DRIVE_END_SIZE = 109;
    public final static int BODY_DRIVE_END_DRIVEDATE = 73;
    public final static int BODY_DRIVE_END_STARTTIME = 79;
    public final static int BODY_DRIVE_END_STATIONID = 85;
    public final static int BODY_DRIVE_END_STATIONTURN = 95;
    public final static int BODY_DRIVE_END_DRIVETURN = 97;
    public final static int BODY_DRIVE_END_DETECTIONSTATIONNUM = 99;
    public final static int BODY_DRIVE_END_UNSENTSTATIONNUM = 101;
    public final static int BODY_DRIVE_END_CROSSROADDETECTIONNUM = 103;
    public final static int BODY_DRIVE_END_UNSENTCROSSROADNUM = 105;
    public final static int BODY_DRIVE_END_RESERVATION = 107;
}

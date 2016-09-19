package neighbor.com.mbis.MapUtil.Value;

/**
 * Created by user on 2016-09-08.
 */
public class LogicBuffer {

    private static LogicBuffer ourInstance = null;

    public static LogicBuffer getInstance() {
        if(ourInstance == null) {
            ourInstance = new LogicBuffer();
            return ourInstance;
        } else {
            return ourInstance;
        }
    }

    //계산용 버퍼
    private int arriveTimeBuf, startTimeBuf, stationNumBuf, crossRoadNumBuf;

    public static int jumpBuf[] = new int[]{-2, -1, 0};
    public static int startBuf[] = new int[]{-10, -10, -10};
    public static double locationXBuf=0, locationYBuf=0;


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

    public int getStationNumBuf() {
        return stationNumBuf;
    }

    public void setStationNumBuf(int stationNumBuf) {
        this.stationNumBuf = stationNumBuf;
    }

    public int getCrossRoadNumBuf() {
        return crossRoadNumBuf;
    }

    public void setCrossRoadNumBuf(int crossRoadNumBuf) {
        this.crossRoadNumBuf = crossRoadNumBuf;
    }
}

package neighbor.com.mbis.MapUtil;

import java.util.ArrayList;

/**
 * Created by user on 2016-08-25.
 */
public class StationBuffer {
    private static StationBuffer ourInstance = new StationBuffer();

    public static StationBuffer getInstance() {
        return ourInstance;
    }

    private ArrayList<Double> referenceLatPosition;
    private ArrayList<Double> referenceLngPosition;
    private ArrayList<Double> distance;
    private ArrayList<Long> referenceStationId;
    private ArrayList<Double> remark;


    private StationBuffer() {
        referenceLatPosition = new ArrayList<Double>();
        referenceLngPosition = new ArrayList<Double>();
        distance = new ArrayList<Double>();
        referenceStationId = new ArrayList<Long>();
    }

    public ArrayList<Double> getDistance() {
        return distance;
    }

    public void setDistance(ArrayList<Double> distance) {
        this.distance = distance;
    }



    public ArrayList<Double> getReferenceLatPosition() {
        return referenceLatPosition;
    }

    public void setReferenceLatPosition(ArrayList<Double> referenceLatPosition) {
        this.referenceLatPosition = referenceLatPosition;
    }

    public ArrayList<Double> getReferenceLngPosition() {
        return referenceLngPosition;
    }

    public void setReferenceLngPosition(ArrayList<Double> referenceLngPosition) {
        this.referenceLngPosition = referenceLngPosition;
    }

    public ArrayList<Long> getReferenceStationId() {
        return referenceStationId;
    }

    public void setReferenceStationId(ArrayList<Long> refernceUniqueNum) {
        this.referenceStationId = refernceUniqueNum;
    }

    public ArrayList<Double> getRemark() {
        return remark;
    }

    public void setRemark(ArrayList<Double> remark) {
        this.remark = remark;
    }

    public void addReferenceLatPosition(double item) {
        referenceLatPosition.add(item);
    }
    public void addReferenceLngPosition(double item) {
        referenceLngPosition.add(item);
    }
    public void addReferenceStationId(long item) {
        referenceStationId.add(item);
    }
    public void addDistance(double item) {distance.add(item);}
    public void addRemark(double item) {remark.add(item);}

}

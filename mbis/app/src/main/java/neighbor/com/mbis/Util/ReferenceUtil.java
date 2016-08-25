package neighbor.com.mbis.Util;

import java.util.ArrayList;

/**
 * Created by user on 2016-08-25.
 */
public class ReferenceUtil {
    private static ReferenceUtil ourInstance = new ReferenceUtil();

    public static ReferenceUtil getInstance() {
        return ourInstance;
    }

    private ArrayList<String> referenceNamePosition;
    private ArrayList<Double> referenceLatPosition;
    private ArrayList<Double> referenceLngPosition;
    private ArrayList<Integer> refernceUniqueNum;

    public ArrayList<String> getReferenceNamePosition() {
        return referenceNamePosition;
    }

    public void setReferenceNamePosition(ArrayList<String> referenceNamePosition) {
        this.referenceNamePosition = referenceNamePosition;
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

    public ArrayList<Integer> getRefernceUniqueNum() {
        return refernceUniqueNum;
    }

    public void setRefernceUniqueNum(ArrayList<Integer> refernceUniqueNum) {
        this.refernceUniqueNum = refernceUniqueNum;
    }


    private ReferenceUtil() {
        referenceNamePosition = new ArrayList<String>();
        referenceLatPosition = new ArrayList<Double>();
        referenceLngPosition = new ArrayList<Double>();
        refernceUniqueNum = new ArrayList<Integer>();

        referenceNamePosition.add("00100-01");
        referenceNamePosition.add("00100-02");
        referenceNamePosition.add("00100-03");
        referenceNamePosition.add("00100-04");
        referenceNamePosition.add("00100-05");

        referenceLatPosition.add(37.49568);
        referenceLatPosition.add(37.49502);
        referenceLatPosition.add(37.49455);
        referenceLatPosition.add(37.49413);
        referenceLatPosition.add(37.49373);

        referenceLngPosition.add(127.12259);
        referenceLngPosition.add(127.12140);
        referenceLngPosition.add(127.12063);
        referenceLngPosition.add(127.11979);
        referenceLngPosition.add(127.11907);

        refernceUniqueNum.add(1000000001);
        refernceUniqueNum.add(1000000002);
        refernceUniqueNum.add(1000000003);
        refernceUniqueNum.add(1000000004);
        refernceUniqueNum.add(1000000005);

    }
}

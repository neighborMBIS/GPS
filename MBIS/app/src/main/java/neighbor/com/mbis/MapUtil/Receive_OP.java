package neighbor.com.mbis.MapUtil;

import neighbor.com.mbis.Function.Func;
import neighbor.com.mbis.MapUtil.OPUtil;
import neighbor.com.mbis.MapUtil.Value.MapVal;

/**
 * Created by user on 2016-09-30.
 */

public class Receive_OP {
    MapVal mv = MapVal.getInstance();
    public Receive_OP(byte op_code) {
        switch (op_code) {
            case OPUtil.OP_OTHER_BUS_INFO:
                addUtilOtherBusInfo();
                break;
        }
    }

    public void addUtilOtherBusInfo() {
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

}

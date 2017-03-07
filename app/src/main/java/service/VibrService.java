package service;

/**
 * Created by Blue on 2016/5/25.
 */
public class VibrService {


    // ---------------------------------得到传感器数据

    public static int[] bytesToAdvalue(String dataString) {
        byte[] readBuffer=dataString.getBytes();
        int[] sensData=new int[readBuffer.length>>1];
        for (int i = 0; i < readBuffer.length>>1; i++) {
            sensData[i] = (int) readBuffer[i + i] & 0xff;
            sensData[i] <<= 8;
            sensData[i] |= (int) readBuffer[i + i + 1] & 0xff;
            sensData[i] = (sensData[i] * 5000) >> 15; // AD值转电压值
            System.out.printf("采集到的AD值为: %d\r\n", sensData[i]);
        }
        return sensData;
    }

}

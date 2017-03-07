package bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Blue on 2016/5/25.
 */
public class VibrInfo {
    private String productMac = "";
    private String userName = "blue";

    private String fileName = "";
    private int freq = 100;
    private int sampleNum = 5;

//    private static final VibrInfo vibrInfo = new VibrInfo();
//
//    public static VibrInfo getvibrInfo() {
//        return vibrInfo;
//    }

    public VibrInfo(String userName,String productMac,String fileName, int freq, int sampleNum)
    {
        this.userName=userName;
        this.productMac=productMac;
        this.fileName=fileName;
        this.freq=freq;
        this.sampleNum=sampleNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public int getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(int sampleNum) {
        this.sampleNum = sampleNum;
    }

    public String getProductMac() {
        return productMac;
    }

    public void setProductMac(String productMac) {
        this.productMac = productMac;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    /********************* 创建Json串 ****************************/
    public String jsonCreat() {
        JSONObject root = new JSONObject();
        try {
            root.put("userName", this.userName);
            root.put("freq", this.freq);
            root.put("sampleNum", this.sampleNum);
            root.put("jsonType", "controlInfo");
            root.put("productMac", this.productMac);
            root.put("fileName", this.fileName);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return root.toString();
    }

    /********************* 解析Json串 ****************************/
    public boolean jsonResolve(String jsondata) {
        JSONObject root;
        try {
            root = new JSONObject(jsondata);
            // this.userID = root.getString("userID");
            // this.productID = root.getString("productID");
            this.freq = root.getInt("freq");
            this.sampleNum = root.getInt("sampleNum");
            this.userName = root.getString("userName");
            this.productMac = root.getString("productMac");
            this.fileName = root.getString("fileName");
            return true;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("无法解析json!\r\n");

            e.printStackTrace();
            return false;
        }
    }
}

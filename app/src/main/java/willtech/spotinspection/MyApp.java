package willtech.spotinspection;

import android.app.Application;
/**
 * Created by Blue on 2016/5/25.
 */
public class MyApp extends Application {
    public String recievedMsg="";
    public String userName="";
    public String productMac="";

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public String dataString="";


    private static final MyApp myApp = new MyApp();

    public static MyApp getMyApp() {
        return myApp;
    }
    public MyApp() {
    }





    public MyApp(String userName,String productMac)
    {
        this.userName=userName;
        this.productMac=productMac;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductMac() {
        return productMac;
    }

    public void setProductMac(String productMac) {
        this.productMac = productMac;
    }



    public String getRecievedMsg() {
        return recievedMsg;
    }

    public void setRecievedMsg(String recievedMsg) {
        this.recievedMsg = recievedMsg;
    }
}

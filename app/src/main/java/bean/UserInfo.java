package bean;

import org.json.JSONObject;
import org.json.JSONException;


/**
 * Created by Blue on 2016/3/31.
 */
public class UserInfo {


    private String userName="";
    private String userPassword="";
    private String jsonType="";
    private int id;

    private static final UserInfo userInfo = new UserInfo();

    public static UserInfo getvibrInfo() {
        return userInfo;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJsonType() {
        return jsonType;
    }

    public void setJsonType(String jsonType) {
        this.jsonType = jsonType;
    }


    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String creatUserJson()
    {
        JSONObject root = new JSONObject();
        try {
            root.put("jsonType", this.jsonType);
            root.put("userName", this.userName);
            root.put("passWord", this.userPassword);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return root.toString();
    }
}
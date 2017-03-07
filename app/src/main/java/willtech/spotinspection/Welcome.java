package willtech.spotinspection;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import bean.UserInfo;
import bean.VibrInfo;
import db.SqliteDB;
import impl.IhandleData;
import impl.IhandleMessge;
import service.MessageService;
import service.VibrService;

public class Welcome extends AppCompatActivity implements View.OnClickListener {
    private UserInfo user;
    private TextView textView;
    private Button connect;
    private Dialog progressDialog;
    private MyApp myApp;
    private final String spliter="WILLTECH_BLUE";


    /**
     * 消息处理器
     */
    private Handler handle = new Handler(){
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            String message = (String) msg.obj;
            myApp = (MyApp)getApplication();
            myApp.setRecievedMsg(message);
            String jsonstring,dataString,messageStrings[];
            messageStrings=message.split(spliter);
            if (messageStrings.length<=1)
            {
                jsonstring=messageStrings[0];
                whatTodo(jsonstring);
            }else{
                jsonstring=messageStrings[0];
                dataString=messageStrings[1];



            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            MessageService.sendMsg("ready to connect!");
        } catch (Exception e) {
            buildConnection();
            e.printStackTrace();

        }
        attemptLogin();         //尝试从本地数据库获取用户信息进行登入
        textView= (TextView) findViewById(R.id.tv);
        connect= (Button) findViewById(R.id.btn);
        connect.setOnClickListener(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /*********************** 服务器需要处理哪些事情 ***************************/
    public void whatTodo(String receivedDate) {
        String jsontype = judgeJsonType(receivedDate);
        switch (jsontype) {
            case "chatInfo":
                System.out.println("该Json串是chatInfo\r\n");
                break;
            case "loginResult":
                System.out.println("该Json串是loginInfo\r\n");
                loginResult(receivedDate);
                break;
            case "regResult":
                System.out.println("该Json串是regInfo\r\n");
                regResult(receivedDate);
                break;

            default:
                break;
        }
    }

    /**
     * 建立连接
     * @param isLogin 是否已经登录
     */
    private void buildConnection() {
        //存在当前用户，尝试连接服务器
//        if (null != user)
        {
            // 此处开启线程防止UI阻塞
            new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println("reached here");
                        MessageService.getMsgServ().startConnect(new IhandleMessge() {
                            @Override
                            public void handleMsg(String message) {
                                android.os.Message msg = handle.obtainMessage();
                                msg.obj = message;
                                handle.sendMessage(msg);
                            }
                        });
                    } catch (IOException e) {
                        handle.sendEmptyMessage(2);
                    }
                }
            }).start();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn:
                try {
                    MessageService.sendMsg("this is the test message");
                } catch (Exception e) {
                    buildConnection();
                    e.printStackTrace();

                }
                Intent intentLogin = new Intent(this, UserLogin.class);
                startActivity(intentLogin);
                break;
        }
    }
    /*********************** 判断是哪类json数据（clothes_info,user_info OR control_info） ***************************/
    public String judgeJsonType(String jsondate) {
        String jsontype = "chatInfo";
        JSONObject root;
        try {
            root = new JSONObject(jsondate);
            jsontype = root.getString("jsonType");
            System.out.println("该字符串是Json串，其类型是" + jsontype + "\r\n");
            return jsontype;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("无法解析json!\r\n");

            e.printStackTrace();
            return jsontype;
        }
    }

    //-----------------------------注册结果------------------------------------
    public boolean regResult(String receivedData)
    {
        JSONObject root;
        try {
            root = new JSONObject(receivedData);
            String regStat = root.getString("regStat");
            if (regStat.equals("Success"))
            {
                Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                UserInfo user=new UserInfo();
                user.setUserName(root.getString("userName"));
                user.setUserPassword(root.getString("passWord"));
                SqliteDB.getInstance(getApplicationContext()).saveUser(user);
                startActivity(new Intent(getApplicationContext(), DeviceList.class));
                return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(), "用户名已经存在！", Toast.LENGTH_SHORT).show();
                return false;
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("无法解析目标MAC地址!");

            e.printStackTrace();
            return false;
        }
    }
    //-----------------------------登入结果------------------------------------
    public boolean loginResult(String receivedData)
    {
        JSONObject root;
        try {
            root = new JSONObject(receivedData);
            String loginStat = root.getString("loginStat");
            if (loginStat.equals("Success"))
            {
                Toast.makeText(getApplicationContext(), "登入成功！", Toast.LENGTH_SHORT).show();
                MyApp.getMyApp().setUserName(root.getString("userName"));

                startActivity(new Intent(getApplicationContext(), DeviceList.class));
                return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("无法解析目标MAC地址!");

            e.printStackTrace();
            return false;
        }
    }

    //------------------------------------------尝试登入------------------------
    public void attemptLogin()    //返回登入字符串
    {
        List<UserInfo> userList;
        userList= SqliteDB.getInstance(getApplicationContext()).loadUser();
        if (!userList.isEmpty())
        {
            UserInfo user=userList.get(userList.size() - 1);
            String loginJson=user.creatUserJson();
            try {
                MessageService.sendMsg(loginJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            startActivity(new Intent(getApplicationContext(), UserLogin.class));
        }
    }
}

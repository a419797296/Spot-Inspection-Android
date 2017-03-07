package willtech.spotinspection;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bean.UserInfo;
import service.MessageService;

public class UserLogin extends AppCompatActivity {
    private EditText username;
    private EditText pwd;
    private Button btnLogin;
    private Button btnReg;
    private TextView textView;
    private List<UserInfo> dataList = new ArrayList<>();
    private MyApp myApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.etLoginUsername);
        pwd = (EditText) findViewById(R.id.etLoginPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnReg= (Button) findViewById(R.id.btnReg);
        textView= (TextView) findViewById(R.id.tvTest);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), loginJson, Toast.LENGTH_SHORT).show();
                Login();

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserReg.class));
            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //------------------------------------------尝试登入------------------------
    public void Login()    //返回登入字符串
    {
        UserInfo user=new UserInfo();
        String loginJson;
        String name=username.getText().toString();
        String pass=pwd.getText().toString();
        if(name.equals("") || name == null || pass.equals("") || pass == null){
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT);
            return;
        }
        else
        {
            user.setUserName(name);
            user.setUserPassword(pass);
            user.setJsonType("loginInfo");
            loginJson=user.creatUserJson();
            try {
                MessageService.sendMsg(loginJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

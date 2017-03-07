package willtech.spotinspection;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bean.UserInfo;
import service.MessageService;

public class UserReg extends AppCompatActivity {
    private EditText username;
    //private EditText tel;
    private EditText pwd;
    private EditText checkpwd;
    private Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnReg= (Button) findViewById(R.id.btnReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = (EditText) findViewById(R.id.etRegisterUsername);
                //  tel = (EditText) findViewById(R.id.etRegisterUsername);
                pwd = (EditText) findViewById(R.id.etRegisterPassword);
                checkpwd = (EditText) findViewById(R.id.etRegisterPP);
                String name = username.getText().toString().trim();
                String password1 = pwd.getText().toString().trim();
                String password2 = checkpwd.getText().toString().trim();
                //   String telphone = tel.getText().toString().trim();
                if (password1.length() < 5) {
                    Toast.makeText(getApplicationContext(), "密码长度最少6个字符！", Toast.LENGTH_SHORT).show();
                } else if (name.length() != 11) {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
                } else if (!password1.equals(password2)) {
                    Toast.makeText(getApplicationContext(), "两次密码不一样，请重新确认！", Toast.LENGTH_SHORT).show();
                } else {
                    UserInfo user = new UserInfo();
                    user.setUserName(name);
                    user.setUserPassword(password1);
                    user.setJsonType("regInfo");
                    String regJson = user.creatUserJson();
                    try {
                        MessageService.sendMsg(regJson.getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

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

}

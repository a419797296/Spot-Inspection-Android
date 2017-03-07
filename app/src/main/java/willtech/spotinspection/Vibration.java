package willtech.spotinspection;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import bean.VibrInfo;
import service.MessageService;
import service.VibrService;

public class Vibration extends AppCompatActivity {
    EditText freq,length,fileName;
    TextView adValue;
    boolean  startReceiveData=false;
    private char[] receivedByteData = new char[1000];
    private int[] sensData = new int[5000];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        freq = (EditText) findViewById(R.id.freq);
        length = (EditText) findViewById(R.id.length);
        fileName = (EditText) findViewById(R.id.fileName);
        adValue = (TextView) findViewById(R.id.tvReceiveContent);


        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sample_freq,sample_nums,sample_fileName;
                String userName,productMac;
                adValue.setText("");
                sample_freq=freq.getText().toString();
                sample_nums=length.getText().toString();
                sample_fileName=fileName.getText().toString();
                userName=MyApp.getMyApp().getUserName();
                productMac=MyApp.getMyApp().getProductMac();

                VibrInfo vibrInfo=new VibrInfo(userName,productMac,sample_fileName,Integer.parseInt(sample_freq),Integer.parseInt(sample_nums));
                String virbCntlCmd=vibrInfo.jsonCreat();
                try {
                    MessageService.sendMsg(virbCntlCmd);
                    MyApp myApp;
                    myApp = (MyApp)getApplication();
                    String adValueString;
//                    while(myApp.getDataString().equals("")){
//                        myApp = (MyApp)getApplication();
//                        try
//                        {
//                            Thread.currentThread().sleep(10);//毫秒
//                        } catch(Exception e){}
//                    }
                    adValueString=myApp.getDataString();
                    myApp.setDataString("");

                    int sensorDataNum=adValueString.length()/2-1;
                    int[] sensorData;
                    sensorData= VibrService.bytesToAdvalue(adValueString);
                    for (int i=0;i<sensorDataNum;i++)
                    {
                        adValue.append(String.valueOf(sensorData[i])+"\r\n");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("----------------------------------");

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

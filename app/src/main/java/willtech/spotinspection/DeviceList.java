package willtech.spotinspection;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class DeviceList extends AppCompatActivity {
    Button productMac;
    Button btnAddDevice;
//    private List<ConfigProduct> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnAddDevice= (Button) findViewById(R.id.btnAddDevice);
        productMac= (Button) findViewById(R.id.MacAddress);
//        productMac.setText(new Login().getProductMac());
//
//        productList= SqliteDB.getInstance(getApplicationContext()).loadProduct();
//        if (!productList.isEmpty())
//        {
//            ConfigProduct product=productList.get(productList.size() - 1);
//            productMac.setText(ConfigProduct.getProductMac());
//            System.out.print("成功恢复mac地址\r\n");
//        }


//        productMac.setText(new SocketClient().getProductMac());
        btnAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceList.this, ProductReg.class));
            }
        });

        productMac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeviceList.this, Vibration.class);
                MyApp.getMyApp().setProductMac(productMac.getText().toString());
                startActivity(i);
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

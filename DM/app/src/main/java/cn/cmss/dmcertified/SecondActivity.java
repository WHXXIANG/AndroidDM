package cn.cmss.dmcertified;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    private  final  String  TAG = "SecondActivity";
    private  Button  debug_button;
    private  Button  realse_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        debug_button = findViewById(R.id.unisdebug_id);
        realse_button= findViewById(R.id.unisrealse_id);

        //debug_button.setOnClickListener(View view);
        debug_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FILENAME = "dm.txt";
                String string = "hello world!";
                Log.d(TAG, "whx debug_button click ");
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(string.getBytes());
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        realse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File fileDir = getFilesDir();
                Log.d(TAG, "whx getFilesDir(): "+fileDir);
                File file = new File(fileDir, "dm.txt");
                if (file.exists()) {
                    Log.d(TAG, "whx realse_button delete file: ");
                    file.delete();
                } else {
                    Log.d(TAG, "whx realse_button file not exit: ");
                }
            }
        });
    }


}
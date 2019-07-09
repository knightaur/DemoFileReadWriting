package sg.edu.rp.c346.demofilereadwriting;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button btnWrite, btnRead;
    String folderLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        btnRead = findViewById(R.id.btnRead);
        btnWrite = findViewById(R.id.btnWrite);

        if(checkPermission()){
            String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";
            File folder = new File(folderLocation);
            if (folder.exists() == false){
                boolean result = folder.mkdir();
                if (result == true){
                    Log.d("FileRead/Write", "Folder created");
                }
            }
        }

        btnWrite.setOnClickListener((v) ->{
            try {
                folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";
                File targetFile = new File(folderLocation , "data.txt");
                FileWriter writer = new FileWriter(targetFile , true);
                writer.write("Hello world" + "\n");
                writer.flush();
                writer.close();
                Toast.makeText(MainActivity. this , "Added!", Toast. LENGTH_LONG ).show();
            } catch (Exception e){
                Toast.makeText(MainActivity. this , "Failed to write!", Toast. LENGTH_LONG ).show();
                e.printStackTrace();
            }
        });

        btnRead.setOnClickListener((v) ->{
            folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";
            File targetFile = new File(folderLocation , "data.txt");
            if(targetFile.exists() == true){
                String data = "";
                try{
                    FileReader reader = new FileReader(targetFile);
                    BufferedReader br = new BufferedReader(reader);
                    String line = br.readLine();
                    while(line!=null){
                        data += line + "\n";
                        line = br.readLine();
                    }
                    tv.setText(data);
                    br.close();
                    reader.close();
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this , "Failed to read!", Toast. LENGTH_LONG).show();
                    e.printStackTrace();
                }
                Log.d("Content", data);
            }
        });


    }

    private boolean checkPermission(){
        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED
                && permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return false;
        }
    }
}

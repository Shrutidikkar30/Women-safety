package phonecard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.womensafetyapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class callpage extends AppCompatActivity {
    EditText phoneNo;
    FloatingActionButton callbtn;
    static int PERMISSION_CODE=100;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callpage);

        phoneNo=findViewById(R.id.editTextTextPersonName);
        callbtn=findViewById(R.id.button2);

        if (ContextCompat.checkSelfPermission(callpage.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED);
        {
            ActivityCompat.requestPermissions(callpage.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);

        }
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneno = phoneNo.getText().toString();
             if (phoneno.length()==10){
                 Intent i = new Intent(Intent.ACTION_CALL);
                 i.setData(Uri.parse("tel:" + phoneno));
                 startActivity(i);
             }
             else
             {
                 Toast.makeText(getApplicationContext(),"Invalid Number",Toast.LENGTH_SHORT).show();
             }
            }
        });
    }
}
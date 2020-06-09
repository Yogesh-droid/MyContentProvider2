package com.example.mycontentprovider2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button,button2;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button);
        button2=findViewById(R.id.b2);
        textView=findViewById(R.id.textV);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put("name",editText.getText().toString());
                Uri uri=getContentResolver().insert(MyContentProvider.CONTENT_URI,values);
                Toast.makeText(getApplicationContext(),""+uri,Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=getContentResolver().query(MyContentProvider.CONTENT_URI,null
                ,null,null,"_id");
                StringBuffer buffer=new StringBuffer();
                while (c.moveToNext()){
                    String id=c.getString(0);
                    String name=c.getString(1);
                    buffer.append(id+"\t"+""+name+"\n");
                }
                textView.setText(buffer.toString());
            }
        });
    }
}

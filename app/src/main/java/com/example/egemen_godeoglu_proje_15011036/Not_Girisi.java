package com.example.egemen_godeoglu_proje_15011036;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class Not_Girisi extends AppCompatActivity {
    Button kaydet;
    EditText not_text;
    Notlar not;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not__girisi);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        not = (Notlar) bundle.getSerializable("not_bilgileri");
        String text="Baslik:"+not.getBaslik()+"\n"+
                "Oncelik:"+not.getOncelik()+"\n"+
                "Renk:"+not.getRenk()+"\n"+
                "Tarih:"+ DateFormat.getDateInstance(DateFormat.FULL).format(not.getZaman().getTime())+
                "Saat"+"Saat: "+not.getZaman().get(Calendar.HOUR_OF_DAY)+"-"+not.getZaman().get(Calendar.MINUTE);
        //Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();

        not_text=(EditText) findViewById(R.id.editText_notText);

        kaydet=(Button) findViewById(R.id.button_kaydet);
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                not.setText(not_text.getText().toString());
                Calendar now=Calendar.getInstance();
                not.setZaman(now);
                MainActivity.db.myDao().insertNot(not);
                Toast.makeText(getBaseContext(), "Not eklendi!", Toast.LENGTH_LONG).show();
                Intent main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(main);
            }
        });

    }
}

package com.example.egemen_godeoglu_proje_15011036;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Guncelle_Sil extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{
    private int mDefaultColor;
    private TextView guncelle_t_tarih,guncelle_t_saat,guncelle_olusturulma_tarihi;
    private EditText guncelle_edit_baslik,guncelle_edit_text;
    private String[] oncelikler={"Düşük", "Orta", "Yüksek"};
    Button guncelle_renk_gosterici;
    Button guncelle_renk;
    Button guncelle_tarih;
    Button guncelle_saat;
    Button guncelle_adres;
    Button guncelle;
    private Spinner guncelle_oncelik;
    private ArrayAdapter<String> dataAdapterForOncelik;
    private ImageView fotograf;
    private Button resim;
    private Uri secilen_resim;
    private static  final int REQUEST_CODE=1;
    private Calendar guncelle_zaman,guncelle_hatirlatici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guncelle__sil);


        guncelle_edit_baslik=(EditText) findViewById(R.id.editText_guncelle_baslik);
        guncelle_olusturulma_tarihi=(TextView)findViewById(R.id.textView_guncelle_olusturulma_tarihi);
        guncelle_t_tarih=(TextView) findViewById(R.id.textView_guncelle_tarih);
        guncelle_t_saat=(TextView) findViewById(R.id.textView_guncelle_saat);
        guncelle_edit_text=(EditText)findViewById(R.id.editText_guncelle_text);

        guncelle_oncelik = (Spinner) findViewById(R.id.spinner_oncelik);
        dataAdapterForOncelik = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, oncelikler);
        dataAdapterForOncelik.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guncelle_oncelik.setAdapter(dataAdapterForOncelik);
        guncelle_oncelik.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) guncelle_oncelik.getSelectedView()).setTextColor(Color.BLUE);
            }
        });

        guncelle_renk_gosterici = (Button) findViewById(R.id.button_guncelle_renkgoster);
        guncelle_renk_gosterici.setBackgroundColor(mDefaultColor);

        guncelle_renk=(Button) findViewById(R.id.button_guncelle_renk);
        guncelle_renk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        guncelle_tarih=(Button) findViewById(R.id.button_guncelle_tarih);
        guncelle_tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TarihSecici tarih_secici=new TarihSecici();
                tarih_secici.show(getSupportFragmentManager(), "Tarih Seç");
            }
        });

        guncelle_saat=(Button) findViewById(R.id.button_guncelle_saat);
        guncelle_saat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaatSecici saat_secici=new SaatSecici();
                saat_secici.show(getSupportFragmentManager(), "Zaman Secici");
            }
        });

        fotograf=(ImageView) findViewById(R.id.imageView_guncelle_foto);
        resim = (Button) findViewById(R.id.button_guncelle_resim);
        resim.setOnClickListener(new ImagePıckListener());

        guncelle_adres=(Button) findViewById(R.id.button_guncelle_adres);
        guncelle_adres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adres_secici=new Intent(getBaseContext(), Adres_Secici.class);
                startActivity(adres_secici);
            }
        });

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Notlar guncel_not = (Notlar) bundle.getSerializable("guncelle_bilgileri");

        guncelle_zaman=guncel_not.getZaman();
        guncelle_hatirlatici=guncel_not.getHatirlatici();
        mDefaultColor=guncel_not.getRenk();
        guncelle_renk_gosterici.setBackgroundColor(mDefaultColor);
        guncelle_edit_text.setText(guncel_not.getText());
        guncelle_edit_text.setBackgroundColor(mDefaultColor);
        guncelle_edit_baslik.setText(guncel_not.getBaslik());

        String olusturulma_zamani=guncelle_zaman.get(Calendar.DAY_OF_MONTH)+"/"+guncelle_zaman.get(Calendar.MONTH)+"/"+
                guncelle_zaman.get(Calendar.YEAR) + "  "+guncelle_zaman.get(Calendar.HOUR_OF_DAY)+"-"+
                guncelle_zaman.get(Calendar.MINUTE);
        guncelle_olusturulma_tarihi.setText("Oluşturulma zamanı:"+olusturulma_zamani);

        String hatirlatma_zamani=guncelle_hatirlatici.get(Calendar.DAY_OF_MONTH)+"/"+guncelle_hatirlatici.get(Calendar.MONTH)+"/"+
                guncelle_hatirlatici.get(Calendar.YEAR);
        guncelle_t_tarih.setText("Zaman:"+hatirlatma_zamani);

        String hatirlatma_saat=guncelle_hatirlatici.get(Calendar.HOUR_OF_DAY)+"-"+guncelle_hatirlatici.get(Calendar.MINUTE);
        guncelle_t_saat.setText("Saat: "+hatirlatma_saat);


        guncelle=(Button) findViewById(R.id.button_guncelle);
        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notlar not=new Notlar();
                not.setZaman(Calendar.getInstance());
                not.setHatirlatici(guncelle_hatirlatici);
                not.setRenk(mDefaultColor);
                if (secilen_resim != null) {
                    not.setResim(secilen_resim.toString());
                } else {
                    not.setResim("0");
                }
                not.setBaslik(guncelle_edit_baslik.getText().toString());
                not.setOncelik(guncelle_oncelik.getSelectedItem().toString());
                not.setText(guncelle_edit_text.getText().toString());
                MainActivity.db.myDao().updateNot(not);
                Toast.makeText(getBaseContext(), "Not güncellendi!", Toast.LENGTH_LONG).show();
                Intent main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(main);


            }
        });



    }
    public class ImagePıckListener implements View.OnClickListener{
        @Override
        public void onClick(View v){

            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*, video/*");
            if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(Intent.createChooser(galleryIntent, "Resim Seç"), REQUEST_CODE);
            }
        }
    }

    public void onActivityResult(int requestcode,int resultcode,Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if(resultcode == Activity.RESULT_OK && data!=null){
            switch (requestcode){
                case REQUEST_CODE:
                    secilen_resim = data.getData();
                    fotograf.setImageURI(secilen_resim);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int yil, int ay, int gun) {
        guncelle_hatirlatici.set(yil,ay,gun);

        String currentDateString= "Tarih: "+guncelle_hatirlatici.get(Calendar.DAY_OF_MONTH)+"/"+guncelle_hatirlatici.get(Calendar.MONTH)+"/"+
                guncelle_hatirlatici.get(Calendar.YEAR);
        guncelle_t_tarih.setText(currentDateString);

    }

    @Override
    public void onTimeSet(TimePicker view, int saat, int dakika) {
        guncelle_hatirlatici.set(Calendar.HOUR_OF_DAY, saat);
        guncelle_hatirlatici.set(Calendar.MINUTE,dakika);
        guncelle_t_saat.setText("Saat: "+guncelle_hatirlatici.get(Calendar.HOUR_OF_DAY)+"-"+guncelle_hatirlatici.get(Calendar.MINUTE));
    }

    public void openColorPicker(){
        AmbilWarnaDialog renkSecici=new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                guncelle_renk_gosterici.setBackgroundColor(mDefaultColor);
            }
        });
        renkSecici.show();
    }
}

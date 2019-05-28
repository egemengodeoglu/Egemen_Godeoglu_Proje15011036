package com.example.egemen_godeoglu_proje_15011036;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
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
import java.text.DateFormat;
import java.util.Calendar;
import yuku.ambilwarna.AmbilWarnaDialog;

public class Not_Ekle_Guncelle extends AppCompatActivity implements DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener {
    int mDefaultColor;
    EditText baslik;
    Calendar zaman;
    Calendar hatirlatici;
    Button renk_gosterici;
    Button renk;
    Button tarih;
    Button saat;
    Button adres;
    Button devam;
    private ImageView fotograf;
    private Button resim;
    private Uri secilen_resim;
    private String[] oncelikler={"Düşük", "Orta", "Yüksek"};
    private Spinner spinner_oncelik;
    private ArrayAdapter<String> dataAdapterForOncelik;
    private static  final int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not__ekle__guncelle);

        baslik=(EditText) findViewById(R.id.editText_baslik);
        zaman= Calendar.getInstance();
        hatirlatici= Calendar.getInstance();

        spinner_oncelik = (Spinner) findViewById(R.id.spinner_oncelik);
        dataAdapterForOncelik = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, oncelikler);
        dataAdapterForOncelik.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_oncelik.setAdapter(dataAdapterForOncelik);
        spinner_oncelik.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinner_oncelik.getSelectedView()).setTextColor(Color.BLUE);
            }
        });

        fotograf=(ImageView) findViewById(R.id.imageView_foto);
        resim = (Button) findViewById(R.id.button_resim);
        resim.setOnClickListener(new ImagePıckListener());

        renk_gosterici = (Button) findViewById(R.id.button_renk_goster);
        renk_gosterici.setBackgroundColor(mDefaultColor);

        renk=(Button) findViewById(R.id.button_renk);
        renk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        tarih=(Button) findViewById(R.id.button_tarih);
        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TarihSecici tarih_secici=new TarihSecici();
                tarih_secici.show(getSupportFragmentManager(), "Tarih Seç");
            }
        });

        saat=(Button) findViewById(R.id.button_saat);
        saat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaatSecici saat_secici=new SaatSecici();
                saat_secici.show(getSupportFragmentManager(), "Zaman Secici");
            }
        });

        adres=(Button) findViewById(R.id.button_adres);
        adres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adres_secici=new Intent(getBaseContext(), Adres_Secici.class);
                startActivity(adres_secici);
            }
        });
        devam=(Button) findViewById(R.id.button_devam);
        devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(baslik.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Başlık boş bırakılamaz!", Toast.LENGTH_SHORT).show();
                }else{
                    Notlar not=new Notlar();
                    not.setBaslik(baslik.getText().toString());
                    not.setOncelik(spinner_oncelik.getSelectedItem().toString());
                    not.setRenk(mDefaultColor);
                    not.setZaman(zaman);
                    not.setHatirlatici(hatirlatici);
                    if (secilen_resim != null) {
                        not.setResim(secilen_resim.toString());
                    } else {
                        not.setResim("0");
                    }

                    String deneme="Baslik:"+not.getBaslik()+"\n"+
                            "Oncelik:"+not.getOncelik()+"\n"+
                            "Renk:"+not.getRenk()+"\n"+
                            "Tarih:"+DateFormat.getDateInstance(DateFormat.FULL).format(not.getZaman().getTime())+
                            "Saat"+"Saat: "+not.getZaman().get(Calendar.HOUR_OF_DAY)+"-"+not.getZaman().get(Calendar.MINUTE);
                    Toast.makeText(v.getContext(), deneme, Toast.LENGTH_LONG).show();
                    Intent not_girisi=new Intent(getBaseContext(), Not_Girisi.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("not_bilgileri", not);
                    not_girisi.putExtras(bundle);
                    startActivity(not_girisi);
                }
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
        hatirlatici.set(yil,ay,gun);

        String currentDateString= "Tarih: "+hatirlatici.get(Calendar.DAY_OF_MONTH)+"/"+hatirlatici.get(Calendar.MONTH)+"/"+
                hatirlatici.get(Calendar.YEAR);
        TextView tarih_secilen=(TextView) findViewById(R.id.tarih);
        tarih_secilen.setText(currentDateString);

    }

    @Override
    public void onTimeSet(TimePicker view, int saat, int dakika) {
        hatirlatici.set(Calendar.HOUR_OF_DAY, saat);
        hatirlatici.set(Calendar.MINUTE,dakika);
        TextView saat_secilen=(TextView) findViewById(R.id.saat);
        saat_secilen.setText("Saat: "+hatirlatici.get(Calendar.HOUR_OF_DAY)+"-"+hatirlatici.get(Calendar.MINUTE));
    }

    public void openColorPicker(){
        AmbilWarnaDialog renkSecici=new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                renk_gosterici.setBackgroundColor(mDefaultColor);
            }
        });
        renkSecici.show();
    }

}

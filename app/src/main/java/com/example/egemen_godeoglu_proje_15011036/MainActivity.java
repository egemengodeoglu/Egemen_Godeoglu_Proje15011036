package com.example.egemen_godeoglu_proje_15011036;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Drawable d_sil;
    Button not_guncelle;
    RecyclerView recyclerView;
    NotAdapter notAdapter;
    List<Notlar> notlarım;
    public static DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d_sil = this.getResources().getDrawable(R.drawable.sil);

        db= Room.databaseBuilder(getApplicationContext(),DataBase.class, "notdb").allowMainThreadQueries().build();
        notlarım = MainActivity.db.myDao().getNotlar();

        Notlar not=new Notlar();
        not.setBaslik("Deneme");
        not.setText("Proje sorunsuz çalışıyor");
        not.setZaman(Calendar.getInstance());
        not.setHatirlatici(Calendar.getInstance());
        not.setOncelik("Normal");
        not.setRenk(50);
        not.setResim("0");
        notlarım.add(not);

        not=new Notlar();
        not.setBaslik("Deneme2");
        not.setText("Proje sorunsuz çalışıyor2");
        not.setHatirlatici(Calendar.getInstance());
        not.setZaman(Calendar.getInstance());
        not.setOncelik("Yüksek");
        not.setRenk(10);
        not.setResim("0");
        notlarım.add(not);


        not_guncelle=(Button) findViewById(R.id.button_Not_Guncelle);
        not_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Not_Ekle_Guncelle.class);
                startActivity(i);
            }
        });

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        notAdapter= new NotAdapter(this, notlarım);
        recyclerView.setAdapter(notAdapter);


    }
public class NotAdapter extends RecyclerView.Adapter<NotAdapter.NotViewHolder>{

    private Context myContext;
    private List<Notlar> not_listesi;

    public NotAdapter(Context myContext, List<Notlar> not_listesi) {
        this.myContext = myContext;
        this.not_listesi = not_listesi;
    }

    @Override
    public NotViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(myContext);
        View view=inflater.inflate(R.layout.list_layout, null);
        return new NotViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NotViewHolder notViewHolder, int i) {
        Notlar notum=not_listesi.get(i);
        notViewHolder.secilen=not_listesi.get(i);

        if(!notum.getResim().equals("0")){
            Uri myUri = Uri.parse(notum.getResim());
            notViewHolder.imageView.setImageURI(myUri);
        }

        notViewHolder.textViewBaslik.setText(notum.getBaslik());
        notViewHolder.textViewText.setText(notum.getText());
        notViewHolder.textViewDosya.setText("Dosya eklenmedi! ");
        notViewHolder.textViewOncelik.setText(String.valueOf("Oncelik: "+notum.getOncelik()));
        String timeText=notum.getZaman().get(Calendar.DAY_OF_MONTH)+"/"+notum.getZaman().get(Calendar.MONTH)+"/"+
                notum.getZaman().get(Calendar.YEAR)+"  "+notum.getZaman().get(Calendar.HOUR_OF_DAY)+"-"+
                notum.getZaman().get(Calendar.MINUTE);
        notViewHolder.textViewZaman.setText(timeText);
        notViewHolder.arkaplan.setBackgroundColor(notum.getRenk());
    }

    @Override
    public int getItemCount() {
        return not_listesi.size();
    }

    class NotViewHolder extends RecyclerView.ViewHolder{
        Button sil;
        ImageView imageView;
        TextView textViewBaslik, textViewText, textViewDosya,textViewOncelik, textViewZaman;
        RelativeLayout arkaplan;
        Notlar secilen;

        public NotViewHolder(View itemView) {
            super(itemView);
            sil=itemView.findViewById(R.id.button_sil);
            sil.setBackgroundDrawable(d_sil);
            arkaplan=itemView.findViewById(R.id.arkaplan);
            imageView=itemView.findViewById(R.id.ımageView_foto);
            textViewBaslik=itemView.findViewById(R.id.textView_baslik);
            textViewText=itemView.findViewById(R.id.textView_not);
            textViewDosya=itemView.findViewById(R.id.textView_dosya);
            textViewOncelik=itemView.findViewById(R.id.textView_oncelik);
            textViewZaman=itemView.findViewById(R.id.textView_tarih_saat)  ;
            sil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.db.myDao().deleteNot(secilen);
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    Toast.makeText(v.getContext(), "Silindi", Toast.LENGTH_LONG).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notlar gonder=new Notlar();
                    gonder.setBaslik(secilen.getBaslik());
                    gonder.setHatirlatici(secilen.getHatirlatici());
                    gonder.setResim(secilen.getResim());
                    gonder.setRenk(secilen.getRenk());
                    gonder.setZaman(secilen.getZaman());

                    Intent not_ekle=new Intent(getBaseContext(), Guncelle_Sil.class);
                    Bundle bundle = new Bundle();
                    Toast.makeText(v.getContext(), String.valueOf(secilen.getText()), Toast.LENGTH_LONG).show();
                    bundle.putSerializable("guncelle_bilgileri", secilen);
                    not_ekle.putExtras(bundle);
                    startActivity(not_ekle);
                    //Toast.makeText(v.getContext(), String.valueOf(secilen.getText()), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

}


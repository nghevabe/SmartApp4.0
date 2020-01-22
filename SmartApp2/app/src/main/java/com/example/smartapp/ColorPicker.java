package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ColorPicker extends AppCompatActivity {

    Button btn;
    ImageView mImageView;
    TextView tvResult;
    View colorview;

    public int r;
    public int g;
    public int b;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker);

        mImageView = (ImageView) findViewById(R.id.imageView);
        tvResult = (TextView) findViewById(R.id.txtResult);
        colorview = (View) findViewById(R.id.colorView);
        btn = (Button) findViewById(R.id.btnChoose);

        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache(true);

        Bundle bundle = getIntent().getExtras();


        final String Id = bundle.getString("ID");
        final String Name_device = bundle.getString("NAME");


        r=0;
        g=0;
        b=0;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ColorPicker.this, DeviceDetail.class);

                Bundle bundle = new Bundle();

                bundle.putString("ID", Id);
                bundle.putString("NAME", Name_device);
                bundle.putString("R", Integer.toString(r));
                bundle.putString("G", Integer.toString(g));
                bundle.putString("B", Integer.toString(b));

                intent.putExtras(bundle);


                DeviceDetail.color_custom = 1;

                startActivity(intent);




            }
        });

        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    bitmap = mImageView.getDrawingCache();

                    int pixel = bitmap.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());

                     r = Color.red(pixel);
                     g = Color.green(pixel);
                     b = Color.blue(pixel);

                    tvResult.setText("Red: "+r + "   Green: "+g + "   Blue: "+b);

                    colorview.setBackgroundColor(Color.rgb(r,g,b));


                }

                return true;
            }
        });

    }
}

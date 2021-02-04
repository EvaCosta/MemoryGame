package com.example.jogodamemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class parabensActivity extends AppCompatActivity {
   private ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parabens);

        String valor = getIntent().getStringExtra("Color");
        layout = findViewById(R.id.fundo);

        layout.setBackgroundColor(Integer.parseInt(valor));
    }
}
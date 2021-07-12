package com.example.mission23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    BestPaintBoard board;
    Button colorButton;
    Button penButton;
    Button eraserButton;
    Button undoButton;

    RadioButton radio01;
    RadioButton radio02;
    RadioButton radio03;

    LinearLayout legendLayout;
    Button colorLegendButton;
    Button sizeLegendButton;

    int mColor = 0xff000000;
    int mSize = 2;
    int oldColor;
    int oldSize;
    boolean eraserSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout toolsLayout = findViewById(R.id.toolsLayout);
        LinearLayout boardLayout = findViewById(R.id.boardLayout);

        colorButton = findViewById(R.id.colorButton);
        penButton = findViewById(R.id.penButton);
        eraserButton = findViewById(R.id.eraserButton);
        undoButton = findViewById(R.id.undoButton);

        radio01 = findViewById(R.id.radio01);
        radio02 = findViewById(R.id.radio02);
        radio03 = findViewById(R.id.radio03);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        board = new BestPaintBoard(this);
        board.setLayoutParams(params);
        board.setPadding(2, 2, 2, 2);

        boardLayout.addView(board);

        legendLayout = findViewById(R.id.legendLayout);
        colorLegendButton = findViewById(R.id.colorLegendButton);
        sizeLegendButton = findViewById(R.id.sizeLegendButton);

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPaletteDialog.listener = new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        mColor = color;
                        board.updatePaintProperty(mColor, mSize);
                        displayPaintProperty();
                    }
                };

                Intent intent = new Intent(getApplicationContext(), ColorPaletteDialog.class);
                startActivity(intent);
            }
        });

        penButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PenPaletteDialog.listener = new OnPenSelectedListener() {
                    @Override
                    public void onPenSelected(int size) {
                        mSize = size;
                        board.updatePaintProperty(mColor, mSize);
                        displayPaintProperty();
                    }
                };

                Intent intent = new Intent(getApplicationContext(), PenPaletteDialog.class);
                startActivity(intent);
            }
        });

        eraserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eraserSelected = !eraserSelected;

                if (eraserSelected) {
                    colorButton.setEnabled(false);
                    penButton.setEnabled(false);
                    undoButton.setEnabled(false);

                    colorButton.invalidate();
                    penButton.invalidate();
                    undoButton.invalidate();

                    oldColor = mColor;
                    oldSize = mSize;

                    mColor = Color.WHITE;
                    mSize = 15;

                    board.updatePaintProperty(mColor, mSize);
                    displayPaintProperty();
                } else {
                    colorButton.setEnabled(true);
                    penButton.setEnabled(true);
                    undoButton.setEnabled(true);

                    colorButton.invalidate();
                    penButton.invalidate();
                    undoButton.invalidate();

                    mColor = oldColor;
                    mSize = oldSize;

                    board.updatePaintProperty(mColor, mSize);
                    displayPaintProperty();
                }
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.undo();
            }
        });

        radio01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "RadioButton 01 clicked.", Toast.LENGTH_SHORT).show();

                board.setCapStyle(Paint.Cap.BUTT);
            }
        });

        radio02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "RadioButton 02 clicked.", Toast.LENGTH_SHORT).show();

                board.setCapStyle(Paint.Cap.ROUND);
            }
        });

        radio03.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "RadioButton 03 clicked.", Toast.LENGTH_SHORT).show();

                board.setCapStyle(Paint.Cap.SQUARE);
            }
        });
    }

    public int getChosenColor() {
        return mColor;
    }

    public int getPenThickness() {
        return mSize;
    }

    private void displayPaintProperty() {
        colorLegendButton.setBackgroundColor(mColor);
        sizeLegendButton.setText("Size : " + mSize);

        legendLayout.invalidate();
    }
}
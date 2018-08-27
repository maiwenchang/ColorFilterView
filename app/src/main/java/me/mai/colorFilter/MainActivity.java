package me.mai.colorFilter;

import android.graphics.ColorMatrix;
import android.graphics.Matrix;
import android.os.Bundle;
import android.renderscript.Matrix4f;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private ColorFilterView mColorFilterView;
    private FlexboxLayout fblButtons;
    private FlexboxLayout fblTextViews;
    private SeekBar mRedSeekBar;
    private SeekBar mGreenSeekBar;
    private SeekBar mBlueSeekBar;
    private SeekBar mAlphaSeekBar;

    private SeekBar mMatrixSeekBar;

    private TextView tvMatrix_0;
    private TextView tvMatrix_1;
    private TextView tvMatrix_2;
    private TextView tvMatrix_3;
    private TextView tvMatrix_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mColorFilterView = findViewById(R.id.mColorFilterView);
        fblButtons = findViewById(R.id.fblButtons);
        fblTextViews = findViewById(R.id.fblTextViews);
        mRedSeekBar = findViewById(R.id.mRedSeekBar);
        mGreenSeekBar = findViewById(R.id.mGreenSeekBar);
        mBlueSeekBar = findViewById(R.id.mBlueSeekBar);
        mAlphaSeekBar = findViewById(R.id.mAlphaSeekBar);
        mMatrixSeekBar = findViewById(R.id.mMatrixSeekBar);
        tvMatrix_0 = findViewById(R.id.tvMatrix_0);
        tvMatrix_1 = findViewById(R.id.tvMatrix_1);
        tvMatrix_2 = findViewById(R.id.tvMatrix_2);
        tvMatrix_3 = findViewById(R.id.tvMatrix_3);
        tvMatrix_4 = findViewById(R.id.tvMatrix_4);

        mRedSeekBar.setOnSeekBarChangeListener(this);
        mGreenSeekBar.setOnSeekBarChangeListener(this);
        mBlueSeekBar.setOnSeekBarChangeListener(this);
        mAlphaSeekBar.setOnSeekBarChangeListener(this);

        for (int i = 0; i < fblTextViews.getChildCount(); i++) {
            fblTextViews.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectMatrixElement(v);
                }
            });
        }
        fblTextViews.getChildAt(0).setSelected(true);
        mMatrixSeekBar.setProgress(400 / 6);
        mMatrixSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.mMatrixSeekBar) {
            for (int i = 0; i < fblTextViews.getChildCount(); i++) {
                View child = fblTextViews.getChildAt(i);
                if (child.isSelected()) {
                    float max;
                    if (i % 5 == 4) {
                        max = 225.000f;
                    } else {
                        max = 3.000f;
                    }
                    float x = max * (progress - 50) / 50;
                    String format = "%.1f";
                    String matrixString = String.format(Locale.getDefault(), format, x);
                    matrixString = matrixString.replace("0.0", "0");
                    matrixString = matrixString.replace(".0", "");
                    ((TextView) child).setText(matrixString);
                    break;
                }
            }
            setCustomMatrix();
        } else {
            mColorFilterView.getColorMatrix()
                    .setScale(transform(mRedSeekBar.getProgress()),
                            transform(mGreenSeekBar.getProgress()),
                            transform(mBlueSeekBar.getProgress()),
                            transform(mAlphaSeekBar.getProgress()));
            mColorFilterView.postInvalidate();
            setMatrixText();
        }
    }

    private float transform(float progress) {
        return 1 + (progress - 50) / 50;
    }

    public void reset(View view) {
        mRedSeekBar.setProgress(50);
        mGreenSeekBar.setProgress(50);
        mBlueSeekBar.setProgress(50);
        mAlphaSeekBar.setProgress(50);

        for (int i = 0; i < fblTextViews.getChildCount(); i++) {
            View child = fblTextViews.getChildAt(i);
            child.setSelected(false);
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                ((TextView) child).setText("1");
            } else {
                ((TextView) child).setText("0");
            }
        }
        fblTextViews.getChildAt(0).setSelected(true);
        mMatrixSeekBar.setProgress(400 / 6);

        mColorFilterView.getColorMatrix().reset();
        mColorFilterView.postInvalidate();
        setMatrixText();
    }

    private void setMatrixText() {
        ColorMatrix colorMatrix = mColorFilterView.getColorMatrix();
        float[] matrixArray = colorMatrix.getArray();
        String format = "%.1f\n%.1f\n%.1f\n%.1f\n";

        String[] matrixStrings = new String[5];
        for (int i = 0; i < 5; i++) {
            String matrixString = String.format(Locale.getDefault(), format,
                    matrixArray[i], matrixArray[i + 5], matrixArray[i + 10], matrixArray[i + 15]);
            matrixString = matrixString.replace("0.0", "0");
            matrixString = matrixString.replace(".0", "");
            matrixStrings[i] = matrixString;
        }

        tvMatrix_0.setText(matrixStrings[0]);
        tvMatrix_1.setText(matrixStrings[1]);
        tvMatrix_2.setText(matrixStrings[2]);
        tvMatrix_3.setText(matrixStrings[3]);
        tvMatrix_4.setText(matrixStrings[4]);
    }

    public void setCustomMatrix() {
        float[] matrix = new float[20];
        for (int i = 0; i < fblTextViews.getChildCount(); i++) {
            String matrixString = ((TextView) fblTextViews.getChildAt(i)).getText().toString();
            matrix[i] = Float.valueOf(matrixString);
        }
        mColorFilterView.setColorMatrixArray(matrix);
        setMatrixText();
    }

    public void setTemplate(View view) {
        int i = fblButtons.indexOfChild(view);
        mColorFilterView.setColorMatrixArray(ColorMatrixList.getInstance().getMatrixList().get(i));
        setMatrixText();
    }

    public void selectMatrixElement(View view) {
        for (int i = 0; i < fblTextViews.getChildCount(); i++) {
            fblTextViews.getChildAt(i).setSelected(false);
        }
        view.setSelected(true);
        int i = fblTextViews.indexOfChild(view);
        String matrixString = ((TextView) view).getText().toString();
        Float x = Float.valueOf(matrixString);
        float max;
        if (i % 5 == 4) {
            max = 225.000f;
        } else {
            max = 3.000f;
        }
        float y = (x * 50 / max) + 50;
        mMatrixSeekBar.setProgress((int) y);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

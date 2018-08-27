package me.mai.colorFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mai on 2018/8/27
 * *
 * Description:
 * *
 */
public class ColorMatrixList {

    private List<float[]> mMatrixList = new ArrayList<>();

    private ColorMatrixList() {
        this.mMatrixList.add(ColorMatrixTemplate.Film);
        this.mMatrixList.add(ColorMatrixTemplate.BlackAndWhite);
        this.mMatrixList.add(ColorMatrixTemplate.Retro);
    }

    public static ColorMatrixList getInstance() {
        return new ColorMatrixList();
    }

    public List<float[]> getMatrixList() {
        return mMatrixList;
    }
}

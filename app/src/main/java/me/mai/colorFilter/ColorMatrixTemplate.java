package me.mai.colorFilter;

/**
 * Created by Mai on 2018/8/24
 * *
 * Description:
 * *
 */
public class ColorMatrixTemplate {

    //胶片（曝光）
    public static float[] Film = {
            -1, 0, 0, 0, 255,
            0, -1, 0, 0, 255,
            0, 0, -1, 0, 255,
            0, 0, 0, 1, 0
    };

    //黑白
    public static float[] BlackAndWhite = {
            0.213f, 0.715f, 0.072f, 0, 0,
            0.213f, 0.715f, 0.072f, 0, 0,
            0.213f, 0.715f, 0.072f, 0, 0,
            0, 0, 0, 1f, 0
    };

    //复古（老照片）
    public static float[] Retro = {
            1 / 2f, 1 / 2f, 1 / 2f, 0, 0,
            1 / 3f, 1 / 3f, 1 / 3f, 0, 0,
            1 / 4f, 1 / 4f, 1 / 4f, 0, 0,
            0, 0, 0, 1f, 0
    };

}

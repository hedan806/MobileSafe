package com.hedan.mobilesafe;

import android.test.AndroidTestCase;

import com.hedan.mobilesafe.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/18.
 */
public class TestHedan extends AndroidTestCase {

    private static final String TAG = TestHedan.class.getSimpleName();

    public void testMath(){
        Double x_15 = 0.15D;
        Double x_7 = 0.7D;
        Double x_10 = 0.1D;
        Double x_2 = 0.02D;
        Double x_07 = 0.007D;
        Double x_05 = 0.015D;
        Double x_2_5 = 0.025D;

        Double y1 = 70274D;
        Double y2 = 6000D;
        Double y3 = 76274D;
        Double y4 = 50801D;
        Double y5 = 3718D;
        Double y6 = 54519D;
        Double y7 = 49206D;
        /*Double y1 = 80274D;
        Double y2 = 6413D;
        Double y3 = 86687D;
        Double y4 = 51801D;
        Double y5 = 4237D;
        Double y6 = 56038D;
        Double y7 = 51206D;*/

        List<Double> xs = new LinkedList<Double>();
        xs.add(x_15);
        xs.add(x_7);
        xs.add(x_10);
        xs.add(x_2);
        xs.add(x_07);
        xs.add(x_05);

        double r =0D;
        /*for(Double d : xs){
            double r1 = y1 * d;
            LogUtil.i(TAG,r1 + "");
            r = r+r1;
        }*/
//        LogUtil.i(TAG,r + "");

        List<Double> list = new LinkedList<Double>();
        list.add(y1);
        list.add(y2);
        list.add(y3);
        list.add(y4);
        list.add(y5);
        list.add(y6);
        list.add(y7);
        for (Double d : list){
            double d3 = 0d;
            for(int i =0 ; i < xs.size();i++){
                double d1 = d * x_15;
                d3 += xs.get(i) * d;
                if(i == xs.size()-1){
                    LogUtil.i(TAG,"养老金：" + d1 + ">>合计：" + d3);
                }
            }

//            d3 = d1 + d2;
        }

    }

}

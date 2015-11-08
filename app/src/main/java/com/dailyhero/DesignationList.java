package com.dailyhero;

import java.util.ArrayList;

/**
 * Created by song on 2015/11/8.
 */
public class DesignationList {
    int point;
    ArrayList designation;
    //傳入point
    public DesignationList(int point) {
        this.point = point;
        Designation();
    }

    public String getDesigntion() {
        String sign="";
        if(point > 500)
            sign = designation.get(0).toString();
        if(point > 1000)
            sign = designation.get(1).toString();
        if(point > 1800)
            sign = designation.get(2).toString();
        if(point > 2500)
            sign = designation.get(3).toString();
        if(point > 3200)
            sign = designation.get(4).toString();
        if(point > 4000)
            sign = designation.get(5).toString();
        if(point > 5000)
            sign = designation.get(6).toString();
        if(point > 6000)
            sign = designation.get(7).toString();
        if(point > 7200)
            sign = designation.get(8).toString();
        if(point > 8500)
            sign = designation.get(9).toString();
        if(point > 10000)
            sign = designation.get(10).toString();
        if(point > 11500)
            sign = designation.get(11).toString();
        if(point > 13000)
            sign = designation.get(12).toString();
        if(point > 15000)
            sign = designation.get(13).toString();
        if(point > 18000)
            sign = designation.get(14).toString();
        if(point > 20000)
            sign = designation.get(15).toString();


        return sign;
    }

    // 測試用
    public String getDesigntion(int point) {
        String sign="";
        if(point > 500)
            sign = designation.get(0).toString();
        if(point > 1000)
            sign = designation.get(1).toString();
        if(point > 1800)
            sign = designation.get(2).toString();
        if(point > 2500)
            sign = designation.get(3).toString();
        if(point > 3200)
            sign = designation.get(4).toString();
        if(point > 4000)
            sign = designation.get(5).toString();
        if(point > 5000)
            sign = designation.get(6).toString();
        if(point > 6000)
            sign = designation.get(7).toString();
        if(point > 7200)
            sign = designation.get(8).toString();
        if(point > 8500)
            sign = designation.get(9).toString();
        if(point > 10000)
            sign = designation.get(10).toString();
        if(point > 11500)
            sign = designation.get(11).toString();
        if(point > 13000)
            sign = designation.get(12).toString();
        if(point > 15000)
            sign = designation.get(13).toString();
        if(point > 18000)
            sign = designation.get(14).toString();
        if(point > 20000)
            sign = designation.get(15).toString();
        if(point > 25000)
            sign = designation.get(16).toString();
        if(point > 35000)
            sign = designation.get(17).toString();



        return sign;
    }

    private void Designation() {
        designation = new ArrayList();
        designation.add("粗心者");
        designation.add("剛離開新手村的粗心者");
        designation.add("有點心得的粗心者");
        designation.add("遠離粗心者名稱的冒險者");
        designation.add("剛離開舒適圈的冒險者");
        designation.add("有點錢的冒險者");
        designation.add("很有錢的冒險者");
        designation.add("你根本是商人吧的冒險者");
        designation.add("剛拯救了一個村子的冒險者");
        designation.add("有名氣的冒險者");
        designation.add("打敗魔王歸來的冒險者");
        designation.add("這個人是勇者");
        designation.add("真正的勇者");
        designation.add("被眾人所知的勇者");
        designation.add("傳說中的勇者");
        designation.add("傳說");
        designation.add("神");
        designation.add("未命名");
}

}


package com.zzu.ehome.ehomefordoctor.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.util.Log;
import android.widget.TextView;



import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2016/4/9.
 */
public class CommonUtils {


    private static long lastClickTime;
    public static final String TINY = "瘦如闪电";
    public static final String NORMAL = "完美身材";
    public static final String OVERLOAD = "超重了亲";
    public static final String SAMLL = "轻微发福";
    public static final String MIDDLE = "小心脂肪";
    public static final String BIG = "不忍直视";
    public  enum Type{
        LOGIN, YELLOW, RED;
    }

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static <T> void intentAction(Activity context, Class<T> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        context.finish();

    }
  


    public static int computeSsz(int ssz) {
        if (ssz < 140) {
            return 1;
        } else if (ssz >= 140 && ssz < 160) {
            return 2;
        } else if (ssz >= 160 && ssz < 180) {
            return 3;
        } else {
            return 4;
        }

    }

    public static int computeSzy(int szy) {
        if (szy < 90) {
            return 1;
        } else if (szy >= 90 && szy < 100) {
            return 2;

        } else if (szy >= 100 && szy < 110) {
            return 3;

        } else {
            return 4;

        }

    }

    public static int MaxInt(int lvssz, int lvszy) {
        if (lvssz != lvszy) {
            if (lvssz > lvszy) {
                return lvssz;
            } else {
                return lvszy;
            }
        } else {
            return lvssz;
        }
    }



    public static long returnLongTime(String time) {
        long result = 0;
        String month = time.split(" ")[0].replace("/", "-");
        String[] times = month.split("\\-");
        if (times.length > 1) {
            int i = Integer.valueOf(times[1]);

            if (i < 10) {
                result = returnLong(times[0] + "-" + "0" + times[1] + "-" + times[2]);
            } else {
                result = returnLong(times[0] + "-" + times[1] + "-" + times[2]);
            }
        }

        return result;
    }

    public static long returnLong(String time) {

        Date dtime = stringToDate(time, "yyyy-MM-dd");
        return dtime.getTime() / 1000;

    }

    public static long returnLongNew(String time) {

        Date dtime = stringToDate(time, "yyyy-MM");
        return dtime.getTime() / 1000;

    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


    public static Date changeDate(int days) {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, days);

        return calendar.getTime();
    }

    public static Map sortMap(Map oldMap) {
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> arg0,
                               Map.Entry<String, Integer> arg1) {
                return arg1.getValue() - arg0.getValue();
            }
        });
        Map newMap = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }

    public static double position(String s, List<String> ss) {
        double p = -1d;
        for (int i = 0; i < ss.size(); i++) {
            if (s.equals(ss.get(i))) {
                p = Double.parseDouble(i + "");
                break;
            }
        }
        return p;
    }

    public static List<Date> dateSplit(Date startDate, Date endDate)
            throws Exception {
        if (!startDate.before(endDate))
            throw new Exception("开始时间应该在结束时间之后");
        Long spi = endDate.getTime() - startDate.getTime();
        Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

        List<Date> dateList = new ArrayList<Date>();
        dateList.add(endDate);
        for (int i = 1; i <= step; i++) {
            dateList.add(new Date(dateList.get(i - 1).getTime()
                    - (24 * 60 * 60 * 1000)));// 比上一天减一
        }
        Collections.reverse(dateList);
        return dateList;
    }

    /**
     * 输入日期格式：2013.04.29
     */
    public static List getDays(String a, String b) {
        List<String> days = new ArrayList<String>();// 日期集合
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long end = sdf.parse(b).getTime();
            long begin = sdf.parse(a).getTime();
            while (begin <= end) {
                Date day = new Date();
                day.setTime(begin);
                begin += 3600 * 24 * 1000;
                days.add(sdf.format(day));
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return days;
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    public static String showBMIDetail(float bmi) {
        if (Float.compare(bmi, 18.5f) < 0) {
            return TINY;
        } else if (Float.compare(bmi, 18.5f) >= 0 && Float.compare(bmi, 24.0f) < 0) {
            return NORMAL;
        } else if (Float.compare(bmi, 24.0f) >= 0 && Float.compare(bmi, 27.0f) < 0) {
            return OVERLOAD;
        } else if (Float.compare(bmi, 27f) >= 0 && Float.compare(bmi, 30f) < 0) {
            return SAMLL;
        } else if (Float.compare(bmi, 30f) >= 0 && Float.compare(bmi, 35f) < 0) {
            return MIDDLE;
        } else {
            return BIG;
        }

    }

    /**
     * bmi指数
     *
     * @param s
     * @param tv
     */
    public static void bmiStatus(String s, TextView tv) {
        if (s.equals(TINY)) {
            tv.setTextColor(Color.parseColor("#fffff"));
        } else if (s.equals(NORMAL)) {
            tv.setTextColor(Color.parseColor("#53bbb3"));
        } else if (s.equals(OVERLOAD)) {
            tv.setTextColor(Color.parseColor("#53bbb3"));
        }

    }

    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

   public static LinkedList<String> getDayLables(){
       LinkedList<String> labels = new LinkedList<String>();
       labels.add("00");
       labels.add("04");
       labels.add("08");
       labels.add("12");
       labels.add("16");
       labels.add("20");
       labels.add("24");
       return labels;
   }
    public static LinkedList<String> getWeekLables(){
        LinkedList<String> labels = new LinkedList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(CommonUtils.changeDate(-6).getTime());
        labels.add(startTime.split("-")[2]);
        labels.add(sdf.format(changeDate(-5).getTime()).split("-")[2]);
        labels.add(sdf.format(changeDate(-4).getTime()).split("-")[2]);
        labels.add(sdf.format(changeDate(-3).getTime()).split("-")[2]);
        labels.add(sdf.format(changeDate(-2).getTime()).split("-")[2]);
        labels.add(sdf.format(changeDate(-1).getTime()).split("-")[2]);
        labels.add(sdf.format((new Date()).getTime()).split("-")[2]);

        return labels;
    }
    public static LinkedList<String> getMonthLables(){
        LinkedList<String> labels = new LinkedList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(CommonUtils.changeDate(-29).getTime());

        List<String> months = new ArrayList<String>();
        months =getDays(startTime, sdf.format(CommonUtils.changeDate(-1).getTime() + 60 * 60 * 24 * 1000));
        int i = 0;
        while (i < 30) {
            if (i == 0) {
                labels.add(months.get(i).split("-")[2]);
                i += 4;
            } else {
                labels.add(months.get(i).split("-")[2]);
                i += 5;
            }

        }
        return labels;
    }




}

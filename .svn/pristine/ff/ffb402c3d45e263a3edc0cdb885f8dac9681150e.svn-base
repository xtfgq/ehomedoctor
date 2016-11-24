package com.zzu.ehome.ehomefordoctor.utils;

import android.provider.ContactsContract;

import com.zzu.ehome.ehomefordoctor.app.Constans;

import java.util.Map;

/**
 * Created by Mersens on 2016/9/26.
 */

public class ECGNode {
    public static String toStart(String name){
        return "&lt;"+name+"&gt;";
    }

    public static String toEnd(String name){
        return "&lt;/"+name+"&gt;";
    }

    public static String getResult(String namespace,Map<String,String> map){
        StringBuffer sbf=new StringBuffer();
        sbf.append(ECGNode.toStart("Request"));
        for(Map.Entry<String,String> entry:map.entrySet()){
            sbf.append(ECGNode.toStart(entry.getKey()));
            sbf.append(entry.getValue());
            sbf.append(ECGNode.toEnd(entry.getKey()));
        }
        sbf.append(ECGNode.toEnd("Request"));
        String str="<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Header>\n" +
                "    <Identify xmlns=\""+ Constans.JE_BASE_URL3+"/TopmdWeixin/\">\n" +
                "      <UserName>weixin</UserName>\n" +
                "      <PassWord>myw#weixin1001</PassWord>\n" +
                "    </Identify>\n" +
                "  </soap:Header>\n" +
                "  <soap:Body>\n" +
                "    <"+namespace+" xmlns=\""+Constans.JE_BASE_URL3+"/TopmdWeixin/\">\n" +
                "      <str>"+sbf.toString()+"</str>\n" +
                "    </"+namespace+">\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
        return str;
    }
}

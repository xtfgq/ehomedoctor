package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Administrator on 2016/10/27.
 */

public interface IPressHistoryView extends  IPressDataView{
    String getPageSize();
    String getPageIndex();
    String getTypeHistory();
    <T>void onResultSuccess(T t);
}

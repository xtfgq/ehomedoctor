package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Administrator on 2016/10/27.
 */

public interface ISuggarHistoryView extends ISuggarDateView{

    String getPageSize();
    String getPageIndex();
    String getTypeHistory();
    <T>void onResultSuccess(T t);

}

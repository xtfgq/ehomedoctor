package com.zzu.ehome.ehomefordoctor.mvp.view;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IUAData;

/**
 * Created by Administrator on 2016/10/27.
 */

public interface IUAHistoryView extends IUaDataView{
    String getPageSize();
    String getPageIndex();
    String getTypeHistory();
    <T>void onResultSuccess(T t);
}

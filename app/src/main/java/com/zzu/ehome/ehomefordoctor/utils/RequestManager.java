package com.zzu.ehome.ehomefordoctor.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.app.Constans;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.zzu.ehome.ehomefordoctor.utils.NetUtils.isNetworkConnected;

/**
 * Created by Mersens on 2016/9/12.
 */
public class RequestManager {
    public final static int CONNECT_TIMEOUT = 10;
    public final static int READ_TIMEOUT = 20;
    public final static int WRITE_TIMEOUT = 10;
    public Retrofit mRetrofit;
    private static RequestManager manager;//管理者实例
    public OkHttpClient mClient;//OkHttpClient实例

    private RequestManager() {
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        mClient = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constans.WEBSERVICE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                .client(mClient)
                .build();
    }

    //单例模式，对提供管理者实例
    public static RequestManager getInstance() {
        if (manager == null) {
            synchronized (RequestManager.class) {
                if (manager == null) {
                    manager = new RequestManager();
                }
            }
        }
        return manager;
    }
    public void execute(Call<ResponseBody> call, final RequestCallBack callBack) {
        callBack.onStart();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    if (response.body() == null) {
                        callBack.onError("请求发生未知错误");
                        return;
                    }
                    String res = response.body().string();
                    if (res.contains("{") && res.contains("}")) {
                        int startIndex = res.indexOf("{");
                        int endIndex = res.lastIndexOf("}") + 1;
                        String subStr = res.substring(startIndex, endIndex);
                        doRequest(subStr, callBack);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onError(t.toString());
            }
        });
    }


    private void doRequest(final String string, final RequestCallBack callBack) {
        Observable.from(new String[]{string}).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                         callBack.onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        callBack.onSueecss((String)o);
                    }
                });
    }

    public Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isNetworkConnected(App.getInstance())) {
                    Log.e("TAG", "Interceptor没有网络连接");
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }
    public <T> T create(Class<T> cls) {
        return mRetrofit.create(cls);
    }

    public interface RequestCallBack {
        void onSueecss(String msg);
        void onError(String msg);
        void onStart();
        void onFinish();
    }
}

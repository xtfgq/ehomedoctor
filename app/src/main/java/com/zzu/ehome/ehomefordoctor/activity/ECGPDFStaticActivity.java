package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.entity.User;
import com.zzu.ehome.ehomefordoctor.utils.DownloadProgressListener;
import com.zzu.ehome.ehomefordoctor.utils.FileDownloader;
import com.zzu.ehome.ehomefordoctor.utils.ToastUtils;
import com.zzu.ehome.ehomefordoctor.view.HeadView;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



/**
 * Created by dell on 2016/6/17.
 */
public class ECGPDFStaticActivity extends BaseActivity {

    private Intent mIntent;
    private TextView tvresult, tv_statu, tvtime, tvname;
    private String imurl, Diagnosis, PatientName, CollectTime,filename;
    long filesize;
    int result;
    private CircleProgressBar circleProgressBar;
    private TextView tvcontent;
    private Boolean isExists = false;
    private RelativeLayout rl_check;
    private User user;

    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
    public final String DOWM_FOLDER = SDCARD_PATH + File.separator + "ehome2" + File.separator + "download" + File.separator;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    float num = (float) msg.getData().getInt("size") / (float) filesize;
                    result = (int) (num * 100);
                    circleProgressBar.setProgress(result);
                    circleProgressBar.setVisibility(View.VISIBLE);
                    //显示下载成功信息
                    if (circleProgressBar.getProgress() == circleProgressBar.getMax()) {
                        ToastUtils.showMessage(ECGPDFStaticActivity.this, "下载成功" + "请到" + DOWM_FOLDER + "查看");
                        openFolder(DOWM_FOLDER + filename);
                        circleProgressBar.setVisibility(View.GONE);
                        tvcontent.setText("打开文件");
                        isExists = true;
                    }
                    break;
                case -1:
                    //显示下载错误信息
                    File file = new File(DOWM_FOLDER + filename);
                    if(file.exists()){file.delete();}
                    ToastUtils.showMessage(ECGPDFStaticActivity.this, "下载失败!");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.layout_dynamic_detail);

        mIntent = this.getIntent();


        imurl = mIntent.getStringExtra("imurl");
        Diagnosis = mIntent.getStringExtra("Diagnosis");
        PatientName = mIntent.getStringExtra("PatientName");
        CollectTime = mIntent.getStringExtra("CollectTime");

        filename=imurl.substring(imurl.lastIndexOf("/")+1,imurl.length());



					try {
                        imurl = new String(imurl.getBytes("UTF-8"));
                        imurl= URLEncoder.encode(imurl,"utf-8").replaceAll("\\+", "%20");
            imurl = imurl.replaceAll("%3A", ":").replaceAll("%2F", "/");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


        initViews();
        tv_statu.setVisibility(View.GONE);

        tvtime.setText(CollectTime);
        tvname.setText(PatientName);

        circleProgressBar.setVisibility(View.GONE);
        File file = new File(DOWM_FOLDER + filename);
        if (file.exists()) {
//            if (filename.equals(CommonUtils.getFileMD5(file))) {
                tvcontent.setText("打开详细报告");
                isExists = true;
//            } else {
//                tvcontent.setText("下载详细报告内容");
//            }
        } else {
            tvcontent.setText("下载详细报告内容");
        }
        initEvent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermission();
        }
        initDatas();
    }

    @Override
    public void init() {

    }

    public void initViews() {

        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "静态心电报告", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        tvresult = (TextView) findViewById(R.id.tvresult);
        tvresult.setText(Diagnosis);
        rl_check = (RelativeLayout) findViewById(R.id.rl_check);
        circleProgressBar = (CircleProgressBar) findViewById(R.id.line_progress);
        tvcontent = (TextView) findViewById(R.id.tvcontent);
        tv_statu = (TextView) findViewById(R.id.tv_statu);
        tvname = (TextView) findViewById(R.id.tvname);
        tvtime = (TextView) findViewById(R.id.tvtime);


        rl_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExists) {
                    openFolder(DOWM_FOLDER + filename);
                } else {
                    if (result > 0) {
                        return;
                    }
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        //开始下载文件
                        download(imurl, new File(DOWM_FOLDER),filename);
                    } else {
                        //显示SDCard错误信息
                        ToastUtils.showMessage(ECGPDFStaticActivity.this, "读取失败!");
                    }
                }
            }
        });
    }

    public void initEvent() {
    }

    public void initDatas() {

    }

    /**
     * 主线程(UI线程)
     * 对于显示控件的界面更新只是由UI线程负责，如果是在非UI线程更新控件的属性值，更新后的显示界面不会反映到屏幕上
     * 如果想让更新后的显示界面反映到屏幕上，需要用Handler设置。
     *
     * @param path
     * @param savedir
     */
    private void download(final String path, final File savedir,final String filename) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileDownloader loader = new FileDownloader(ECGPDFStaticActivity.this, path, savedir, filename,4);
                    filesize = loader.getFileSize();
                    circleProgressBar.setMax(100);//设置进度条的最大刻度为文件的长度
                    loader.download(new DownloadProgressListener() {
                        @Override
                        public void onDownloadSize(int size) {//实时获知文件已经下载的数据长度
                            Message msg = new Message();
                            msg.what = 1;
                            msg.getData().putInt("size", size);
                            handler.sendMessage(msg);//发送消息
                        }
                    });
                } catch (Exception e) {
                    handler.obtainMessage(-1).sendToTarget();
                }
            }
        }).start();
    }

    public void openFolder(String filename) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new
                File(filename));
        intent.setDataAndType(uri, "application/pdf");
        this.startActivity(intent);
    }
}

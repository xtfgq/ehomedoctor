package com.zzu.ehome.ehomefordoctor.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.utils.DownloadProgressListener;
import com.zzu.ehome.ehomefordoctor.utils.FileDownloader;
import com.zzu.ehome.ehomefordoctor.utils.PermissionsChecker;
import com.zzu.ehome.ehomefordoctor.utils.ToastUtils;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/10/26.
 */

public class ECGDetailsActivity extends BaseActivity {
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tvtime)
    TextView tvtime;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.tv_statu)
    TextView tvStatu;
    @BindView(R.id.tvresult)
    TextView tvresult;
    @BindView(R.id.iv_pdf)
    ImageView ivPdf;
    @BindView(R.id.tvcontent)
    TextView tvcontent;
    @BindView(R.id.line_progress)
    CircleProgressBar lineProgress;
    @BindView(R.id.rl_check)
    RelativeLayout rlCheck;
    private Intent mIntent;
    private Boolean isExists = false;
    private String filename, filemd5,username;
    private String url = "", status;
    long filesize;
    int result;

    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
    public final String DOWM_FOLDER = SDCARD_PATH + File.separator + "ehome2" + File.separator + "download" + File.separator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dynamic_detail);
        ButterKnife.bind(this);
        init();

    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "动态心电报告", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
               finish();
            }
        });
        mIntent = this.getIntent();
        filemd5 = mIntent.getStringExtra("FileMD5");
        status = mIntent.getStringExtra("ReportType");
        username=mIntent.getStringExtra("username");
        filename = mIntent.getStringExtra("Fid").trim()+".pdf";
        url = ( mIntent.getStringExtra("Download")).replace("\\", "/");
        try {
            url = new String(url.getBytes("UTF-8"));
            url= URLEncoder.encode(url,"utf-8").replaceAll("\\+", "%20");
            url = url.replaceAll("%3A", ":").replaceAll("%2F", "/");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        tvStatu.setText(status);
        tvtime.setText(mIntent.getStringExtra("time"));
        tvname.setText(username);
        if (status.contains("低")) {
            tvStatu.setTextColor(Color.parseColor("#00c07d"));
        } else if (status.contains("中")) {
            tvStatu.setTextColor(Color.parseColor("#fb9c2e"));
        } else if (status.contains("高")) {
            tvStatu.setTextColor(Color.parseColor("#f95935"));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermission();
        }
        lineProgress.setVisibility(View.GONE);
        File file = new File(DOWM_FOLDER + filename);
        if (file.exists()) {
            tvcontent.setText("打开详细报告");
            isExists = true;
        } else {
            tvcontent.setText("下载详细报告内容");
        }
        rlCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExists) {
                    openFolder(DOWM_FOLDER + filename);
                } else {
                    if (result > 0) {
                        return;
                    }
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        download(url, new File(DOWM_FOLDER),filename);
                    } else {
                        ToastUtils.showMessage(ECGDetailsActivity.this, "读取失败!");
                    }
                }
            }
        });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    float num = (float) msg.getData().getInt("size") / (float) filesize;
                    result = (int) (num * 100);
                    lineProgress.setProgress(result);
                    lineProgress.setVisibility(View.VISIBLE);
                    //显示下载成功信息
                    if (lineProgress.getProgress() == lineProgress.getMax()) {
                        ToastUtils.showMessage(ECGDetailsActivity.this, "下载成功" + "请到" + DOWM_FOLDER + "查看");
                        openFolder(DOWM_FOLDER + filename);
                        lineProgress.setVisibility(View.GONE);
                        tvcontent.setText("打开文件");
                        isExists = true;
                    }
                    break;
                case -1:
                    //显示下载错误信息
                    File file = new File(DOWM_FOLDER + filename);
                    if(file.exists()){file.delete();}
                    ToastUtils.showMessage(ECGDetailsActivity.this, "下载失败!");
                    break;
            }
        }
    };

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
                    FileDownloader loader = new FileDownloader(ECGDetailsActivity.this, path, savedir, filename,4);
                    filesize = loader.getFileSize();
                    lineProgress.setMax(100);//设置进度条的最大刻度为文件的长度
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

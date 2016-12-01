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
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.download.DownloadInfo;
import com.lzy.okserver.download.DownloadManager;
import com.lzy.okserver.download.DownloadService;
import com.lzy.okserver.listener.DownloadListener;
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

import static com.zzu.ehome.ehomefordoctor.R.id.rl_check;
import static com.zzu.ehome.ehomefordoctor.R.id.tv_statu;
import static io.rong.imlib.statistics.UserData.username;

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
    @BindView(tv_statu)
    TextView tvStatu;
    @BindView(R.id.tvresult)
    TextView tvresult;
    @BindView(R.id.iv_pdf)
    ImageView ivPdf;
    @BindView(R.id.tvcontent)
    TextView tvcontent;
    @BindView(R.id.line_progress)
    CircleProgressBar lineProgress;
    @BindView(rl_check)
    RelativeLayout rlCheck;
    private Intent mIntent;

    private String filename, username;
    private String url = "", status;
    long filesize;
    int result;
    private DownloadManager downloadManager;
    private DownloadInfo downloadInfo;

    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
    public final String DOWM_FOLDER = SDCARD_PATH + File.separator + "ehomedoc" + File.separator + "download" + File.separator;
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
        downloadManager = DownloadService.getDownloadManager();
        downloadManager.setTargetFolder(DOWM_FOLDER);
        downloadInfo = downloadManager.getDownloadInfo(url);

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
        lineProgress.setVisibility(View.GONE);
        if (downloadInfo != null) {
            if(downloadInfo.getState() == DownloadManager.FINISH){
                lineProgress.setVisibility(View.GONE);
                tvcontent.setText("打开文件");
            }else if(downloadInfo.getState() == DownloadManager.PAUSE){
                lineProgress.setVisibility(View.VISIBLE);
                result=(int)(Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100);
                lineProgress.setProgress(result);
                tvcontent.setText("继续下载细报告内容");
            }else if(downloadInfo.getState()==DownloadManager.ERROR){
                lineProgress.setVisibility(View.GONE);
                tvcontent.setText("重新下载细报告内容");
            }
        }else{
            tvcontent.setText("下载详细报告内容");
        }
        initEvent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermission();
        }
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
    public void initEvent() {
        rlCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadInfo != null) {
                    if (downloadInfo.getState() == DownloadManager.FINISH) {
                        tvcontent.setText("打开文件");
                        lineProgress.setVisibility(View.GONE);
                        openFolder(DOWM_FOLDER + filename);
                    } else if (downloadInfo.getState() == DownloadManager.PAUSE) {
                        downloadManager.startAllTask();
                        lineProgress.setVisibility(View.VISIBLE);
                        tvcontent.setText("正在下载详细报告内容...");
                    } else if (downloadInfo.getState() == DownloadManager.DOWNLOADING) {
                        lineProgress.setVisibility(View.VISIBLE);
                        downloadManager.pauseTask(url);
                        tvcontent.setText("继续下载详细报告内容");
                    } else if (downloadInfo.getState() == DownloadManager.ERROR) {
                        lineProgress.setVisibility(View.VISIBLE);
                        downloadManager.restartTask(url);
                        tvcontent.setText("正在下载详细报告内容...");
                    }
                } else {
                    GetRequest request = OkGo.get(url);
                    downloadManager.addTask(filename,url, request, null);
                    downloadInfo = downloadManager.getDownloadInfo(url);
                    lineProgress.setVisibility(View.VISIBLE);
                    downloadManager.startAllTask();
                    DownloadListener downloadListener = new MyDownloadListener();
                    downloadInfo.setListener(downloadListener);
                    tvcontent.setText("正在下载详细报告内容...");
                }

            }
        });
        if(downloadInfo!=null) {
            DownloadListener downloadListener = new MyDownloadListener();
            downloadInfo.setListener(downloadListener);
        }
    }
    private class MyDownloadListener extends DownloadListener {

        @Override
        public void onProgress(DownloadInfo downloadInfo) {
            Long length=downloadInfo.getDownloadLength();
            Long size=downloadInfo.getTotalLength();
            result=(int)(Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100);
            lineProgress.setProgress(result);
        }

        @Override
        public void onFinish(DownloadInfo downloadInfo) {
            Toast.makeText(ECGDetailsActivity.this, "下载完成:" + downloadInfo.getTargetPath(), Toast.LENGTH_SHORT).show();
            tvcontent.setText("打开文件");
        }

        @Override
        public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
            if (errorMsg != null) {
                Toast.makeText(ECGDetailsActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                tvcontent.setText("重新下载详细报告内容");
            }
        }
    }


}

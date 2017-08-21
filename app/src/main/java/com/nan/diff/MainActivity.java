package com.nan.diff;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nan.diff.utils.ApkUtils;
import com.nan.diff.utils.BsPatch;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.nan.diff.utils.Constants.NEW_APK_PATH;
import static com.nan.diff.utils.Constants.PACKAGE_NAME;
import static com.nan.diff.utils.Constants.PATCH_FILE_PATH;

public class MainActivity extends AppCompatActivity {

    public static final int CODE_REQUEST_PERMISSIONS = 100;
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mCtx = this;

        //申请文件读写、创建删除文件权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
            }, CODE_REQUEST_PERMISSIONS);
        }

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "这是版本1", Toast.LENGTH_SHORT).show();
                update();
            }
        });
    }

    private void update() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //下载差分包
                //DownloadUtils.download(URL_PATCH_DOWNLOAD);
                String oldFile = ApkUtils.getSourceApkPath(mCtx, PACKAGE_NAME);
                //合并差分包
                BsPatch.patch(oldFile, NEW_APK_PATH, PATCH_FILE_PATH);
                e.onNext(NEW_APK_PATH);
                Toast.makeText(MainActivity.this, "差分包合成完毕", Toast.LENGTH_SHORT).show();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String newApkPath) throws Exception {
                Toast.makeText(MainActivity.this, "开始安装", Toast.LENGTH_SHORT).show();
                ApkUtils.installApk(mCtx, newApkPath);
            }
        });
    }
}

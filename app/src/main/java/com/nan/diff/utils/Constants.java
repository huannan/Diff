package com.nan.diff.utils;

import java.io.File;

import android.os.Environment;

public class Constants {

    public static final String SD_CARD = Environment.getExternalStorageDirectory() + File.separator;

    //新版本apk的目录
    public static final String NEW_APK_PATH = SD_CARD + "dn_apk_new.apk";
    //差分包文件路径
    public static final String PATCH_FILE = "apk.patch";
    public static final String PATCH_FILE_PATH = SD_CARD + PATCH_FILE;

    //linux remote
//    public static final String URL_PATCH_DOWNLOAD = "http://172.19.167.1:8080/dn_app_update_server/"+PATCH_FILE;
    public static final String URL_PATCH_DOWNLOAD = "http://192.168.43.60/Bsdiff/" + PATCH_FILE;

    public static final String PACKAGE_NAME = "com.nan.diff";


}

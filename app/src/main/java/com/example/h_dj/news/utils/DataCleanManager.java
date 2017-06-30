package com.example.h_dj.news.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by H_DJ on 2017/6/27.
 */

public class DataCleanManager {

    private static final String DATA_BASE_PATH = "/data/data/";
    private Context mContext;
    private static DataCleanManager mDataCleanManager;

    private DataCleanManager(Context context) {
        this.mContext = context;
    }

    public static DataCleanManager getInstance(Context context) {
        if (mDataCleanManager == null) {
            synchronized (DataCleanManager.class) {
                if (mDataCleanManager == null) {
                    mDataCleanManager = new DataCleanManager(context);
                }
            }
        }
        return mDataCleanManager;
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context
     */
    public DataCleanManager cleanInternalCache() {
        deleteFilesByDirectory(mContext.getCacheDir());
        return this;
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
     */
    public DataCleanManager cleanDatabases() {
        deleteFilesByDirectory(new File(DATA_BASE_PATH
                + mContext.getPackageName() + "/databases"));
        return this;
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public DataCleanManager cleanSharedPreference() {
        deleteFilesByDirectory(new File(DATA_BASE_PATH
                + mContext.getPackageName() + "/shared_prefs"));
        return this;
    }

    /**
     * 按名字清除本应用数据库 * * @param context * @param dbName
     */
    public DataCleanManager cleanDatabaseByName(String dbName) {
        mContext.deleteDatabase(dbName);
        return this;
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context
     */
    public DataCleanManager cleanFiles() {
        deleteFilesByDirectory(mContext.getFilesDir());
        deleteFilesByDirectory(mContext.getExternalFilesDir(null));
        return this;
    }

    public DataCleanManager cleanWebViewFile() {
        // WebView 缓存文件
        File appCacheDir = new File(DATA_BASE_PATH + mContext.getPackageName() + "/app_webview");
        if (appCacheDir.exists()) {
            deleteFilesByDirectory(appCacheDir);
        }
        return this;
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public DataCleanManager cleanExternalCache() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(mContext.getExternalCacheDir());
        }
        return this;
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath
     */
    public DataCleanManager cleanCustomCache(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            deleteFilesByDirectory(new File(filePath));
        }
        return this;
    }

    /**
     * 清除本应用所有的数据 * * @param context * @param filepath
     */
    public DataCleanManager cleanApplicationData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    cleanInternalCache();
                    cleanExternalCache();
                    cleanSharedPreference();
                    cleanDatabases();
                    cleanFiles();
                    cleanWebViewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return this;
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            LogUtil.e("要删除文件夹：" + directory.getAbsolutePath());
            for (File item : directory.listFiles()) {
                boolean isDel = item.delete();
                LogUtil.e("删除：" + isDel);
            }
        }
    }

    /**
     * 格式化缓存的大小
     *
     * @return
     * @throws Exception
     */
    public String getTotalCacheSize() throws Exception {
        long cacheSize = getCacheSize();
        return getFormatSize(cacheSize);
    }

    /**
     * 获取缓存大小
     *
     * @return
     * @throws Exception
     */
    public long getCacheSize() throws Exception {
        long cacheSize = getFolderSize(mContext.getCacheDir());
        cacheSize += getFolderSize(mContext.getFilesDir());
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += getFolderSize(mContext.getExternalCacheDir());
                cacheSize += getFolderSize(mContext.getExternalFilesDir(null));
            }
        }
        LogUtil.e("缓存路径：" + mContext.getCacheDir() + ": " + mContext.getFilesDir() + ":" + mContext.getExternalCacheDir() + ":" + mContext.getExternalFilesDir(null));
        return cacheSize;
    }


    /**
     * 获取缓存文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public long getFolderSize(File file) throws Exception {
        if (file == null) {
            return 0;
        }
        if (!file.isDirectory()) {
            return 0;
        }
        long size = 0;
        if (file.exists()) {
            try {
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return size;

    }

    /**
     * 格式化大小
     *
     * @param size
     * @return
     */
    public String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
}

package com.gambition.recorder;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(ex);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private void handleException(final Throwable ex) {
        if (ex == null) {
            return;
        } else {
            new SimpleNetTask(mContext) {

                @Override
                protected void doInBack() throws Exception {
                    Map<String, Object> params = new HashMap<>();
                    params.put("content", getStackTraceText(ex));
                    UrlConnectionUtil.doPost("https://stg-idogogo.leanapp.cn/api/applog", params);
                }

                @Override
                protected void onSucceed() {

                }
            }.execute();
        }
    }

    public static String getStackTraceText(Throwable t) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            return sw.toString();
        } catch (Exception e) {
        }
        return "";
    }
}  
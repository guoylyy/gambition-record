package com.gambition.recorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * 网络异步通信
 */
public abstract class NetAsyncTask extends AsyncTask<Void, Void, Void> {
    protected Context ctx;
    ProgressDialog dialog = null;
    boolean openDialog = true;
    Exception exception;

    protected NetAsyncTask(Context ctx) {
        this.ctx = ctx;
    }

    protected NetAsyncTask(Context ctx, boolean openDialog) {
        this.ctx = ctx;
        this.openDialog = openDialog;
    }

    public NetAsyncTask setOpenDialog(boolean openDialog) {
        this.openDialog = openDialog;
        return this;
    }

    public ProgressDialog getDialog() {
        return dialog;
    }

    public NetAsyncTask setDialog(ProgressDialog dialog) {
        this.dialog = dialog;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!isNetworkAvailable(ctx)) {
            Log.d("network", "当前网络不可用");
            Toast.makeText(ctx, "当前网络不可用", Toast.LENGTH_LONG).show();
            netWorkFail();
            cancel(true);
        } else if (openDialog) {
            if (dialog == null) {
                dialog = new ProgressDialog(ctx);
            }
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(true);
            dialog.setMessage("努力加载中...");
            if (!((Activity) ctx).isFinishing()) {
                dialog.show();
            }
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            doInBack();
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (openDialog) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        onPost(exception);
    }

    protected abstract void doInBack() throws Exception;

    protected abstract void onPost(Exception e);

    public boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return null != connectivityManager && null != connectivityManager.getActiveNetworkInfo() && connectivityManager.getActiveNetworkInfo().isAvailable();
    }

    protected void netWorkFail() {
    }
}

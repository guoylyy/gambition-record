package com.gambition.recorder;

import android.content.Context;
import android.widget.Toast;

/**
 * 网络异步通信 后面可以用这个做异步通信的 重写doInBack和onSucceed方法就好
 */
public abstract class SimpleNetTask extends NetAsyncTask {
    protected SimpleNetTask(Context cxt) {
        super(cxt);
    }

    protected SimpleNetTask(Context cxt, boolean openDialog) {
        super(cxt, openDialog);
    }


    @Override
    protected void onPost(Exception e) {
        if (e != null) {
            e.printStackTrace();
            onFailed();
            Toast.makeText(ctx, "网络异常", Toast.LENGTH_SHORT).show();
        } else {
            onSucceed();
        }
    }

    protected abstract void doInBack() throws Exception;

    protected abstract void onSucceed();

    protected void onFailed() {
    }
}

package com.gambition.recorder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

/**
 * 检查权限的工具类
 * Created by alfred_yuan on 4/27/16.
 */
public class PermissionsCheckerUtil {
    private static final String POSITIVE_BUTTON_TEXT = "确定";
    private static final String NEGATIVE_BUTTON_TEXT = "取消";
    private final Context mContext;

    public PermissionsCheckerUtil(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return mContext.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED;
    }

    /**
     * 授权失败弹窗提醒
     */
    public void alterGrantPermissionFailed(Context context) {
        new GambitionNotifyDialog.Builder(context)
                .setTitle(R.string.view_client_activity_permission_declined)
                .setPositiveButton(POSITIVE_BUTTON_TEXT, mCancelListener)
                .setNegativeButton(NEGATIVE_BUTTON_TEXT, mCancelListener)
                .create()
                .show();
    }

    /**
     * 取消操作，关闭窗口
     */
    private DialogInterface.OnClickListener mCancelListener = new android.content.DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
}
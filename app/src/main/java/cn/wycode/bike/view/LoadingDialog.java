package cn.wycode.bike.view;

import android.content.Context;

import cn.wycode.bike.R;


/**
 * Created by huangyi on 16/2/27.
 * 加载Dialog
 */
public class LoadingDialog extends BaseDialog {
    public static LoadingDialog mLoadingDialog;

    public LoadingDialog(Context context, int layoutId) {
        super(context, layoutId);
    }

    /**
     * 展示Dialog
     */
    public static void showDialog(Context context) {
        if (mLoadingDialog == null) {
            synchronized (LoadingDialog.class) {
                if (mLoadingDialog == null) {
                    mLoadingDialog = new LoadingDialog(context, R.layout.dialog_loading);
                    mLoadingDialog.show();
                }
            }
        } else {
            mLoadingDialog = null;
            showDialog(context);
        }
    }

    /**
     * 消失Dialog
     */
    public static void dismissDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }


    }

    @Override
    public void show() {
        if (this.isShowing()) {
            dismiss();
        }
        super.show();
    }
}

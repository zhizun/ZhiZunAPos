package com.ch.epw.utils;

import com.zhizun.pos.view.photograph.Bimp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.ClipboardManager;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class BaseTools {

	/** 获取屏幕的宽度 */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取教师的称谓 0：校长；1：老师；2：园长；3：副园长；4：班主任。
	 * 
	 * @param post
	 * @return
	 */
	public static String getAppe(String post) {
		if(post != null && !post.equals("")){
			String appe = "";
			switch (Integer.parseInt(post)) {
			case 0:
				appe = "校长";
				break;
			case 1:
				appe = "老师";
				break;
			case 2:
				appe = "园长";
				break;
			case 3:
				appe = "副园长";
				break;
			case 4:
				appe = "班主任";
				break;
			default:
				break;
			}
			return appe;
		}
		return "";
	}
	//上传图片公用对象
	public static Bimp bimp=new Bimp();
	public static Dialog copyText(final Context context,final String content) {
		Dialog dialog = new AlertDialog.Builder(context).setTitle("提示")
				.setNegativeButton("取消", null)
				.setItems(new String[] { "复制" }, new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// 得到剪贴板管理器
						ClipboardManager cmb = (ClipboardManager) context
								.getSystemService(Context.CLIPBOARD_SERVICE);
						cmb.setText(content.trim());
						Toast.makeText(context, "文本已复制到粘贴板", 2000)
								.show();
					}
				}).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		return dialog;

	}
}

package com.zhizun.pos.widget.actionsheet;

import com.zhizun.pos.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionSheet {

	public interface OnActionSheetSelected {
		public void onClick(View view);
	}

	private ActionSheet() {
	}

	public static Dialog showSheet(Context context, String contentText,
			String cancelText, final OnActionSheetSelected selectedListener,
			final OnCancelListener cancelListener) {
		final Dialog dlg = new Dialog(context, R.style.ActionSheet);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.actionsheet, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		TextView mContent = (TextView) layout
				.findViewById(R.id.actionsheet_content);
		TextView mCancel = (TextView) layout
				.findViewById(R.id.actionsheet_cancel);
		if (!TextUtils.isEmpty(contentText)) {
			mContent.setText(contentText);
		}
		if (!TextUtils.isEmpty(cancelText)) {
			mCancel.setText(cancelText);
		}

		mContent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectedListener != null) {
					selectedListener.onClick(v);
				}
				dlg.dismiss();
			}
		});

		mCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cancelListener != null) {
					cancelListener.onCancel(dlg);
				}
				dlg.dismiss();
			}
		});

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		if (cancelListener != null)
			dlg.setOnCancelListener(cancelListener);

		dlg.setContentView(layout);
		dlg.show();

		return dlg;
	}
}

package com.ch.epw.view;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ch.epw.utils.ImageUtils;
import com.ch.epw.view.SelectSexDialog.LeaveMyDialogListener;
import com.zhizun.pos.R;

public class PopupWindows extends PopupWindow implements
		android.view.View.OnClickListener {

	Context context;
	private LeaveMyDialogListener listener;
	Button item_popupwindows_camera;
	Button item_popupwindows_Photo;
	Button item_popupwindows_cancel;

	public interface LeaveMyDialogListener {
		public void onClick(View view);
	}

	public PopupWindows(Context mContext, View parent,
			LeaveMyDialogListener listener) {
		this.context = context;
		this.listener = listener;
		View view = View.inflate(mContext, R.layout.item_popupwindows, null);
		view.startAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.fade_ins));
		LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.push_bottom_in_2));

		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.FILL_PARENT);
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		update();

		item_popupwindows_camera = (Button) view
				.findViewById(R.id.item_popupwindows_camera);
		item_popupwindows_Photo = (Button) view
				.findViewById(R.id.item_popupwindows_Photo);
		item_popupwindows_cancel = (Button) view
				.findViewById(R.id.item_popupwindows_cancel);
		item_popupwindows_camera.setOnClickListener(this);
		item_popupwindows_Photo.setOnClickListener(this);
		item_popupwindows_cancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		listener.onClick(v);
	}
}
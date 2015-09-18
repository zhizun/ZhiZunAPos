package com.zhizun.pos.adapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.FileUtils;
import com.ch.epw.utils.ImageUtils;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;

/**
 * GrideView 图片Adapter
 */
public class GridViewUploadImagesAdapter extends MyBaseAdapter {
	protected static final String TAG = GridViewUploadImagesAdapter.class
			.getName();
	private Context context;// 运行上下文
	private LayoutInflater inflater; // 视图容器
	private int selectedPosition = -1;// 选中的位置
	private boolean shape;
	private Handler handler;
	String  imageViewNmae;
	// 最大上传图片限制，默认值为 Constant.UPLOAD_PHOTO_COUNT（9），支持自定义设置
	int maxUploadCount = Constant.UPLOAD_PHOTO_COUNT;

	public boolean isShape() {
		return shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}

	public GridViewUploadImagesAdapter(Context context,Handler handler) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.handler=handler;
	}

	public void update(Handler handler) {
		loading(handler);
	}

	public int getCount() {
		if (BaseTools.bimp.getBmp().size() < maxUploadCount) {
			return (BaseTools.bimp.getBmp().size() + 1);
		} else {
			return BaseTools.bimp.getBmp().size();
		}
	}

	public Object getItem(int position) {
		if (position < BaseTools.bimp.getBmp().size()
				&& BaseTools.bimp.getBmp().size() > 0) {
			return BaseTools.bimp.getBmp().get(position);
		}

		return null;
	}

	public long getItemId(int arg0) {

		return 0;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(final int position, View convertView, ViewGroup parent) {
		final int coord = position;
		ViewHolder holder = null;
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.square_item_published_grid,
					parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView)convertView
					.findViewById(R.id.item_grida_image);
			holder.lv_image=(LinearLayout) convertView.findViewById(R.id.lv_image);
			holder.tv_image_error=(ImageView) convertView.findViewById(R.id.tv_image_error);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Bitmap bmap = (Bitmap) getItem(position);
		// if (position == BaseTools.bimp.getBmp().size()) {
		if (bmap == null) {
			holder.image.setImageBitmap(BitmapFactory.decodeResource(
					context.getResources(), R.drawable.add_new));
			holder.lv_image.setVisibility(View.GONE);

		} else {
			holder.image.setImageBitmap(bmap);
			String Str = BaseTools.bimp.getDrr().get(position).substring(
					BaseTools.bimp.getDrr().get(position).lastIndexOf("/") + 1,
					BaseTools.bimp.getDrr().get(position).lastIndexOf("."));
			imageViewNmae=FileUtils.FILE_SDCARD + Str + ".JPEG";
//			BaseTools.bimp.getDrr().get(position)
			if (BaseTools.bimp.getErrorFiles().contains(imageViewNmae)) {
				holder.lv_image.setVisibility(View.VISIBLE);
//				holder.tv_image_error.setVisibility(View.VISIBLE);
			}else{
				holder.lv_image.setVisibility(View.GONE);
			}
			holder.lv_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i("position", position+"");
					Dialog(position);
				}
			});
		}

		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
		public ImageView tv_image_error;
		public LinearLayout lv_image;
	}

	public void loading(final Handler handler) {
		new Thread(new Runnable() {
			public void run() {

				while (true) {
					if (BaseTools.bimp.getMax() == BaseTools.bimp.getDrr().size()) {
						Message message = new Message();
						message.what = Constant.UPLOAD_IMG_SEND_WHAT;
						handler.sendMessage(message);
						break;
					} else {
						try {
							String oldPath = BaseTools.bimp.getDrr().get(BaseTools.bimp.getMax());
							System.out.println(oldPath);
							Bitmap bm = revitionImageSize(oldPath);
							BaseTools.bimp.getBmp().add(bm);

							// 图片名字前面的部分
							String newStrFirst = oldPath.substring(0,
									oldPath.lastIndexOf("/") + 1);
							Log.i(TAG, "图片名字前面的部分=" + newStrFirst);
							// 图片名字 不包括后缀
							String newStrName = oldPath.substring(
									oldPath.lastIndexOf("/") + 1,
									oldPath.lastIndexOf("."));
							Log.i(TAG, "图片名字 不包括后缀=" + newStrName);
							// 图片的后缀
							String picSuffix = oldPath.substring(
									oldPath.lastIndexOf("."), oldPath.length());
							Log.i(TAG, "图片的后缀=" + picSuffix);
							// 将图片重命名
							String newPicNameString = newStrName.replace(
									newStrName, ImageUtils.getTempFileName());
							Log.i(TAG, "图片重命名的名字=" + newPicNameString);
							// 拼接到一块 成为一个完整路径
							String path = newStrFirst + newPicNameString
									+ picSuffix;
							// 替换 Bimp.drr 中的地址
							BaseTools.bimp.getDrr().set(BaseTools.bimp.getMax(), path);
							//
							//
							// String newStr = path.substring(
							// path.lastIndexOf("/") + 1,
							// path.lastIndexOf("."));

							FileUtils.saveBitmap(bm, "" + newPicNameString);
							BaseTools.bimp.setMax(BaseTools.bimp.getMax()+1);
//							bimp.max += 1;
							// Message message = new Message();
							// message.what = 1;
							// handler.sendMessage(message);
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
	public Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	private void Dialog(final int position) {
		String Str = BaseTools.bimp.getDrr().get(position).substring(
				BaseTools.bimp.getDrr().get(position).lastIndexOf("/") + 1,
				BaseTools.bimp.getDrr().get(position).lastIndexOf("."));
		final String imageViewNmaePath=FileUtils.FILE_SDCARD + Str + ".JPEG";
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.upload_imageView);
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Message message = new Message();
						message.what = Constant.UPLOAD_IMAGE_ERROR;
						Bundle bundle=new Bundle();  
		                bundle.putString("imageViewNmae", imageViewNmaePath);  
		                message.setData(bundle);
						handler.sendMessage(message);
						Toast.makeText(context, "正在上传，请稍后...",
								Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
				});
		builder.setNegativeButton("删除",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						BaseTools.bimp.getBmp().remove(position);
						BaseTools.bimp.getDrr().remove(position);
						BaseTools.bimp.setMax(BaseTools.bimp.getMax()-1);
						Message message = new Message();
						message.what = Constant.DELETE_IMAGE_ERROR;
						Bundle bundle=new Bundle();  
		                bundle.putString("imageViewNmaePath", imageViewNmaePath);  
		                message.setData(bundle);
						handler.sendMessage(message);
						dialog.dismiss();
					}
				});
		builder.show();
	}

	public int getMaxUploadCount() {
		return maxUploadCount;
	}

	public void setMaxUploadCount(int maxUploadCount) {
		this.maxUploadCount = maxUploadCount;
	}

}

package com.ch.epw.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.FileUtils;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.PopupWindows;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.AlbumShowActivity;
import com.zhizun.pos.activity.PhotoShowActivity;
import com.zhizun.pos.adapter.GridViewUploadImagesAdapter;
import com.zhizun.pos.api.ApiClient;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Photo;
import com.zhizun.pos.bean.Picture;
import com.zhizun.pos.bean.PictureList;

/**
 * 上传图片helper
 * 
 * @author lyc
 *
 */
public class UploadImageHelper {

	private final String TAG = UploadImageHelper.class.getName();
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static String localTempImageFileName = "";
	private static String clientImgUrlPre = "";

	private Context context;
	private GridViewUploadImagesAdapter uploadImagesAdapteradapter;
	private GridView imageListView;
	private OnImageUploadFinshedListener onImageUploadFinshedListener;

	private List<Picture> uploadedPicList = new ArrayList<Picture>();
	private List<String> lastNeedUploadPicPaths = null;
	private List<String> needUploadPicPaths = new ArrayList<String>();
	private List<String> successfulPicPaths = new ArrayList<String>();
	private List<String> failedPicPaths = new ArrayList<String>();

	private PopupWindows popSelectPhotoSource;
	private ProgressDialog progress;

	int uploadImgCount = 0;// 要上传图片的总数量

	Set<String> cameraPhotoSet = new HashSet<String>();

	public UploadImageHelper(Context context, GridView imageListView) {
		this.context = context;
		this.imageListView = imageListView;
		resetContents();
		imageListView.setAdapter(getUploadImagesAdapteradapter());
		imageListView.setOnItemClickListener(onImageListItemListener);
	}

	public UploadImageHelper(Context context, GridView imageListView,
			int uploadSize) {
		this(context, imageListView);
		getUploadImagesAdapteradapter().setMaxUploadCount(uploadSize);
	}

	public void refreshAndLoadImage() {
		// 判断拍照生成的照片是否生成
		File cameraPhoto = new File(FileUtils.FILE_SDCARD, localTempImageFileName);
		if (BaseTools.bimp.getDrr().size() < getUploadImagesAdapteradapter().getMaxUploadCount()) {
			if (cameraPhoto.exists()
					&& cameraPhoto.isFile()
					&& !cameraPhotoSet.contains(
							cameraPhoto.getAbsolutePath())) {
				cameraPhotoSet.add(cameraPhoto.getAbsolutePath());
				BaseTools.bimp.getDrr().add(cameraPhoto.getAbsolutePath());
			}
		}
		showProgress("图片加载中...");
		uploadImagesAdapteradapter.update(imageLoadAsyncHandler);
	}

	public void upLoadImageAsync() {

		needUploadPicPaths = getPhotoPathList();
		// 用户第一次选取图片
		if (lastNeedUploadPicPaths == null) {
			lastNeedUploadPicPaths = new ArrayList<String>();
			lastNeedUploadPicPaths.addAll(needUploadPicPaths);
		} else {
			// 用户对图片集合进行了改动， 之前成功上传的图片可能不需要了， 从头再来
			if (!needUploadPicPaths.equals(lastNeedUploadPicPaths)) {
				successfulPicPaths.clear();
				failedPicPaths.clear();
				lastNeedUploadPicPaths.clear();
				uploadedPicList.clear();
				lastNeedUploadPicPaths.addAll(needUploadPicPaths);
			}
		}

		if (needUploadPicPaths.size() <= 0) {
			imageLoadAsyncHandler.sendEmptyMessage(Constant.UPLOAD_IMG_SEND);
		} else {
			List<String> listPath = getRemainPathList();
			showProgress("图片上传中...");
			for (String path : listPath) {
				try {
					uploadFile(context, path, URLs.UPLOADIMG_URL);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getPicturePostFormat(){
		StringBuffer strBuff = new StringBuffer();
		for (Picture pic : uploadedPicList) {
			strBuff.append(pic.getThumbPath()).append("@")
					.append(pic.getPicPath()).append(",");
		}
		if (strBuff.length() > 1) {
			return strBuff.substring(0, strBuff.length() - 1).toString();
		}
		return "";
	}

	private Handler imageLoadAsyncHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			hideProgress();
			switch (msg.what) {
			case Constant.UPLOAD_IMG_SEND_WHAT:
				// noScrollgridview.setAdapter(uploadImagesAdapteradapter);
				uploadImagesAdapteradapter.notifyDataSetChanged();
				break;
			case Constant.UPLOAD_IMG_SEND:
				if (onImageUploadFinshedListener != null) {
					onImageUploadFinshedListener.onImageUploadFinshed();
				}
				break;

			case Constant.UPLOAD_IMAGE_ERROR:
				String picPath = msg.getData().getString("imageViewNmae");
				try {
					uploadFile(context, picPath, URLs.UPLOADIMG_URL);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case Constant.DELETE_IMAGE_ERROR:
				String imageViewNmaePath = msg.getData().getString(
						"imageViewNmaePath");
				uploadImagesAdapteradapter.notifyDataSetChanged();
				failedPicPaths.remove(imageViewNmaePath);
				uploadImgCount--;
				judgeSucceed();
				break;

			default:
				break;
			}
		}
	};

	OnItemClickListener onImageListItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Object item = parent.getAdapter().getItem(position);
			if (item != null) {
				Intent intent = new Intent(context, PhotoShowActivity.class);
				intent.putExtra("ID", position);
				context.startActivity(intent);
			}
			// 弹出上传图片选择对话框
			else {
				popSelectPhotoSource = new PopupWindows(
					context,
					imageListView,
					new PopupWindows.LeaveMyDialogListener() {
						@Override
						public void onClick(View view) {
							switch (view.getId()) {
							case R.id.item_popupwindows_camera:
								String status = Environment
										.getExternalStorageState();
								if (status
										.equals(Environment.MEDIA_MOUNTED)) {
									try {
										localTempImageFileName = "";
											// 本地图片地址前缀
										clientImgUrlPre = FileUtils.FILE_SDCARD;
											// 生成图片名字 唯一
										localTempImageFileName = ImageUtils
												.getTempFileName()
												+ ".JPEG";
										String clientImgUrl = clientImgUrlPre
												+ localTempImageFileName;
										Log.i("TAG", "clientImgUrl="
												+ clientImgUrl);
										File filePath = new File(
												clientImgUrlPre);
										if (!filePath.exists()) {
											if (!filePath.mkdirs()) {
												return;
											}
										}
										Intent intent = new Intent(
												android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
										File f = new File(filePath,
												localTempImageFileName);

										Uri u = Uri.fromFile(f);
										intent.putExtra(
												MediaStore.Images.Media.ORIENTATION,
												0);
										intent.putExtra(
												MediaStore.EXTRA_OUTPUT, u);
										context.startActivity(intent);
										// context.startActivityForResult(intent,
										// FLAG_CHOOSE_PHONE);
									} catch (ActivityNotFoundException e) {
										//
									}
								}
								break;
							case R.id.item_popupwindows_Photo:
								
									// maxUploadCount参数传递路径:
									// UploadImageHelper（GridViewUploadImagesAdapter）
									// -> AlbumShowActivity ->
									// ImageGridActivity（ImageGridAdapter）
								Intent intent = new Intent(context,
										AlbumShowActivity.class);
								intent.putExtra("maxUploadCount", getUploadImagesAdapteradapter().getMaxUploadCount());
								context.startActivity(intent);
								break;
							case R.id.item_popupwindows_cancel:

								break;
							}
							popSelectPhotoSource.dismiss();
						}
					});
			}
		}
	};

	// 返回选取的图片地址
	private List<String> getPhotoPathList() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < BaseTools.bimp.getDrr().size(); i++) {
			String Str = BaseTools.bimp
					.getDrr()
					.get(i)
					.substring(
							BaseTools.bimp.getDrr().get(i).lastIndexOf("/") + 1,
							BaseTools.bimp.getDrr().get(i).lastIndexOf("."));
			Log.i(TAG, "上传图片的路径：" + i + "--" + FileUtils.FILE_SDCARD + Str
					+ ".JPEG");
			list.add(FileUtils.FILE_SDCARD + Str + ".JPEG");
		}
		Log.i(TAG, "上传图片的数量=" + list.size());
		uploadImgCount = list.size();
		return list;
	}

	private List<String> getRemainPathList() {
		List<String> result = new ArrayList<String>();
		result.addAll(needUploadPicPaths);
		result.removeAll(this.successfulPicPaths);
		return result;
	}

	/**
	 * @param path
	 *            要上传的文件路径
	 * @param url
	 *            服务端接收URL
	 * @throws Exception
	 */
	public void uploadFile(final Context context, final String path, String url)
			throws Exception {
		File file = new File(path);
		if (file.exists() && file.length() > 0) {
			Log.i(TAG, "压缩前img_length=" + file.length());
			Bitmap bm = BitmapFactory.decodeFile(path);
			// 压缩图片 路径
			String path_compressed = BaseActivity.FILE_LOCAL
					+ ImageUtils.getTempFileName() + ".jpg";
			File file_compressed = new File(path_compressed);

			ImageUtils.compressBmpToFile(bm, file_compressed);// 压缩后

			if (file_compressed.exists() && file_compressed.length() > 0) {
				Log.i(TAG, "压缩之后img_length=" + file_compressed.length());
				AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params = new RequestParams();

				params.put("uploadfile", file_compressed);

				// 上传文件
				client.post(url, params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						// 上传成功后要做的工作
						if (statusCode == 200) {
							try {

								PictureList pictureList = PictureList
										.parse(new String(
										responseBody));
								if (null != pictureList
										&& pictureList.getPictureList().size() > 0) {

									handleUploadResult(path, true);

									Picture picture = pictureList
											.getPictureList().get(0);
									uploadedPicList.add(picture);
									Log.e(TAG, "uploadedPicList.size="
											+ uploadedPicList.size());
									Log.e(TAG, "uploadImgCount="
											+ uploadImgCount);

								} else {
									handleUploadResult(path, false);
								}

							} catch (IOException e) {
								handleUploadResult(path, false);
								e.printStackTrace();
							} catch (AppException e) {
								handleUploadResult(path, false);
								e.printStackTrace();
							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						handleUploadResult(path, false);
						if (null != responseBody) {
							Log.i("上传图片失败后返回值为=", new String(responseBody));
						}
					}

				});
			} else {
				Toast.makeText(context, "压缩后的文件不存在", Toast.LENGTH_LONG).show();
			}

		} else {
			Toast.makeText(context, "图片文件不存在", Toast.LENGTH_LONG).show();
		}
	}

	synchronized private void handleUploadResult(String filePath, boolean result) {
		if (result) {
			this.successfulPicPaths.add(filePath);
			if (BaseTools.bimp.getErrorFiles().size() > 0) {
				BaseTools.bimp.getErrorFiles().remove(filePath);
				// 刷新界面
				imageLoadAsyncHandler
						.sendEmptyMessage(Constant.UPLOAD_IMG_SEND_WHAT);
			}
		} else {
			if (!this.failedPicPaths.contains(filePath)) {
				this.failedPicPaths.add(filePath);
			}
		}

		judgeSucceed();
	}

	public void judgeSucceed() {
		if (this.successfulPicPaths.size() == uploadImgCount) {
			// 全部上传成功， 调用接口插入在校动态
			imageLoadAsyncHandler.sendEmptyMessage(Constant.UPLOAD_IMG_SEND);

		} else if (this.successfulPicPaths.size() + this.failedPicPaths.size() == uploadImgCount) {

			if (this.failedPicPaths.size() > 0) {
				List<String> errorFiles = new ArrayList<String>();
				errorFiles.addAll(failedPicPaths);
				BaseTools.bimp.setErrorFiles(errorFiles);
				imageLoadAsyncHandler
						.sendEmptyMessage(Constant.UPLOAD_IMG_SEND_WHAT);
				Toast.makeText(context, "有部分图片上传不成功，可点击单张发送", Toast.LENGTH_LONG)
						.show();
				failedPicPaths.clear();
			}
		}
	}

	public void resetContents() {
		// 清除图片目录 路径
		BaseTools.bimp.setMax(0);
		BaseTools.bimp.getDrr().clear();
		BaseTools.bimp.getBmp().clear();
		FileUtils.clearFileWithPath(FileUtils.FILE_SDCARD);
	}
	
	public void loadImageForUpdate(List<Photo> photoList) {
		if(photoList == null || photoList.size() == 0){
			return;
		}
		final int totalPhotoSize = photoList.size();
		showProgress("图片加载中...");
		for (int k = 0; k < totalPhotoSize; k++) {
			Photo photo = photoList.get(k);
			final String remoteFilePath = photo.getThumbPath()+ photo.getThumbSaveName();
			// 名字加上循环下标避免重复
			final String saveName = ImageUtils.getTempFileName() + "_" + k;
			final String savePathName = FileUtils.FILE_SDCARD + saveName + ".JPEG";
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Bitmap bout = ApiClient.getNetBitmap(remoteFilePath);
						FileUtils.saveBitmap(bout, saveName);
						BaseTools.bimp.getDrr().add(savePathName);
						if(BaseTools.bimp.getDrr().size() == totalPhotoSize){
							uploadImagesAdapteradapter
									.update(imageLoadAsyncHandler);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}).start();
		}
	}

	public interface OnImageUploadFinshedListener {
		public void onImageUploadFinshed();
	}

	public GridViewUploadImagesAdapter getUploadImagesAdapteradapter() {
		if (uploadImagesAdapteradapter == null) {
			uploadImagesAdapteradapter = new GridViewUploadImagesAdapter(
					context, imageLoadAsyncHandler);
		}
		return uploadImagesAdapteradapter;
	}

	public OnImageUploadFinshedListener getOnImageUploadFinshedListener() {
		return onImageUploadFinshedListener;
	}

	public void setUploadImagesAdapteradapter(
			GridViewUploadImagesAdapter uploadImagesAdapteradapter) {
		this.uploadImagesAdapteradapter = uploadImagesAdapteradapter;
	}


	public void setOnImageUploadFinshedListener(
			OnImageUploadFinshedListener onImageUploadFinshedListener) {
		this.onImageUploadFinshedListener = onImageUploadFinshedListener;
	}

	protected void showProgress(String msg) {
		hideProgress();
		progress = ProgressDialog.show(context, "", msg);
	}

	protected void hideProgress() {
		if (progress != null) {
			progress.dismiss();
		}
	}

}

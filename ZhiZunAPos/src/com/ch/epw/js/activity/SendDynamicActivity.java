package com.ch.epw.js.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.EmojiFilter;
import com.ch.epw.utils.FileUtils;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.PopupWindows;
import com.ch.epw.view.TitleBarView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.AlbumShowActivity;
import com.zhizun.pos.activity.PhotoShowActivity;
import com.zhizun.pos.adapter.GridViewUploadImagesAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Picture;
import com.zhizun.pos.bean.PictureList;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.StudentClass;
import com.zhizun.pos.bean.StudentInfo;

/**
 * 发送动态 创建人：李林中 创建日期：2014-12-15 上午10:10:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class SendDynamicActivity extends BaseActivity {
	Context context;
	private TitleBarView titleBarView;
	RelativeLayout rl_activity_fsdt_teacher_receviers;
	ArrayList<StudentClass> studentClasseList;
	TextView tv_activity_fsdt_teacher_receivers,
			tv_activity_fsdt_teacher_receivers_count,
			tv_activity_fsdt_teacher_content_count;
	PopupWindows popupWindows;
	private GridViewUploadImagesAdapter adapter;
	// 拍照上传 所用参数
	private static String localTempImageFileName = "";
	private static String clientImgUrlPre = "";

	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final String TAG = SendDynamicActivity.class.getName();
	Boolean ftpImgResult;// 图片上传是否成功
	Bitmap bsave;
	String fileName;
	PictureList pictureList;
	Result result;
	int RESULT_CODE_INSERTDYNAMIC = 1;
	EditText et_activity_fsdt_teacher_content;
	protected List<Picture> uploadedPicList = new ArrayList<Picture>();
	private List<String> lastNeedUploadPicPaths = null;
	private List<String> needUploadPicPaths = new ArrayList<String>();
	private List<String> successfulPicPaths = new ArrayList<String>();
	private List<String> failedPicPaths = new ArrayList<String>();
	String content;// 上传内容
	GridView noScrollgridview;
	int uploadImgCount = 0;// 要上传图片的总数量
//	ArrayList<ProgressBean> progressNum=new ArrayList<ProgressBean>();
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case Constant.UPLOAD_IMG_SEND_WHAT:
				noScrollgridview.setAdapter(adapter);
				closeProgressDialog();
				break;
			case Constant.UPLOAD_IMG_SEND:
				new InsertdynamicTask().execute(AppContext.getApp().getToken(),
						EmojiFilter.filterEmoji(content), studentClasseList,
						uploadedPicList);
				break;
			case Constant.UPLOAD_IMAGE_ERROR:
				String picPath=msg.getData().getString("imageViewNmae");
				try {
					uploadFile(context, picPath, URLs.UPLOADIMG_URL);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case Constant.DELETE_IMAGE_ERROR:
				String imageViewNmaePath=msg.getData().getString("imageViewNmaePath");
//				int position=msg.getData().getInt("position");
//				BaseTools.bimp.getBmp().remove(position);
//				BaseTools.bimp.getDrr().remove(position);
				noScrollgridview.setAdapter(adapter);
				failedPicPaths.remove(imageViewNmaePath);
				uploadImgCount--;
				judgeSucceed();
				break;

			default:
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_fsdt_teacher);
		options = Options.getListOptions();
		sInputFormat = getResources()
				.getString(R.string.input_text_count_limit);
		sFinalInput = String.format(sInputFormat, Rest_Length);
		initView();
	}
	
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_fsdt_teacher);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);

		titleBarView.setTitleText(R.string.title_text_senddynamic);
		titleBarView.setRightText(R.string.title_text_send);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		rl_activity_fsdt_teacher_receviers = (RelativeLayout) findViewById(R.id.rl_activity_fsdt_teacher_receviers);
		rl_activity_fsdt_teacher_receviers
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,
								MyInvitationSelectStudentActivity.class);
						intent.putExtra("studentClasseList", studentClasseList);
						startActivityForResult(intent, 1);
						intoAnim();
					}
				});
		tv_activity_fsdt_teacher_receivers = (TextView) findViewById(R.id.tv_activity_fsdt_teacher_receivers);
		tv_activity_fsdt_teacher_receivers_count = (TextView) findViewById(R.id.tv_activity_fsdt_teacher_receivers_count);
		et_activity_fsdt_teacher_content = (EditText) findViewById(R.id.et_activity_fsdt_teacher_content);
		et_activity_fsdt_teacher_content
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

						Rest_Length = et_activity_fsdt_teacher_content
								.getText().toString().trim().length();

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						if (Rest_Length > MAX_LENGTH) {
							UIHelper.ToastMessage(context, "最多只能输入"+MAX_LENGTH+"个字");
							sFinalInput = String.format(sInputFormat,
									MAX_LENGTH);
							tv_activity_fsdt_teacher_content_count
									.setText(sFinalInput);
							et_activity_fsdt_teacher_content
									.setText(et_activity_fsdt_teacher_content
											.getText().toString().trim()
											.substring(0, MAX_LENGTH));
							et_activity_fsdt_teacher_content
									.setSelection(et_activity_fsdt_teacher_content
											.getText().toString().trim()
											.length());
						} else {
							sFinalInput = String.format(sInputFormat,
									Rest_Length);
							tv_activity_fsdt_teacher_content_count
									.setText(sFinalInput);
						}

					}
				});
		tv_activity_fsdt_teacher_content_count = (TextView) findViewById(R.id.tv_activity_fsdt_teacher_content_count);
		tv_activity_fsdt_teacher_content_count.setText(sFinalInput);
		titleBarView.setBarRightOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				needUploadPicPaths = getPhotoPathList();
				
				//用户第一次选取图片
				if(lastNeedUploadPicPaths == null){
					lastNeedUploadPicPaths = new ArrayList<String>();	
					lastNeedUploadPicPaths.addAll(needUploadPicPaths);
					
				}else{					
					//用户对图片集合进行了改动， 之前成功上传的图片可能不需要了， 从头再来
					if(!needUploadPicPaths.equals(lastNeedUploadPicPaths)){
						successfulPicPaths.clear();
						failedPicPaths.clear();
						lastNeedUploadPicPaths.clear();
						uploadedPicList.clear();
						lastNeedUploadPicPaths.addAll(needUploadPicPaths);
						
					}
				}
				
				
				
				
				content = et_activity_fsdt_teacher_content.getText().toString();
				if ((null == content || content.equals(""))
						&& needUploadPicPaths.size() == 0) {
					UIHelper.ToastMessage(context, "内容不能为空");
					return;
				}
				if (content != null && content.length() > MAX_LENGTH) {
					UIHelper.ToastMessage(context, "内容不能大于"+MAX_LENGTH+"个字符");
					return;
				}
				StringBuffer sBuffer = new StringBuffer();
				if (null != studentClasseList && studentClasseList.size() > 0) {
					for (StudentClass studentClass : studentClasseList) {
						if (!studentClass.getReceivers().equals("")) {
							sBuffer.append(studentClass.getReceivers());
							break;
						}
					}
					if (sBuffer.toString().length() <= 0) {
						UIHelper.ToastMessage(context, "接收人不能为空");
						return;
					}
				} else {
					UIHelper.ToastMessage(context, "接收人不能为空");
					return;
				}
				showProgressDialog(context, "",
						getResources().getString(R.string.submit_ing));

				if (needUploadPicPaths.size() <= 0) {
					handler.sendEmptyMessage(1);
				} else {
					List<String> listPath = getRemainPathList();
					for (String path : listPath) {
						try {
							uploadFile(context, path, URLs.UPLOADIMG_URL);
						} catch (Exception e) {
							closeProgressDialog();
							e.printStackTrace();
						}
					}
				}

			}
		});

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridViewUploadImagesAdapter(this,handler);
		adapter.update(handler);
		noScrollgridview.setAdapter(adapter);

		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// 点击时关闭输入法
				((InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(
								et_activity_fsdt_teacher_content
										.getWindowToken(), 0);

				if (position ==BaseTools.bimp.getBmp().size()) {
					popupWindows = new PopupWindows(SendDynamicActivity.this,
							noScrollgridview,
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
														MediaStore.EXTRA_OUTPUT,
														u);
												startActivityForResult(intent,
														FLAG_CHOOSE_PHONE);
											} catch (ActivityNotFoundException e) {
												//
											}
										}
										popupWindows.dismiss();
										break;
									case R.id.item_popupwindows_Photo:
										Intent intent = new Intent(
												SendDynamicActivity.this,
												AlbumShowActivity.class);
										startActivity(intent);
										popupWindows.dismiss();
										break;
									case R.id.item_popupwindows_cancel:
										popupWindows.dismiss();
										break;
									}

								}
							});
				} else {
					Intent intent = new Intent(context, PhotoShowActivity.class);
					intent.putExtra("ID", position);
					startActivity(intent);
				}

			}
		});

		// 高清的压缩图片全部就在 list 路径里面了
		// 高清的压缩过的 bmp 对象 都在 Bimp.bmp里面
		// 完成上传服务器后 .........

		// activity_selectimg_send.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// List<String> list = new ArrayList<String>();
		// for (int i = 0; i < Bimp.drr.size(); i++) {
		// String Str = Bimp.drr.get(i).substring(
		// Bimp.drr.get(i).lastIndexOf("/") + 1,
		// Bimp.drr.get(i).lastIndexOf("."));
		// list.add(FileUtils.SDPATH+Str+".JPEG");
		// }
		// // 高清的压缩图片全部就在 list 路径里面了
		// // 高清的压缩过的 bmp 对象 都在 Bimp.bmp里面
		// // 完成上传服务器后 .........
		// FileUtils.deleteDir();
		// }
		// });

	}

	// 返回选取的图片地址
	private List<String> getPhotoPathList() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i <BaseTools.bimp.getDrr().size(); i++) {
			String Str = BaseTools.bimp.getDrr().get(i).substring(
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
	private List<String> getRemainPathList(){
		List<String> result = new ArrayList<String>();
		result.addAll(needUploadPicPaths);
		result.removeAll(this.successfulPicPaths);
		return result;
		
	}

	protected void onRestart() {
		showProgressDialog(context, "", "图片加载中...");
		adapter.update(handler);
		super.onRestart();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// 清除图片目录 路径
		BaseTools.bimp.setMax(0);
		BaseTools.bimp.getDrr().clear();
		BaseTools.bimp.getBmp().clear();
		FileUtils.deleteDir();// 清除上传图片文件夹
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		int count = 0;
		if (requestCode == 1 && resultCode == 1) {
			studentClasseList = (ArrayList<StudentClass>) data
					.getSerializableExtra("studentClasseList");
			StringBuffer sbBuffer = new StringBuffer();
			String nameListStringShort = "";
			if (null != studentClasseList && studentClasseList.size() > 0) {
				for (int i = 0; i < studentClasseList.size(); i++) {
					StudentClass sClass = studentClasseList.get(i);
					for (int j = 0; j < sClass.getStudentInfoList().size(); j++) {
						StudentInfo sInfo = sClass.getStudentInfoList().get(j);
						if (sInfo.getCheckState()) {
							if (sbBuffer.length() > 0) {
								sbBuffer.append(",");
							}
							sbBuffer.append(sInfo.getName());
							count++;
							if (count <= 2) {
								nameListStringShort = sbBuffer.toString();
							}
						}
					}
				}
			}

			if (count > 0) {
				if (!nameListStringShort.equals(sbBuffer.toString())) {
					nameListStringShort = nameListStringShort + "等";
				}
				tv_activity_fsdt_teacher_receivers.setText(nameListStringShort);
				tv_activity_fsdt_teacher_receivers_count.setText(" 共" + count
						+ "人");
			} else {
				tv_activity_fsdt_teacher_receivers.setText("");
				tv_activity_fsdt_teacher_receivers_count.setText("");
			}

		}
		// 选取图像后的返回处理方法
		if (requestCode == FLAG_CHOOSE_PHONE && resultCode == RESULT_OK) {
			Log.e(TAG, "选择了照相");

			File f = new File(FileUtils.FILE_SDCARD, localTempImageFileName);
			if (BaseTools.bimp.getDrr().size() < Constant.UPLOAD_PHOTO_COUNT) {
				BaseTools.bimp.getDrr().add(f.getAbsolutePath());
			}
		}
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
			String path_compressed = FILE_LOCAL + ImageUtils.getTempFileName()
					+ ".jpg";
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

								pictureList = PictureList.parse(new String(
										responseBody));
								if (null != pictureList
										&& pictureList.getPictureList().size() > 0) {
									
									handleUploadResult(path,true);
									
									Picture picture = pictureList
											.getPictureList().get(0);
									uploadedPicList.add(picture);
									Log.e(TAG, "uploadedPicList.size="
											+ uploadedPicList.size());
									Log.e(TAG, "uploadImgCount="
											+ uploadImgCount);
									
								}
								else {
									handleUploadResult(path,false);
								}
								
							} catch (IOException e) {
								closeProgressDialog();
								handleUploadResult(path,false);
								e.printStackTrace();
							} catch (AppException e) {
								closeProgressDialog();
								handleUploadResult(path,false);
								e.printStackTrace();
							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						handleUploadResult(path,false);
						if (null != responseBody) {
							Log.i("上传图片失败后返回值为=", new String(responseBody));
						}
					}

				});
			} else {
				//TODO
				closeProgressDialog();
				Toast.makeText(context, "压缩后的文件不存在", Toast.LENGTH_LONG).show();
			}

		} else {
			//TODO
			closeProgressDialog();
			Toast.makeText(context, "图片文件不存在", Toast.LENGTH_LONG).show();
		}
	}
	synchronized private void handleUploadResult(String filePath, boolean result){
		if(result){
			this.successfulPicPaths.add(filePath);
			if (BaseTools.bimp.getErrorFiles().size()>0) {
				BaseTools.bimp.getErrorFiles().remove(filePath);
				//刷新界面
				handler.sendEmptyMessage(2);
			}
		}
		else{
			if (!this.failedPicPaths.contains(filePath)) {
				this.failedPicPaths.add(filePath);
			}
		}
		
		judgeSucceed();
	}

	public void judgeSucceed() {
		if(this.successfulPicPaths.size() == uploadImgCount){
			// 全部上传成功， 调用接口插入在校动态
			handler.sendEmptyMessage(1);

		}else if(this.successfulPicPaths.size()+this.failedPicPaths.size() == uploadImgCount){
			
			if(this.failedPicPaths.size()>0){
				List<String> errorFiles = new ArrayList<String>();
				errorFiles.addAll(failedPicPaths);
				BaseTools.bimp.setErrorFiles(errorFiles);
				handler.sendEmptyMessage(2);
				closeProgressDialog();
				Toast.makeText(context, "有部分图片上传不成功，可点击单张发送", Toast.LENGTH_LONG).show();
				failedPicPaths.clear();
			}
		}
	}

	/**
	 * 发送动态 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class InsertdynamicTask extends AsyncTask<Object, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(Object... params) {
			// TODO Auto-generated method stub
			try {
				String token = (String) params[0];
				String content = (String) params[1];
				List<StudentClass> scList = (List<StudentClass>) params[2];
				List<Picture> picList = (List<Picture>) params[3];

				StringBuffer sBuffer = new StringBuffer();
				for (StudentClass studentClass : studentClasseList) {
					if (!studentClass.getReceivers().equals("")) {
						sBuffer.append(studentClass.getReceivers());
						sBuffer.append(",");
					}
				}
				String receivers = sBuffer.toString();
				if (receivers.endsWith(",")) {
					receivers = receivers.substring(0, receivers.length() - 1);
				}
				sBuffer = new StringBuffer();
				for (Picture picture : picList) {
					sBuffer.append(picture.toString());
					sBuffer.append(",");
				}
				String pictures = sBuffer.toString();
				if (pictures.endsWith(",")) {
					pictures = pictures.substring(0, pictures.length());
				}

				result = AppContext.getApp().insertdynamic(token, content,
						receivers, pictures);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null != result) {
				if (result.getStatus().equals("0")) {

					UIHelper.ToastMessage(context, "您已成功发送动态");

					// 将图片数量和uploadedPicList置为初始值
					uploadImgCount = 0;
					uploadedPicList.clear();
					// 清楚图片目录 路径
					BaseTools.bimp.setMax(0);
					BaseTools.bimp.getDrr().clear();
					BaseTools.bimp.getBmp().clear();
					FileUtils.deleteDir();// 清除上传图片文件夹

					Intent intent = getIntent();
					intent.putExtra("sendStatus", 1);
					SendDynamicActivity.this.setResult(
							RESULT_CODE_INSERTDYNAMIC, intent);
					SendDynamicActivity.this.finish();
					backAnim();
				} else {
					// 将图片数量和uploadedPicList置为初始值
//					uploadImgCount = 0;
//					uploadedPicList.clear();
					noScrollgridview.setAdapter(adapter);
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			} else {
				// 将图片数量和uploadedPicList置为初始值
//				uploadImgCount = 0;
//				uploadedPicList.clear();
				noScrollgridview.setAdapter(adapter);
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}
		}
	}
}

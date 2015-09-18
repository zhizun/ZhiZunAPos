package com.zhizun.pos.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhizun.pos.R;
import com.ch.epw.bean.send.PersoninfoSend;
import com.ch.epw.task.CheckUserNameTask;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.ModifyAvatarDialog;
import com.ch.epw.view.MyInputDialog;
import com.ch.epw.view.SelectSexDialog;
import com.ch.epw.view.TitleBarView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.PersonInfo;
import com.zhizun.pos.bean.PersonInfoDetail;
import com.zhizun.pos.bean.Picture;
import com.zhizun.pos.bean.PictureList;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.widget.circularimage.CircularImage;

/**
 * 我的益培 个人资料 教师端 创建人：李林中 创建日期：2014-12-15 上午10:08:08 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyepeiPersonInfoActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	Context context;
	Dialog dialog;
	CircularImage iv_myepei_personinfo_pic;// 头像image
	RelativeLayout rl_myepei_personinfo_head;// 头像
	RelativeLayout rl_myepei_personinfo_username;// 用户名
	RelativeLayout rl_myepei_personinfo_name;// 姓名
	RelativeLayout rl_myepei_personinfo_sex;// 性别
	RelativeLayout rl_myepei_personinfo_addr;// 地址

	TextView tv_myepei_personinfo_username;// 用户名
	TextView tv_myepei_personinfo_name;// 姓名
	TextView tv_myepei_personinfo_sex;// 性别
	TextView tv_myepei_personinfo_addr;// 地址
	PictureList pictureList;
	protected List<Picture> uploadedPicList = new ArrayList<Picture>();
	// 图片上传参数
	private static String localTempImageFileName = "";
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	private static final String TAG = MyepeiPersonInfoActivity.class.getName();
	Boolean ftpImgResult;// 图片上传是否成功
	Result result;
	Bitmap bsave;
	String fileName;
	private static String clientImgUrlPre = "";

	PersonInfoDetail personInfoDetail;
	PersonInfo personInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myepei_personinfo);
		context = this;
		personInfoDetail = (PersonInfoDetail) getIntent().getSerializableExtra(
				"personInfoDetail");
		options = Options.getListOptions();
		initView();

	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_techer);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);

		titleBarView.setTitleText(R.string.title_text_mypersoninfo);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		titleBarView.getRightTextView().setText(R.string.text_save);
		titleBarView.setBarRightOnclickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						PersoninfoSend personinfoSend = new PersoninfoSend(
								personInfo.getPhotoPath(),
								tv_myepei_personinfo_username.getText()
										.toString().trim(),
								tv_myepei_personinfo_name.getText().toString()
										.trim(), StringUtils
										.getSexNameNum(tv_myepei_personinfo_sex
												.getText().toString().trim()),
								personInfo.getAddr(), personInfo.getProvince(),
								personInfo.getCity(), personInfo.getCounty(),
								personInfo.getLat(), personInfo.getLng());
						new UpdatePersoninfoTask().execute(personinfoSend);
					}
				});
		iv_myepei_personinfo_pic = (CircularImage) this
				.findViewById(R.id.iv_myepei_personinfo_pic);

		rl_myepei_personinfo_head = (RelativeLayout) this
				.findViewById(R.id.rl_myepei_personinfo_head);
		rl_myepei_personinfo_username = (RelativeLayout) this
				.findViewById(R.id.rl_myepei_personinfo_username);
		rl_myepei_personinfo_name = (RelativeLayout) this
				.findViewById(R.id.rl_myepei_personinfo_name);
		rl_myepei_personinfo_sex = (RelativeLayout) this
				.findViewById(R.id.rl_myepei_personinfo_sex);
		rl_myepei_personinfo_addr = (RelativeLayout) this
				.findViewById(R.id.rl_myepei_personinfo_addr);

		tv_myepei_personinfo_username = (TextView) findViewById(R.id.tv_myepei_personinfo_username);
		tv_myepei_personinfo_name = (TextView) findViewById(R.id.tv_myepei_personinfo_name);
		tv_myepei_personinfo_sex = (TextView) findViewById(R.id.tv_myepei_personinfo_sex);
		tv_myepei_personinfo_addr = (TextView) findViewById(R.id.tv_myepei_personinfo_addr);
		initData();
	}

	private void initData() {
		// new GetInviteCountTask().execute(AppContext.getApp().getToken());
		rl_myepei_personinfo_head.setOnClickListener(this);
		rl_myepei_personinfo_addr.setOnClickListener(this);

		rl_myepei_personinfo_name.setOnClickListener(this);
		rl_myepei_personinfo_sex.setOnClickListener(this);
		personInfo = personInfoDetail.getPersonInfo();
		if (personInfo.getUserName() != null
				&& !personInfo.getUserName().equals("")) {
			tv_myepei_personinfo_username.setText(personInfo.getUserName());// 用户名
			View userNameRightArrow = rl_myepei_personinfo_username
					.findViewById(R.id.iv_arrow_right_username);
			if (userNameRightArrow != null) {
				userNameRightArrow.setVisibility(View.INVISIBLE);
			}
		} else {
			rl_myepei_personinfo_username.setOnClickListener(this);
		}

		tv_myepei_personinfo_name.setText(personInfo.getName());// 姓名
		tv_myepei_personinfo_sex.setText(StringUtils.getSexName(personInfo
				.getSex()));// 性别
		tv_myepei_personinfo_addr.setText(personInfo.getAddrInfo());// 地址
		showPicture(personInfo.getPhotoPath(), iv_myepei_personinfo_pic,
				options);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_myepei_personinfo_sex:
			dialog = new SelectSexDialog(context, R.style.MyDialog,
					StringUtils.getSexNameNum(tv_myepei_personinfo_sex
							.getText().toString().trim()),
					new SelectSexDialog.LeaveMyDialogListener() {

						@Override
						public void onClick(View view) {
							switch (view.getId()) {
							case R.id.btn_dialog_selectstudent_yes:
								CheckBox cb_dialog_selectsex_female = (CheckBox) dialog
										.findViewById(R.id.cb_dialog_selectsex_female);
								CheckBox cb_dialog_selectsex_man = (CheckBox) dialog
										.findViewById(R.id.cb_dialog_selectsex_man);
								String sex = tv_myepei_personinfo_sex.getText()
										.toString().trim();
								if (cb_dialog_selectsex_female.isChecked()) {
									sex = "女";
								}
								if (cb_dialog_selectsex_man.isChecked()) {
									sex = "男";
								}
								tv_myepei_personinfo_sex.setText(sex);
								dialog.dismiss();
								break;
							case R.id.btn_dialog_selectstudent_no:
								dialog.dismiss();
								break;
							}
						}
					});
			dialog.show();
			break;
		case R.id.rl_myepei_personinfo_name:
			dialog = new MyInputDialog(context, R.style.MyDialog, "编辑姓名",
					tv_myepei_personinfo_name.getText().toString().trim(),
					new MyInputDialog.LeaveMyDialogListener() {

						@Override
						public void onClick(View view) {
							switch (view.getId()) {
							case R.id.btn_dialog_selectstudent_yes:
								EditText eText = (EditText) dialog
										.findViewById(R.id.et_dialog_myinput_content);
								if (eText.getText().toString().trim()
										.equals("")) {
									UIHelper.ToastMessage(context, "姓名不能为空");
									return;
								}
								tv_myepei_personinfo_name.setText(eText
										.getText().toString().trim());
								dialog.dismiss();
								break;
							case R.id.btn_dialog_selectstudent_no:
								dialog.dismiss();
								break;
							}
						}
					});
			dialog.show();
			break;
		case R.id.rl_myepei_personinfo_username:
			dialog = new MyInputDialog(context, R.style.MyDialog, "编辑用户名",
					tv_myepei_personinfo_username.getText().toString().trim(), 20, //20为最大输入长度
					new MyInputDialog.LeaveMyDialogListener() {

						@Override
						public void onClick(View view) {
							switch (view.getId()) {
							case R.id.btn_dialog_selectstudent_yes:
								EditText eText = (EditText) dialog
										.findViewById(R.id.et_dialog_myinput_content);
								String newName = eText.getText().toString()
										.trim();
								if (newName == null || newName.equals("")) {
									UIHelper.ToastMessage(context, "用户名不能为空");
									return;
								}

								// 用户名格式：4-20个字符，可由中英文字母、数字、"-"、"_"组成
								Pattern pattern = Pattern
										.compile("[\\w\\u4e00-\\u9fa5\\-]{4,20}");
								Matcher matcher = pattern.matcher(newName);
								if (!matcher.matches()) {
									UIHelper.ToastMessage(context,
											R.string.username_patten_invalid);
									return;
								}

								new CheckUserNameTask(context,
										tv_myepei_personinfo_username, dialog)
										.execute(newName);
								// tv_myepei_personinfo_username.setText(eText
								// .getText().toString().trim());
								// dialog.dismiss();
								break;
							case R.id.btn_dialog_selectstudent_no:
								dialog.dismiss();
								break;
							}

						}
					});
			dialog.show();
			break;
		case R.id.rl_myepei_personinfo_addr:
			Intent intent_addr = new Intent(context, LocationActivity.class);
			intent_addr.putExtra("personInfo", personInfo);
			startActivityForResult(intent_addr,
					Constant.REQUSTCONDE_PERSONINFO_ADDRESS);
			break;

		case R.id.rl_myepei_personinfo_head:
			// 调用选择那种方式的dialog
			ModifyAvatarDialog modifyAvatarDialog = new ModifyAvatarDialog(
					context) {
				// 选择本地相册
				@Override
				public void doGoToImg() {
					this.dismiss();
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_PICK);
					intent.setType("image/*");
					startActivityForResult(intent, FLAG_CHOOSE_IMG);
				}

				// 选择相机拍照
				@Override
				public void doGoToPhone() {
					this.dismiss();
					String status = Environment.getExternalStorageState();
					if (status.equals(Environment.MEDIA_MOUNTED)) {
						try {
							localTempImageFileName = "";
							// 本地图片地址前缀
							clientImgUrlPre = Environment
									.getExternalStorageDirectory()
									+ File.separator
									+ "epeiwang"
									+ File.separator;
							// 生成图片名字 唯一
							localTempImageFileName = ImageUtils
									.getTempFileName() + ".png";
							String clientImgUrl = clientImgUrlPre
									+ localTempImageFileName;
							Log.i("TAG", "clientImgUrl=" + clientImgUrl);
							File filePath = new File(clientImgUrlPre);
							if (!filePath.exists()) {
								if (!filePath.mkdirs()) {
									return;
								}
							}
							Intent intent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
							File f = new File(filePath, localTempImageFileName);

							Uri u = Uri.fromFile(f);
							intent.putExtra(
									MediaStore.Images.Media.ORIENTATION, 0);
							intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
							startActivityForResult(intent, FLAG_CHOOSE_PHONE);
						} catch (ActivityNotFoundException e) {
							//
						}
					}
				}
			};
			AlignmentSpan span = new AlignmentSpan.Standard(
					Layout.Alignment.ALIGN_CENTER);
			AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(25, true);
			SpannableStringBuilder spannable = new SpannableStringBuilder();
			String dTitle = getString(R.string.gl_choose_title);
			spannable.append(dTitle);
			spannable.setSpan(span, 0, dTitle.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			spannable.setSpan(span_size, 0, dTitle.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			modifyAvatarDialog.setTitle(spannable);
			modifyAvatarDialog.show();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// 地图选取地址之后返回
		if (requestCode == Constant.REQUSTCONDE_PERSONINFO_ADDRESS
				&& resultCode == Constant.REQUSTCONDE_PERSONINFO_ADDRESS) {
			personInfo = (PersonInfo) data.getSerializableExtra("personInfo");
			tv_myepei_personinfo_addr.setText(personInfo.getAddrInfo());
		}
		// 选取图像后的返回处理方法
		if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
			if (data != null) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					Cursor cursor = getContentResolver().query(uri,
							new String[] { MediaStore.Images.Media.DATA },
							null, null, null);
					if (null == cursor) {
						Toast.makeText(context, "图片没找到", 0).show();
						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					cursor.close();
					Log.i(TAG, "path=" + path);
					Intent intent = new Intent(this, CropImageActivity.class);
					intent.putExtra("path", path);
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				} else {
					Log.i(TAG, "path=" + uri.getPath());
					Intent intent = new Intent(this, CropImageActivity.class);
					intent.putExtra("path", uri.getPath());
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				}
			}
		} else if (requestCode == FLAG_CHOOSE_PHONE && resultCode == RESULT_OK) {
			File f = new File(clientImgUrlPre, localTempImageFileName);
			Intent intent = new Intent(this, CropImageActivity.class);
			intent.putExtra("path", f.getAbsolutePath());
			startActivityForResult(intent, FLAG_MODIFY_FINISH);
		} else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
			if (data != null) {
				final String path = data.getStringExtra("path");
				Log.i(TAG, "截取到的图片路径是 = " + path);
				try {
					uploadFile(context, path, URLs.UPLOADIMG_URL);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// // 截取获得文件的名字
				// String[] strs = path.split("/");
				// fileName = strs[strs.length - 1];
				//
				// File file = new File(path);
				// FileInputStream input = null;
				// try {
				// input = new FileInputStream(path);
				// } catch (FileNotFoundException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

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
	public void uploadFile(Context context, final String path, String url)
			throws Exception {
		File file = new File(path);
		if (file.exists() && file.length() > 0) {
			AsyncHttpClient client = new AsyncHttpClient();

			RequestParams params = new RequestParams();
			params.put("uploadfile", file);

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
							Picture picture = pictureList.getPictureList().get(
									0);
							personInfo.setPhotoPath(picture.getPicPath());
							// imageLoader.displayImage(URLs.URL_IMG_API_HOST
							// + picture.getPicPath(),
							// iv_myepei_personinfo_pic, options);
							iv_myepei_personinfo_pic.setImageBitmap(ImageUtils
									.getBitmapByPath(path));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					if (null != responseBody) {
						Log.i("上传图片失败后返回值为=", new String(responseBody));
					}

				}

			});
		} else {
			Toast.makeText(context, "文件不存在", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 保存个人资料 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class UpdatePersoninfoTask extends AsyncTask<PersoninfoSend, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(PersoninfoSend... params) {
			// TODO Auto-generated method stub
			try {

				result = AppContext.getApp().updatePersonList(params[0]);
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
					UIHelper.ToastMessage(context, "更新个人资料成功");
					MyepeiPersonInfoActivity.this
							.setResult(Constant.REQUSTCONDE_PERSONINFO);
					MyepeiPersonInfoActivity.this.finish();
					backAnim();
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			} else {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}
		}
	}
}

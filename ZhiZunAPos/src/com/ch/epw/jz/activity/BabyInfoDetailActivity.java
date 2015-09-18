package com.ch.epw.jz.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.annotation.SuppressLint;
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

import com.ch.epw.bean.send.BabyinfoSend;
import com.ch.epw.bean.send.PersoninfoSend;
import com.ch.epw.js.activity.SendNoticePatternActivity;
import com.ch.epw.jz.activity.BabyInfoListActivity;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.DateTimePickDialogUtil;
import com.ch.epw.view.ModifyAvatarDialog;
import com.ch.epw.view.MyInputDialog;
import com.ch.epw.view.SelectSexDialog;
import com.ch.epw.view.TitleBarView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.CropImageActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.BabyInfoDetail;
import com.zhizun.pos.bean.InterestPri;
import com.zhizun.pos.bean.InterestSec;
import com.zhizun.pos.bean.PersonInfo;
import com.zhizun.pos.bean.PersonInfoDetail;
import com.zhizun.pos.bean.Picture;
import com.zhizun.pos.bean.PictureList;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.widget.circularimage.CircularImage;

public class BabyInfoDetailActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	Context context;
	Dialog dialog;

	RelativeLayout rl_myepei_personinfo_head;// 头像
	RelativeLayout rl_myepei_personinfo_username;// 姓名
	RelativeLayout rl_myepei_personinfo_name;// 乳名
	RelativeLayout rl_myepei_personinfo_sex;// 性别
	RelativeLayout rl_myepei_personinfo_addr;// 出生日期
	RelativeLayout rl_myepei_personinfo_babyinfo;// 兴趣爱好

	CircularImage iv_myepei_personinfo_pic;// 头像image
	TextView tv_myepei_personinfo_username;// 姓名
	TextView tv_myepei_personinfo_name;// 乳名
	TextView tv_myepei_personinfo_sex;// 性别
	TextView tv_myepei_personinfo_addr;// 出生日期
	TextView tv_myepei_babyinfo_detail_cats;// 兴趣爱好

	PictureList pictureList;
	protected List<Picture> uploadedPicList = new ArrayList<Picture>();
	// 图片上传参数
	private static String localTempImageFileName = "";
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	private static final String TAG = BabyInfoDetailActivity.class.getName();
	Boolean ftpImgResult;// 图片上传是否成功
	Result result;
	Bitmap bsave;
	String fileName;
	private static String clientImgUrlPre = "";

	BabyInfoDetail babyInfoDetail;
	private StringBuffer catString;
	private StringBuffer catlistStringBuffer;
	String strCatString = "";
	String strCatListString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myepei_babyinfo_detail);
		context = this;

		babyInfoDetail = (BabyInfoDetail) getIntent().getSerializableExtra(
				"babyInfoDetail");
		options = Options.getListOptions();
		initView();

	}

	@SuppressLint("ResourceAsColor")
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_techer);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);

		titleBarView.setTitleText(R.string.title_text_editbabyinfo);
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

						BabyinfoSend personinfoSend = new BabyinfoSend(
								AppContext.getApp().getUserLoginSharedPre()
										.getUserInfo().getUserId(),
								babyInfoDetail.getPhotoPath(),
								tv_myepei_personinfo_name.getText().toString()
										.trim(), tv_myepei_personinfo_username
										.getText().toString().trim(),

								StringUtils
										.getSexNameNum(tv_myepei_personinfo_sex
												.getText().toString().trim()),

								tv_myepei_personinfo_addr.getText().toString()
										.trim(), strCatString, babyInfoDetail
										.getChildId());
						new UpdateBabyinfoTask().execute(personinfoSend);
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
		rl_myepei_personinfo_babyinfo = (RelativeLayout) this
				.findViewById(R.id.rl_myepei_personinfo_babyinfo);
		rl_myepei_personinfo_babyinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CatSelectActivity.class);
				intent.putExtra("babyInfoDetail", babyInfoDetail);
				startActivityForResult(intent,
						Constant.REQUSTCONDE_BABYINFO_CATLIST);
				intoAnim();
			}
		});

		tv_myepei_personinfo_username = (TextView) findViewById(R.id.tv_myepei_personinfo_username);
		tv_myepei_personinfo_name = (TextView) findViewById(R.id.tv_myepei_personinfo_name);
		tv_myepei_personinfo_sex = (TextView) findViewById(R.id.tv_myepei_personinfo_sex);
		tv_myepei_personinfo_addr = (TextView) findViewById(R.id.tv_myepei_personinfo_addr);
		tv_myepei_babyinfo_detail_cats = (TextView) findViewById(R.id.tv_myepei_babyinfo_detail_cats);

		initData();
	}

	private void initData() {
		// new GetInviteCountTask().execute(AppContext.getApp().getToken());
		rl_myepei_personinfo_head.setOnClickListener(this);
		rl_myepei_personinfo_addr.setOnClickListener(this);
		rl_myepei_personinfo_username.setOnClickListener(this);
		rl_myepei_personinfo_name.setOnClickListener(this);
		rl_myepei_personinfo_sex.setOnClickListener(this);

		tv_myepei_personinfo_username.setText(babyInfoDetail.getName());
		tv_myepei_personinfo_name.setText(babyInfoDetail.getNickName());//
		tv_myepei_personinfo_sex.setText(StringUtils.getSexName(babyInfoDetail
				.getSex()));// 性别
		// 出生日期
		tv_myepei_personinfo_addr.setText(babyInfoDetail.getBirthDate());//
		// StringBuffer buffString = new StringBuffer();
		// for (InterestPri interestPri : babyInfoDetail.getInterestPriList()) {
		// for (InterestSec interestSec : interestPri.getInterestSecList()) {
		//
		// buffString.append(interestSec.getItemName() + ",");
		//
		// }
		// }
		//
		// String buffStr = buffString.toString();
		// if (buffStr.endsWith(",")) {
		// buffStr = buffStr.substring(0, buffStr.length() - 1);
		// }
		// tv_myepei_babyinfo_detail_cats.setText(buffStr);

		catString = new StringBuffer();
		catlistStringBuffer = new StringBuffer();
		List<InterestPri> listPri = babyInfoDetail.getInterestPriList();
		if (null != listPri && listPri.size() > 0) {
			for (InterestPri interestPri : listPri) {
				for (InterestSec interestSec : interestPri.getInterestSecList()) {

					catString.append(interestPri.getCatId() + "|"
							+ interestSec.getItemId() + ",");
					catlistStringBuffer.append(interestSec.getItemName() + ",");

				}
			}

			// 要往服务器提交的字符串
			strCatString = catString.toString();
			if (strCatString.endsWith(",")) {
				strCatString = strCatString.substring(0,
						strCatString.length() - 1);
			}

			Log.i(TAG, "strCatString=" + strCatString);
			// 返回后显示的兴趣爱好字符串

			strCatListString = catlistStringBuffer.toString();
			if (strCatListString.endsWith(",")) {
				strCatListString = strCatListString.substring(0,
						strCatListString.length() - 1);
			}
		} else {
			strCatListString = "";
		}

		Log.i(TAG, "strCatListString=" + strCatListString);
		tv_myepei_babyinfo_detail_cats.setText(strCatListString);

		showPicture(babyInfoDetail.getPhotoPath(), iv_myepei_personinfo_pic,
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
			dialog = new MyInputDialog(context, R.style.MyDialog, "编辑乳名",
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
									UIHelper.ToastMessage(context, "乳名不能为空");
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
			dialog = new MyInputDialog(context, R.style.MyDialog, "编辑姓名",
					tv_myepei_personinfo_username.getText().toString().trim(),
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
								tv_myepei_personinfo_username.setText(eText
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
		case R.id.rl_myepei_personinfo_addr:
			dateTimeDialog();
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
		if (requestCode == Constant.REQUSTCONDE_BABYINFO_CATLIST
				&& resultCode == Constant.REQUSTCONDE_BABYINFO_CATLIST) {
			catString = new StringBuffer();
			catlistStringBuffer = new StringBuffer();
			ArrayList<InterestPri> listPri = (ArrayList<InterestPri>) data
					.getSerializableExtra("listPri");
			if (null != listPri && listPri.size() > 0) {
				for (InterestPri interestPri : listPri) {
					for (InterestSec interestSec : interestPri
							.getInterestSecList()) {
						if (interestSec.getIsSelected()) {
							catString.append(interestPri.getCatId() + "|"
									+ interestSec.getItemId() + ",");
							catlistStringBuffer.append(interestSec
									.getItemName() + ",");
						}
					}
				}

				// 要往服务器提交的字符串
				strCatString = catString.toString();
				if (strCatString.endsWith(",")) {
					strCatString = strCatString.substring(0,
							strCatString.length() - 1);
				}

				Log.i(TAG, "strCatString=" + strCatString);
				// 返回后显示的兴趣爱好字符串

				strCatListString = catlistStringBuffer.toString();
				if (strCatListString.endsWith(",")) {
					strCatListString = strCatListString.substring(0,
							strCatListString.length() - 1);
				}
			} else {
				strCatListString = "";
			}

			Log.i(TAG, "strCatListString=" + strCatListString);
			tv_myepei_babyinfo_detail_cats.setText(strCatListString);
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
							babyInfoDetail.setPhotoPath(picture.getPicPath());
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
	class UpdateBabyinfoTask extends AsyncTask<BabyinfoSend, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(BabyinfoSend... params) {
			// TODO Auto-generated method stub
			try {
				result = AppContext.getApp().updateBabyList(params[0]);
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
					UIHelper.ToastMessage(context, "更新宝宝资料成功");
					BabyInfoDetailActivity.this.setResult(
							Constant.REQUSTCONDE_BABYINFO_CATLIST, getIntent());
					BabyInfoDetailActivity.this.finish();
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

	private void dateTimeDialog() {
		String birthDate = "";
		if (tv_myepei_personinfo_addr.getText() != null
				&& tv_myepei_personinfo_addr.getText().length() >= Constant.DATETIME_PICK_YYYYMMDD) {
			birthDate = tv_myepei_personinfo_addr.getText().toString().trim()
					+ " 00:00";
		}
		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				BabyInfoDetailActivity.this, birthDate);
		dateTimePicKDialog
				.dateTimePicKDialog(tv_myepei_personinfo_addr,
						Constant.DATETIME_PICK_YYYYMMDD,
						Constant.DATETIME_PICK_NOTHING);
	}
}

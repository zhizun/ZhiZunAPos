package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zhizun.pos.R;
import com.zhizun.pos.adapter.SuggestOrgDrrAdpater;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.SuggestName;
import com.zhizun.pos.bean.SuggestNameWrapper;
import com.ch.epw.task.GetInputSuggestTask;
import com.ch.epw.task.GetSuggestCourseTask;
import com.ch.epw.task.TaskCallBack;

public class SuggestInputWindow extends PopupWindow implements PopupWindow.OnDismissListener {

	private Context mContext;
	private String searchType;
	private String keyWord;
	private String orgId;
	private final String savedKeyWord; // 保存建议窗口弹出时的关键字
	private OnSuggestionCheckedListener onSuggestionCheckedListener;
	List<SuggestName> listItems=new ArrayList<SuggestName>();
	boolean suggestionAccepted = false; // 是否接受输入建议，PopWindow关闭时判断是否点击了某条建议项
	int lastWinHeight = 99999999; // 在主窗口OnGlobalLayoutListener只记录窗口的高度。当高度发生变化时，判断是否是由于关闭输入法

	EditText et_search_text;

	// 如果窗口当前高度小于lastWinHeight，调用输入法关闭事件，关闭当前窗口

	public SuggestInputWindow(Context context, String searchType,
			String keyWord, String orgId) {
		super(context);
		mContext = context;
		this.searchType = searchType;
		this.keyWord = keyWord;
		// 搜索课程时使用传入的orgId，搜索机构时直接置空
		if ("0".equals(this.searchType)) {
			this.orgId = orgId;
		} else {
			this.orgId = "";
		}
		savedKeyWord = keyWord;
		setOnDismissListener(this);
		initView();
	}

	private void initView() {
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.suggest_input_window_layout, null);
		setContentView(view);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setFocusable(true);
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setBackgroundDrawable(AppContext.getApp().getResources().getDrawable(R.color.transparent));

		et_search_text = (EditText) view
				.findViewById(R.id.et_search_text);
		et_search_text.postDelayed(new Runnable() {
	        @Override  
	        public void run() {  
	        	InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);  
	            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
	        }  
	    }, 0);  

		final ListView ll_suggestions_list = (ListView) view
				.findViewById(R.id.ll_suggestions_list);

		ll_suggestions_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (listItems != null && listItems.size() > position
						&& onSuggestionCheckedListener != null) {
					suggestionAccepted = true;
					onSuggestionCheckedListener.OnSuggestionChecked(listItems
							.get(position));
				}
				SuggestInputWindow.this.dismiss();
			}
		});

		final TaskCallBack getSuggestCallBack = new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean result) {
				SuggestNameWrapper suggestNameWrapper = (SuggestNameWrapper) result;
				if (suggestNameWrapper != null
						&& "0".equals(suggestNameWrapper.getStatus())) {
					listItems = suggestNameWrapper.getSuggestNameList();
					String[] suggestNames = suggestNameWrapper
							.getSuggestNames();
					if (suggestNames != null) {
						ll_suggestions_list
								.setAdapter(new SuggestOrgDrrAdpater(mContext, listItems));
					}
				}
			}
		};
		
		et_search_text.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if (!keyWord.equals(s.toString())) {
					keyWord = s.toString();
					if (!TextUtils.isEmpty(keyWord)) {
						if ("1".equals(searchType) || !TextUtils.isEmpty(orgId)) {
							new GetInputSuggestTask(mContext,
									getSuggestCallBack)
							.execute(searchType, keyWord, orgId);
						}
					}
				}
			}
		});

		// 如果初始的关键字不为空，调用搜索建议提示
		if (!TextUtils.isEmpty(keyWord)) {
			et_search_text.setText(keyWord);
			et_search_text.setSelection(keyWord.length());
			if ("1".equals(searchType) || !TextUtils.isEmpty(orgId)) {
				new GetInputSuggestTask(mContext, getSuggestCallBack).execute(
						searchType, keyWord, orgId);
			}
		} else {
			// 如果调用的是搜索课程，并且机构ID不为空，则调用单独的search接口获得课程输入建议
			if ("0".equals(searchType) && !TextUtils.isEmpty(orgId)) {
				new GetSuggestCourseTask(mContext, getSuggestCallBack)
						.execute(orgId);
			}
		}

		view.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						if (lastWinHeight >= view.getHeight()) {
							lastWinHeight = view.getHeight();
						}
						// 窗口变小，输入法关闭，调用窗口关闭事件
						else {
							SuggestInputWindow.this.dismiss();
						}
					}

				});
	}
	
	public void setSuggestTextHint(String hint) {
		et_search_text.setHint(hint);
	}

	@Override
	public void onDismiss() {
		if (!suggestionAccepted && onSuggestionCheckedListener != null) {
			SuggestName suggest = new SuggestName();
			suggest.setSearchType(searchType);
			suggest.setOrgName(keyWord);
			suggest.setCourName(keyWord);
			onSuggestionCheckedListener.OnSuggestionChecked(suggest);
		}
	}

	public static interface OnSuggestionCheckedListener {
		public void OnSuggestionChecked(SuggestName suggest);
	}

	public OnSuggestionCheckedListener getOnSuggestionCheckedListener() {
		return onSuggestionCheckedListener;
	}

	public void setOnSuggestionCheckedListener(
			OnSuggestionCheckedListener onSuggestionCheckedListener) {
		this.onSuggestionCheckedListener = onSuggestionCheckedListener;
	}

}

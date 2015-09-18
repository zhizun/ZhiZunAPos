package com.ch.epw.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.ch.epw.js.activity.MyepeiTeacherActivity;
import com.ch.epw.js.activity.NavigationTeacherActivity;
import com.zhizun.pos.activity.FriendsCircleActivity;
import com.zhizun.pos.activity.LoginActivity;
import com.zhizun.pos.activity.MainActivity;
import com.zhizun.pos.activity.MarketingactivitiesActivity;

public class Constant {

	/**
	 * 获取列表的条数
	 */
	public static Integer LOADDATACOUNT = 10;
	/**
	 * 初始化城市
	 */
	public static String INITCITY = "郑州";

	/**
	 * 初始化城市ID
	 */
	public static Integer INITCITYID = 2;
	/**
	 * 初始化城市列表版本号
	 */
	public static Integer INITCITYVERSION = 1;
	/**
	 * 初始化商圈列表版本号
	 */
	public static Integer INITBUSINESSAREAVERSION = 1;
	/**
	 * 初始化区域列表版本号
	 */
	public static Integer INITDISTRICTSION = 1;
	/**
	 * 上传图片限制张数
	 */
	public static Integer UPLOAD_PHOTO_COUNT = 9;
	/**
	 * Tab选项卡的文字
	 */
	public static String mTextviewArray[] = { "首页", "朋友圈", "互动", "我的" };

	/**
	 * 每一个Tab界面 家长 SearchNearbyHotsActivity.class,//附近
	 */ 
	public static Class mParentTabClassArray[] = {
			MarketingactivitiesActivity.class,FriendsCircleActivity.class, MainActivity.class,
			MyepeiTeacherActivity.class };
	/**
	 * 每一个Tab界面 教师 管理员等 , SearchNearbyHotsActivity.class
	 */
	public static Class mTeacherTabClassArray[] = {
			MarketingactivitiesActivity.class,FriendsCircleActivity.class, NavigationTeacherActivity.class,
			MyepeiTeacherActivity.class };
	/**
	 * 每一个Tab界面 未登录 SearchNearbyHotsActivity.class,
	 */
	public static Class mLoginTabClassArray[] = {
			MarketingactivitiesActivity.class, FriendsCircleActivity.class, LoginActivity.class,
			LoginActivity.class };
	/**
	 * 系统默认图片列表
	 */
	public static String SYS_INIT_PIC_LIST = "default_photo,default_logo,default_image,";

	public static int SMS_VALID_CODE_LEN = 6; // 默认的验证码长度

	public static long SMS_SEND_INTERVAL = 120000;// 验证码发送间隔时间 ，单位毫秒

	// 评论区最长输入内容
	public static int COMMENT_MAX_LEN = 140;

	public static String USER_PWD_PSEUDO_CODE = "c`;F(zQ}w{=D7&9>&Pw2*q11=4v#5[LzD2|n;^J@?0q7Sc6c+";

	/**
	 * 邀请接受状态
	 */
	public static String INVITESTATUS_UNTREATED = "0";// 未处理

	public static String INVITESTATUS_ACCEPTED = "1";// 已接受

	public static String INVITESTATUS_REFUSE = "2";// 已拒绝

	// 发送邀请状态码
	public static int REQUEST_COLDE = 1; // 发送邀请请求码
	public static int RESULT_ACCEPT_COLDE = 1; // 结果返回码 接受邀请
	public static int RESULT_REFUSE_COLDE = 2; // 结果返回码 拒绝邀请
	// 跳转来自 哪个activity
	public static String FROM_ACTIVITY_MARKETINGACTIVITIESACTIVITY = "MarketingactivitiesActivity"; // 跳转来自MarketingactivitiesActivity
	/**
	 * 邀请单类型 0-邀请家长
	 */
	public static final String INVITE_TYPE_TO_PARENT = "0";
	/**
	 * 邀请单类型 1-邀请老师
	 */
	public static final String INVITE_TYPE_TO_TEACHER = "1";

	/**
	 * 邀请单类型 1-邀请老师
	 */
	public static final String TOKEN_EXPIRED_CODE = "1003";

	// 动态类型，0：在校动态，1：通知公告，2：家庭作业，3：考勤记录，
	// 4：在校点评，5：教学计划，6：班级食谱，7：家长心声，8：我的邀请， 9:我的收藏 99：最新回复92:课程评价分享

	public static final int FSI_TYPE_ZXDT = 0;
	public static final int FSI_TYPE_TZGG = 1;
	public static final int FSI_TYPE_JTZY = 2;
	public static final int FSI_TYPE_KQJL = 3;
	public static final int FSI_TYPE_ZXDP = 4;
	public static final int FSI_TYPE_JXJH = 5;
	public static final int FSI_TYPE_BJSP = 6;
	public static final int FSI_TYPE_JZXS = 7;
	public static final int FSI_TYPE_WDYQ = 8;
	public static final int FSI_TYPE_WDSC = 9;
	public static final int FSI_TYPE_ZXHF = 99;
	public static final int FSI_TYPE_FXYJ = 50;
	public static final int FSI_TYPE_TJYJ = 51;
	public static final int FSI_TYPE_YHHD = 52;
	public static final int FSI_TYPE_MSG = 96;
	public static final int FSI_TYPE_NEWMSG = 97;
	public static final int FSI_TYPE_COUR = 92;

	public static String COMMNETTYPE_ZXDT = String.valueOf(FSI_TYPE_ZXDT);
	public static String COMMNETTYPE_TZGG = String.valueOf(FSI_TYPE_TZGG);
	public static String COMMNETTYPE_JTZY = String.valueOf(FSI_TYPE_JTZY);
	public static String COMMNETTYPE_KQJL = String.valueOf(FSI_TYPE_KQJL);
	public static String COMMNETTYPE_ZXDP = String.valueOf(FSI_TYPE_ZXDP);
	public static String COMMNETTYPE_JXJH = String.valueOf(FSI_TYPE_JXJH);
	public static String COMMNETTYPE_BJSP = String.valueOf(FSI_TYPE_BJSP);
	public static String COMMNETTYPE_JZXS = String.valueOf(FSI_TYPE_JZXS);
	public static String COMMNETTYPE_WDYQ = String.valueOf(FSI_TYPE_WDYQ);
	public static String COMMNETTYPE_WDSC = String.valueOf(FSI_TYPE_WDSC);
	public static String COMMNETTYPE_ZXHF = String.valueOf(FSI_TYPE_ZXHF);
	public static String COMMNETTYPE_FXYJ = String.valueOf(FSI_TYPE_FXYJ);
	public static String COMMNETTYPE_TJYJ = String.valueOf(FSI_TYPE_TJYJ);
	public static String COMMNETTYPE_YHHD = String.valueOf(FSI_TYPE_YHHD);
	public static String COMMNETTYPE_MSG = String.valueOf(FSI_TYPE_MSG);
	public static String COMMNETTYPE_NEWMSG = String.valueOf(FSI_TYPE_NEWMSG);
	public static String COMMNETTYPE_COUR = String.valueOf(FSI_TYPE_COUR);

	// 注册Jpush别名时指定的tags
	public static Set<String> PUSH_ALIAS_TAGS = new HashSet<String>(Arrays.asList(new String[]{
			COMMNETTYPE_MSG,
			COMMNETTYPE_NEWMSG
	}));
	// 上传图片成功 发送通知
	public final static int UPLOAD_IMG_SEND = 1;//
	// 上传图片 时候 发送通知
	public final static int UPLOAD_IMAGE_ERROR = 6;//
	// 删除上传失败图片
	public final static int DELETE_IMAGE_ERROR = 8;//
	// 老师端发送动态等页面发送人默认显示个数（多于此数以则 加上 等）
	public static int FSI_RECVERS_FOR_SHORT_LEN = 2;
	// 分享类型 0分享有奖，1 推荐有奖2优惠活动
	public static final int EVENT_TYPE_SHARE_PRIZE = 0;
	public static final int EVENT_TYPE_RECOMMEND_PRIZE = 1;
	public static final int EVENT_TYPE_RECOMMEND_YHHD = 2;

	// 是否
	public static String TYPE_YES = "1";
	public static String TYPE_NO = "0";
	
	// 是否加入机构 0未加入 1已加入
	public static final int IS_JOIN_ORG_NO = 0;
	public static final int IS_JOIN_ORG_YES = 1;

	// 性别 0：女，1：男
	public static String SEX_MALE = "1";
	public static String SEX_FEMALE = "0";

	// 通知发送模式 resultcode
	public static int NOTICE_SENDPATTERN_IMMEDIATELY = 0; // 立即发送
	public static int NOTICE_SENDPATTERN_TIMING = 1; // 定时发送

	// 通知发送状态
	public static String NOTICE_SEND_STATUS_WAIT = "0"; // 待发送
	public static String NOTICE_SEND_STATUS_SENT = "1"; // 已发送

	// 通知发送模式 requestcode
	public static int SEND_NOTICE_PARTTERN = 2;

	// 选择学生 requestcode
	public static int SEND_NOTICE_SELCET_SUTDENT = 1;
	public static int SEND_NOTICE_SELCET_TEACHER = 102;
	// 选择代理老师requestcode
	public static int MYAGENT_SELCET_TEACHER = 102;
	// 发送模式 requestcode
	public static int SEND_NOTICE_SELCET_TEMPLETE = 3;

	// 通知已读1 未读0
	public static String NOTICE_READ = "1";
	public static String NOTICE_UNREAD = "0";
	// 是否收藏 1是 0否
	public static String COLLECTION_YES = "1";
	public static String COLLECTION_NO = "0";
	// 接收状态
	public static String STATUS_ALL = "all";// 全部
	public static String STATUS_TASK = "task";// 等待发送
	public static String STATUS_SENT = "sent";// 已发送

	// 家长心声 接收状态 all：全部，noRead：未浏览，nosend：未回复
	public static String VOICE_STATUS_ALL = "all";// 全部
	public static String VOICE_STATUS_NOREAD = "noRead";// 等待发送
	public static String VOICE_STATUS_NOSEND = "nosend";// 已发送

	// 找回密码 验证身份类型，mobile：手机，email：邮箱
	public static String VALIDTYPE_MOBILE = "mobile";
	public static String VALIDTYPE_EMAIL = "email";//

	// 验证类型，REG：注册，FIND_PWD：找回密码，BIND：绑定手机号，
	// CHANGE_PWD：修改密码，REBIND：更换绑定，AUTHENTICATION：用户认证

	public static String VALIDTYPE_REG = "REG";
	public static String VALIDTYPE_FIND_PWD = "FIND_PWD";
	public static String VALIDTYPE_BIND = "BIND";
	public static String VALIDTYPE_LOGIN = "LOGIN";
	public static String VALIDTYPE_CHANGE_PWD = "CHANGE_PWD";
	public static String VALIDTYPE_REBIND = "REBIND";
	public static String VALIDTYPE_AUTHENTICATION = "AUTHENTICATION";
	// activity 请求码和返回码
	public static int REQUSTCONDE_BINDPHONE = 1;// 绑定手机号码

	public static int REQUSTCONDE_PERSONINFO = 1; // 个人资料修改后返回码
	public static int REQUSTCONDE_PERSONINFO_ADDRESS = 1; // 个人资料地址选择后返回码
	public static int REQUSTCONDE_BABYINFO_CATLIST = 10; // 兴趣爱好选择后返回码
	public static int REQUSTCONDE_BABYINFO_ORGCOUNT = 7; // 兴趣爱好选择后返回码
	public static int REQUSTCONDE_SELECTCLASS = 107; // 选班
	public static int REQUSTCONDE_INDEXTO_LOGIN = 107; // 跳转到登录页面请求码

	// 机构角色定义
	public final static String ORG_ROLE_TYPE_NOLOGIN = "-2"; // 未登录
	public final static String ORG_ROLE_TYPE_NOROLE = "-1"; // 无角色 已登录
	public final static String ORG_ROLE_TYPE_PARENT = "0"; // 机构角色类型-家长
	public final static String ORG_ROLE_TYPE_TEACHER = "1"; // 机构角色类型-老师
	public final static String ORG_ROLE_TYPE_ADMIN = "2"; // 机构角色类型-管理员
	public final static String ORG_ROLE_TYPE_SUPADMIN = "3";// 机构角色类型-超级管理员
	// 角色区分
	public final static int ORG_ROLE_NOROLE = -1; // 无角色
	public final static int ORG_ROLE_PARENT = 0; // 机构角色类型-家长
	public final static int ORG_ROLE_TEACHERORORG = 1; // 机构角色类型-老师 或者管理员 超级管理员
	// 考勤结果状态
	public final static String ATTENCE_STATUS_ATTEND = "1";
	public final static String ATTENCE_STATUS_ABSENT = "0";

	// dateTimePicKDialog选取时间长度参数
	public final static int DATETIME_PICK_NOTHING = 0;
	public final static int DATETIME_PICK_YYYYMMDD = 10;
	public final static int DATETIME_PICK_HHMMSS = 8;
	public final static int DATETIME_PICK_HHMM = 5;

	// SharedPreferences 消息推送配置的名字
	public final static String MSG_PUSH_STATUS = "msg_push_status";//
	// 是否接受推送消息的SharedPreferences名字
	// SharedPreferences的参数状态值
	public final static String MSG_PUSH_STATUS_YES = "0";// 接受
	public final static String MSG_PUSH_STATUS_NO = "1";// 不接收

	// 客户端类型，0：Android；1：iOS
	public final static String UPDATE_CLIENTTYPE_ANDROID = "0";//
	// 是否强制升级，0：否；1：是
	public final static String UPDATE_ISFORCEUPGRADE_NO = "0";//
	public final static String UPDATE_ISFORCEUPGRADE_YES = "1";//
	// 上传图片 时候 发送通知
	public final static int UPLOAD_IMG_SEND_WHAT = 2;//
	// 家长端红点通知默认当前选项不显示红点
	public static int POSTION_NUM = 0;
	// 代理状态 0 进行中 1 已取消 2 已过期
	public final static String MYAGENT_AGENTING = "0";//
	public final static String MYAGENT_CANCEL = "1";//
	public final static String MYAGENT_END = "2";//

	// 分享平台
	public final static String SMP_TYPE_WECHAT = "0"; // 微信
	public final static String SMP_TYPE_WECHAT_MOMENTS = "1"; // 微信朋友圈
	public final static String SMP_TYPE_WECHAT_FAVORITE = "2"; // 微信收藏
	public final static String SMP_TYPE_QQ = "3"; // QQ
	public final static String SMP_TYPE_QQ_ZONE = "4"; // QQ空间
	public final static String SMP_TYPE_WEIBO_SINA = "5"; // 新浪微博

	public final static String SMP_TYPE_OTHER = "99";
	/**
	 * 备注类型
	 */
	public static String KQ_CD = "0";// 迟到
	public static String KQ_QJ = "1";// 请假
	public static String KQ_QT = "2";// 其他
	// 考勤添加备注广播通知
	public static String REMARKS = "remarks";//
	// 课程搜索，选择条件后发送广播提交
	public static String COURSE = "course";//
	/**
	 * 有奖活动
	 */
	public static String SHARE_AWARD = "0";// 0 分享有奖
	public static String RECOMMEND_AWARD = "1";// 1 推荐有奖
	public static String PRIZED_PREFERENTIAL = "2";// 2 优惠活动
	// 学生变动类型，1：休学；2：转班：3：退班；4：复学；5：毕业
	public final static String STUDENT_CHANGE_TYPE_XX = "1"; //
	public final static String STUDENT_CHANGE_TYPE_ZB = "2"; //
	public final static String STUDENT_CHANGE_TYPE_TB = "3";//
	public final static String STUDENT_CHANGE_TYPE_FX = "4";//
	public final static String STUDENT_CHANGE_TYPE_BY = "5";//
	
	public final static String SORT_NODE_TYPE_NEAR_CODE = "near"; // 查找附近
	public final static String SORT_NODE_TYPE_ORDER_CODE = "order"; // 排序
	public final static String SORT_NODE_TYPE_COUNTY_CODE = "county"; // 区县代码
	public final static String SORT_NODE_TYPE_CATEGORY_CODE = "category";// 课程、机构分类代码
	
	/**
	 * 机构、课程搜索时查找附近距离条件 目标代码 SortTreeNode 
	 * 
	 * [0] itemId 
	 * [1] itemName 
	 * [2] parentItemId
	 * [3] type
	 * [4] checked 0:false; 1:true
	 */
	public final static String[][] nearbyAreaSettings = { 
			{ "0", "附近", "", SORT_NODE_TYPE_NEAR_CODE, "1" },
			{ "1000", "1公里内", "0", SORT_NODE_TYPE_NEAR_CODE, "0" },
			{ "3000", "3公里内", "0", SORT_NODE_TYPE_NEAR_CODE, "1" },
			{ "5000", "5公里内", "0", SORT_NODE_TYPE_NEAR_CODE, "0" },
			{ "10000", "10公里内", "0", SORT_NODE_TYPE_NEAR_CODE, "0" },
			{ "10000000", "不限", "0", SORT_NODE_TYPE_NEAR_CODE, "0" }
	};
	
	/**
	 * 机构、课程搜索时查找附近距离条件 目标代码 SortTreeNode 
	 * 
	 * [0] itemId 
	 * [1] itemName 
	 * [2] parentItemId
	 * [3] type
	 * [4] checked 0:false; 1:true
	 */
	public final static String[][] coureListOrderOptionSettings = { 
			{ "distance", "距离优先", "", SORT_NODE_TYPE_ORDER_CODE, "1" },
			{ "commentNum", "评论最多", "", SORT_NODE_TYPE_ORDER_CODE, "0" }
	};

	/**
	 * 机构搜索时候排序参数 目标代码 SortTreeNode [0] itemId [1] itemName [2] parentItemId [3]
	 * type
	 */
	public final static String[][] orgListOrderOptionSettings = {
		{ "viewNum",
			"按浏览数", "", SORT_NODE_TYPE_ORDER_CODE }
	};
	
	/**
	 * 课程评价项与评价名称参数 目标代码 Rating [0] remarkItemId [1] remarkItemName [2] rating
	 */
	public final static String[][] courseRatingOptionSettings = { 
			{ "markAvg", "综合评分", "0" }, { "markA", "教学环境", "0" },
			{ "markB", "教学水平", "0" }, { "markC", "学习效果", "0" }
	};
	public final static String imageLog="/epeiwang/static/theme/images/share_epw_logo.png";
	
	public final static String courseDownladApp="http://www.epeiwang.com/epeiwang/invite_friend.html";
}

package com.ch.epw.utils;

import java.io.Serializable;
import java.net.URLEncoder;

import com.zhizun.pos.bean.HostConfig;
import com.zhizun.pos.bean.Photo;

/**
 * 接口URL实体类 
 * ===================================================
 */
public class URLs implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String EPEIWANG_SERVER = HostConfig.epeiwang_server;// 测试
	public final static String EPEIWANG_IMG_SERVER = HostConfig.epeiwang_img_server;// 图片服务器测试
	public final static String EPEIWANG_URL = HostConfig.epeiwang_url; // 益培网域名地址
	
	public final static String PROCOTOL = "http://";
	public final static String URL_API_HOST = PROCOTOL + EPEIWANG_SERVER;

	public final static String URL_IMG_API_HOST = PROCOTOL
			+ EPEIWANG_IMG_SERVER + "/";

	public final static String URL_SHARE_PRE = PROCOTOL + EPEIWANG_URL + "/"
			+ "epeiwang/share.view?id=";
	
	// 当连上无root的电脑时候的分享url
	// public final static String URL_SHARE_PRE = PROCOTOL + HOST_EPEIWANG
	// + "/share.view?id=";

	// 得到登录接口的地址
	public final static String GETLOGINVERIFY_URL = URL_API_HOST
			+ "/j_bsp_security_check/token";
	// 得到我的在校动态 家长端
	public final static String GETMYDYNAMIC_URL = URL_API_HOST
			+ "/homedynamicappdatacmd.cmd?method=getMyDynamic";
	// 得到我的在校动态 教师端
	public final static String GETTEACHERDYNAMIC_URL = URL_API_HOST
			+ "/dynamicdeployapp.cmd?method=getTeacherDynamic";
	// 回复动态或者评论
	public final static String REPLYCOMMENTS_URL = URL_API_HOST
			+ "/fsicommentdata.cmd?method=addComment";

	// 获得老师班级
	public final static String GETTEACHERCLASS_URL = URL_API_HOST
			+ "/dynamicdeployapp.cmd?method=getTeacherClass";

	// 老师发布动态
	public final static String INSERTDYNAMIC_URL = URL_API_HOST
			+ "/dynamicdeployapp.cmd?method=insertdynamic";
	// 老师删除动态
	public final static String DELETEDYNAMIC_URL = URL_API_HOST
			+ "/dynamicdeployapp.cmd?method=deleteDynamic";
	// 删除评论
	public final static String DELETECOMMENTS_URL = URL_API_HOST
			+ "/fsicommentdata.cmd?method=delComment";

	// 添加或取消收藏
	public final static String FAV_URL = URL_API_HOST
			+ "/fsifavdata.cmd?method=fav";
	// 添加或取消赞一条动态
	public final static String LIKE_URL = URL_API_HOST
			+ "/fsilikedata.cmd?method=like";

	// 获得我的邀请数量
	public final static String GETINVITECOUNT_URL = URL_API_HOST
			+ "/fsiorginvitedata.cmd?method=getInviteCount";
	// 获得我的邀请
	public final static String GETUSERINVITE_URL = URL_API_HOST
			+ "/fsiorginvitedata.cmd?method=getUserInvite";
	// 获得我发出的邀请
	public final static String GETUSERSENDINVITE_URL = URL_API_HOST
			+ "/fsiorginvitedata.cmd?method=getUserSendInvite";
	// 接受邀请
	public final static String ACCEPTINVITE_URL = URL_API_HOST
			+ "/fsiorginvitedata.cmd?method=accept";
	// 拒绝邀请
	public final static String REJECT_URL = URL_API_HOST
			+ "/fsiorginvitedata.cmd?method=reject";
	// 获取当前用户拥有的宝宝
	public final static String GETUSERCHILDS_URL = URL_API_HOST
			+ "/bmchilddata.cmd?method=getUserChilds";

	// 最新回复
	public final static String GETDYNAMICNEWCOMMENTS_URL = URL_API_HOST
			+ "/dynamicdeployapp.cmd?method=getDynamicNewComments";
	// 历史回复
	public final static String GETDYNAMICOLDCOMMENTS_URL = URL_API_HOST
			+ "/dynamicdeployapp.cmd?method=getDynamicOldComments";
	// 回复详情
	public final static String GETFSIDYNAMICANDCOMMENTS_URL = URL_API_HOST
			+ "/dynamicdeployapp.cmd?method=getFsiDynamicAndComments";

	// 获得班级和班级里的学生
	public final static String GETUSERCLASSANDSTUDENT_URL = URL_API_HOST
			+ "/omclassdata.cmd?method=getUserClassAndStudent";
	// 上传图片
	public final static String UPLOADIMG_URL = URL_API_HOST + "/upload";
	// 获取当前登录用户拥有的角色
	public final static String GETROLES_URL = URL_API_HOST
			+ "/sysswitchroledata.cmd?method=getRoles";
	// 切换角色
	public final static String SWITCHROLE_URL = URL_API_HOST
			+ "/sysswitchroledata.cmd?method=switchRole";

	/**
	 * 通知公告接口
	 * 
	 */
	// 发送通知
	public final static String SENDNOTICE_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=sendNotice";
	// 新增模版
	public final static String SAVETMPL_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=saveTmpl";
	// 发件箱
	public final static String QUERYOUTBOXLIST_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=queryOutBoxList";
	// 查看已发通知明细
	public final static String GETNOTICEDETAIL_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=getNoticeDetail";
	// 收件箱
	public final static String QUERYINBOXLIST_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=queryInBoxList";
	// 通知公告模板
	public final static String QUERYNOTICETPLLIST_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=queryNoticeTplList";
	// 通知公告标签列表
	public final static String GETTPLTAGLIST_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=getTplTagList";
	// 删除通知公告模板
	public final static String DELNOTICETPL_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=delNoticeTpl";
	// 删除已发通知
	public final static String DELSENTNOTICE_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=delSentNotice";
	// 删除已收通知
	public final static String DELRECVNOTICE_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=delRecvNotice";
	// 设置已读
	public final static String SETNOTICEREAD_URL = URL_API_HOST
			+ "/fsinoticedata.cmd?method=setNoticeRead";
	// 退出登录
	public final static String LOGOUT_URL = URL_API_HOST
			+ "/logoutdata.cmd?method=logout";

	/**
	 * 家庭作业接口
	 * 
	 */
	// 获取当前登录用户收到的家庭作业（老师端）
	public final static String GETHOMEWORKLIST_URL = URL_API_HOST
			+ "/fsihomeworkdata.cmd?method=getHomeworkList";
	// 删除一条家庭作业 教师才有的功能
	public final static String DELETEHOMEWORK_URL = URL_API_HOST
			+ "/fsihomeworkdata.cmd?method=deleteHomework";
	// 布置一条家庭作业
	public final static String ASSIGNHOMEWORK_URL = URL_API_HOST
			+ "/fsihomeworkdata.cmd?method=assignHomework";
	// 获取当前登录用户收到的家庭作业（家长端）
	public final static String GETUSERHOMEWORKLIST_URL = URL_API_HOST
			+ "/fsihomeworkdata.cmd?method=getUserHomeworkList";

	/**
	 * 通用最新回复接口 家长端
	 * 
	 */
	// 获取当前最新回复列表（家长端）
	public final static String GETLATESTCOMMENTREPLYLIST_URL = URL_API_HOST
			+ "/fsicommentdata.cmd?method=getLatestCommentReplyList";
	// 获取当前最新回复数量（家长端）
	public final static String GETLATESTCOMMENTREPLYNUM_URL = URL_API_HOST
			+ "/fsicommentdata.cmd?method=getLatestCommentReplyNum";
	// 获取当前最新回复详情（家长端）
	public final static String GETFSIDATADETAIL_URL = URL_API_HOST
			+ "/fsicommentdata.cmd?method=getFsiDataDetail";

	/**
	 * 通用最新回复接口 家长端
	 * 
	 */
	// 获取考勤记录列表（教师端）
	public final static String GETATTENCELISTTEACHERBYDAY_URL = URL_API_HOST
			+ "/fsiattencedata.cmd?method=getAttenceListTeacherByDay";
	// 删除考勤记录（教师端）
	public final static String DELATTENCE_URL = URL_API_HOST
			+ "/fsiattencedata.cmd?method=delAttence";
	// 编辑 新增 考勤记录（教师端）
	public final static String SAVEATTENCE_URL = URL_API_HOST
			+ "/fsiattencedata.cmd?method=saveAttence";
	// 家长查看考勤
	public final static String GETATTENCELISTPARENT_URL = URL_API_HOST
			+ "/fsiattencedata.cmd?method=getAttenceListParent";
	// 查看考勤明细
	public final static String GETATTENCEDETAIL_URL = URL_API_HOST
			+ "/fsiattencedata.cmd?method=getAttenceDetail";
	/**
	 * 在校点评
	 * 
	 */
	// 删除点评（教师端）
	public final static String DELETEREMARK_URL = URL_API_HOST
			+ "/fsiremarkdata.cmd?method=deleteRemark";
	// 获取点评列表（教师端）
	public final static String GETREMARKLIST_URL = URL_API_HOST
			+ "/fsiremarkdata.cmd?method=getRemarkList";
	// 发送点评（教师端）
	public final static String PUBLISH_URL = URL_API_HOST
			+ "/fsiremarkdata.cmd?method=publish";
	// 获取点评模板（教师端）
	public final static String GETRATINGTPLLIST_URL = URL_API_HOST
			+ "/fsiremarkdata.cmd?method=getRatingTplList";
	// 获取当前登录用户收到的在校点评（家长端）
	public final static String GETUSERREMARKLIST_URL = URL_API_HOST
			+ "/fsiremarkdata.cmd?method=getUserRemarkList";
	/**
	 * 家长心声
	 * 
	 */
	// 获取心声列表（教师端）
	public final static String GETVOICETEACHERLIST_URL = URL_API_HOST
			+ "/fsiVoicedata.cmd?method=getVoiceTeacherList";
	// 获取心声列表（家长端）
	public final static String GETVOICELIST_URL = URL_API_HOST
			+ "/fsiVoicedata.cmd?method=getVoiceList";
	// 删除（家长端）
	public final static String DELETEVOICE_URL = URL_API_HOST
			+ "/fsiVoicedata.cmd?method=deletevoice";
	// 新增（家长端）
	public final static String INSERTVOICE_URL = URL_API_HOST
			+ "/fsiVoicedata.cmd?method=insertvoice";
	// 获取教师列表
	public final static String GETUSERTOTEACHERS_URL = URL_API_HOST
			+ "/teacherdata.cmd?method=getUserToTeachers";
	// 获取教师列表
	public final static String GETUSERCLASSANDTEACHER_URL = URL_API_HOST
			+ "/teacherdata.cmd?method=getUserClassAndTeacher";
	// 获取代理教师列表
	public final static String GETDELEGATIONCLASSANDTEACHER_URL = URL_API_HOST
			+ "/teacherdata.cmd?method=getDelegationClassAndTeacher";
	// 获取本机构所有班级
	public final static String GETORGCLASSES_URL = URL_API_HOST
			+ "/omclassdata.cmd?method=getOrgClasses";
	// 保存学员变更
	public final static String SAVESTUCHGSTATE_URL = URL_API_HOST
			+ "/omstudentdata.cmd?method=saveStuChgState";

	/**
	 * 获取我的收藏列表
	 */
	public static final String GETFAVORLIST_URL = URL_API_HOST
			+ "/fsifavdata.cmd?method=getFavList";

	/**
	 * 忘记密码 找回密码
	 * 
	 */
	// 找回密码-验证用户
	public final static String VALIDUSER_URL = URL_API_HOST
			+ "/findPwdData.view?method=validUser";
	// 找回密码-发送验证码
	public final static String SENDCODE_URL = URL_API_HOST
			+ "/findPwdData.view?method=sendCode";
	// 找回密码-校验验证码
	public final static String VALIDCODE_URL = URL_API_HOST
			+ "/findPwdData.view?method=validCode";
	// 找回密码-重置密码
	public final static String RESETPWD_URL = URL_API_HOST
			+ "/findPwdData.view?method=resetPwd";
	// 检验用户名是否有效
	public final static String CHECKUSERNAME_URL = URL_API_HOST
			+ "/userValidate.cmd?method=checkUsername";
	/**
	 * 注册
	 * 
	 */
	// 发送短信验证码
	public final static String SENDSMSCODE_URL = URL_API_HOST
			+ "/validcode.view?method=sendSmsCode";
	// 校验短信验证码
	public final static String VALIDATESMSCODE_URL = URL_API_HOST
			+ "/validcode.view?method=validateSmsCode";
	// 注册
	public final static String REGISTER_URL = URL_API_HOST
			+ "/registerdata.view?method=register";
	/**
	 * 绑定手机
	 * 
	 */
	// 绑定手机
	public final static String BIND_URL = URL_API_HOST
			+ "/bindmobiledata.cmd?method=bind";
	// 发送短信验证码
	public final static String SENDSMSCODE_BIND_URL = URL_API_HOST
			+ "/validcode.cmd?method=sendSmsCode";
	// 发送登陆短信验证
		public final static String SENDSMSCODE_LOGIN_URL = URL_API_HOST
				+ "/validcode.view?method=sendSmsCode";
	// 验证密码
	public final static String CHECKPASS_URL = URL_API_HOST
			+ "/userValidate.cmd?method=checkPass";
	// 验证短信验证码
	public final static String VALIDATESMSCODE_BIND_URL = URL_API_HOST
			+ "/validcode.cmd?method=validateSmsCode";
	// 验证用户
	public final static String VALIDATE_URL = URL_API_HOST
			+ "/securitydata.cmd?method=validate";
	// 重新绑定手机号
	public final static String REBIND_URL = URL_API_HOST
			+ "/bindmobiledata.cmd?method=rebind";

	/**
	 * 个人资料
	 * 
	 */
	// 获取当前用户的基本资料（家长）,“我的”整合接口，总的
	public final static String GETPERSONLIST_URL = URL_API_HOST
			+ "/bmUserData.cmd?method=getUserInfo";
	// 编辑保存用户基本资料 （家长）
	public final static String UPDATEPERSONLIST_URL = URL_API_HOST
			+ "/personalParentsData.cmd?method=UpdatePersonList";
	// 获取当前登录用户的所有宝宝信息
	public final static String GETBABYNAMELIST_URL = URL_API_HOST
			+ "/babyParentsData.cmd?method=getBabyNameList";
	// 新增一个宝宝信息
	public final static String INSERTBABYLIST_URL = URL_API_HOST
			+ "/babyParentsData.cmd?method=insertBabyList";
	// 删除一个宝宝信息
	public final static String DELETEBABY_URL = URL_API_HOST
			+ "/babyParentsData.cmd?method=deleteBaby";
	// 修改宝宝信息
	public final static String UPDATEBABYLIST_URL = URL_API_HOST
			+ "/babyParentsData.cmd?method=updateBabyList";
	// 宝宝信息明细
	public final static String GETUPDATEBABY_URL = URL_API_HOST
			+ "/babyParentsData.cmd?method=getupdateBaby";
	// 获取宝宝兴趣爱好
	public final static String GETCAT_URL = URL_API_HOST
			+ "/coursecat.cmd?method=getCat";
	// 获取宝宝兴趣爱好
	public final static String EXITORG_URL = URL_API_HOST
			+ "/omStuFamDataCmd.cmd?method=exitOrg";

	// 我的代理信息
	public final static String GETUSERDELEGATION_URL = URL_API_HOST
			+ "/delegationdata.cmd?method=getUserDelegation";
	// 我的代理的历史信息
	public final static String QUERYDELEGATION_URL = URL_API_HOST
			+ "/delegationdata.cmd?method=queryDelegation";
	// 取消代理
	public final static String CANCELDELEGATION_URL = URL_API_HOST
			+ "/delegationdata.cmd?method=cancelDelegation";
	// 增加代理
	public final static String SAVEDELEGATION_URL = URL_API_HOST
			+ "/delegationdata.cmd?method=saveDelegation";

	/**
	 * 修改密码
	 * 
	 */
	// 修改密码
	public final static String UPDATE_URL = URL_API_HOST
			+ "/changepwddata.cmd?method=update";

	/**
	 * 获得教师和所属机构 班级
	 * 
	 */
	// 获得教师和所属机构 班级
	public final static String GETCHILDCLASSANDTEACHER_URL = URL_API_HOST
			+ "/omclassdata.cmd?method=getChildClassAndTeacher";
	/**
	 * 教师端家校互动未读数量提示
	 * 
	 */
	// 教师端家校互动未读数量提示
	public final static String GETUNREADRECVNUM_URL = URL_API_HOST
			+ "/fsirecvdata.cmd?method=getUnReadRecvNum";
	/**
	 * 获取客户端版本信息
	 * 
	 */
	// 获取客户端版本信息
	public final static String GETCLIENTVERSION_URL = URL_API_HOST
			+ "/clientVerInfo.view?method=getClientVersion";

	/**
	 * 获取机构信息接口
	 */
	public final static String ORGINFO_URL = URL_API_HOST
			+ "/omorgdata.cmd?method=detail";

	/**
	 * 分享接口
	 */
	public final static String SHARE_URL = URL_API_HOST
			+ "/fsisharedata.cmd?method=addShare";
	
	/**
	 * 分享接口
	 */
	public final static String CANCEL_SHARE_URL = URL_API_HOST
			+ "/fsisharedata.cmd?method=cancel";
	
	/**
	 * 有奖活动
	 */
	public final static String PRIZED_URL = URL_API_HOST
			+ "/mktgEventData.cmd?method=query";
	/**
	 * 有奖活动 分享有奖 参与详情页面
	 */

	public final static String PRIZED_PARTICIPATION_URL = URL_API_HOST
			+ "/mktgEventData.cmd?method=getPartakeDetail";
	/**
	 * 有奖活动 推荐有奖 参与详情页面
	 */
	public final static String PRIZED_RECOMMEND_URL = URL_API_HOST
			+ "/mktgEventData.cmd?method=getRecommendDetail";

	/**
	 * 营销活动首页 列表
	 */
	public final static String PRIZED_INDEX_MARKETING_URL = URL_API_HOST
			+ "/mktgEventDataController.view?method=query";

	/**
	 * 获取热门活动
	 */
	public final static String PRIZED_INDEX_HOT_URL = URL_API_HOST
//			+ "/mktgEventDataController.view?method=hot";
			+ "/indexData.view?method=init";

	/**
	 * 有奖活动
	 */
	public final static String EVENTID_URL = URL_API_HOST
			+ "/mktgEventDataController.view?method=detail";
	
	/**
	 * 课程,机构搜索列表
	 */
	public final static String COURSE_URL = URL_API_HOST
			+ "/pubSearch.view?method=search";
	/**
	 * 立即报名
	 */
	public final static String COURSE__APPLY_URL = URL_API_HOST
			+ "/enrollData.cmd?method=signUp";
	
	/**
	 * 课程搜索列表
	 */
	public final static String COURSE__DETAIL_URL = URL_API_HOST
			+ "/courseDetailData.view?method=detail";
	
	/**
	 * 机构介绍详情
	 */
	public final static String ORG_INTRO_URL = URL_API_HOST
			+ "/omorgctler.view?method=getOrgIntro";
	/**
	 * 上传通讯录
	 */
	public final static String UPLOADER_PHONE_URL = URL_API_HOST
			+ "/bmUserRelationData.cmd?method=upload";
	/**
	 * 获取朋友圈评论列表
	 */
	public final static String FRIENDS_COMMENT_URL = URL_API_HOST
			+ "/omCommentData.cmd?method=friendsComment";
	/**
	 * 我的评价
	 */
	public final static String MY_FRIENDS_COMMENT_URL = URL_API_HOST
			+ "/omCommentData.cmd?method=getMyCommentList";
	/**
	 * 附近评价
	 */
	public final static String MY_FRIENDS_FUJIN_URL = URL_API_HOST
			+ "/omCommentDataController.view?method=nearByOrgList";
	/**
	 * 感谢
	 */
	public final static String FRIENDS_HELPFUL_URL = URL_API_HOST
			+ "/omComHelpfulDataCmd.cmd?method=helpful";
	/**
	 * 我的系统消息
	 */
	public final static String MYMESSAGE_URL = URL_API_HOST+"/sysmsgData.cmd?method=getSysMsgList";
	/**
	 * 我的消息
	 */
	public final static String MY_MYMESSAGE_URL = URL_API_HOST+"/sysmsgData.cmd?method=getMyMsgList";
	
	/**
	 * 系统消息详情页(按照receiveId查询)
	 */
	public final static String MY_SYSMSG_BY_RECVID_URL = URL_API_HOST+"/sysmsgData.cmd?method=detail";
	
	/**
	 * 系统消息详情页(按照MsgId查询)
	 */
	public final static String MY_SYSMSG_BY_MSGID_URL = URL_API_HOST+"/sysmsgData.cmd?method=getDetail";

	/**
	 * 
	 * 通知 系统消息详情页
	 */
	public final static String MY_SYSTEM_DETAIL_URL = URL_API_HOST+"/sysmsgGetDetail";
	/**
	 * 系统消息详情页 列表 分页加载
	 */
	public final static String MY_SYSTEM_DETAIL_LIST_URL = URL_API_HOST+"/sysmsgData.cmd?method=getSysMsgReplyList";
	/**
	 * 回复系统消息
	 */
	public final static String SEND__MESSAGE_URL = URL_API_HOST
			+ "/sysmsgData.cmd?method=sendReply";
	/**
	 * 是否有新的消息，获取消息数
	 */
	public final static String SYSTEM__MESSAGE_GETUNREADMSGNUM_URL = URL_API_HOST
			+ "/sysmsgData.cmd?method=getUnReadMsgNum";

	/**
	 * 获取查询机构和课程名称的搜索建议
	 */
	public final static String GET_SUGGEST_NAME_URL = URL_API_HOST
			+ "/pubSearch.view?method=suggest";

	/**
	 * 课程咨询数+1
	 */
	public static final String ADD_COUESE_COUNSEL_NUM_URL = URL_API_HOST
			+ "/omCourseController.view?method=setCounselNum";

	/**
	 * 发表课程评价
	 */
	public static final String SEND_COUESE_COMMENT_URL = URL_API_HOST
			+ "/omCommentData.cmd?method=postComment";

	/**
	 * 根据课程ID和机构ID查询课程评价
	 */

	public static final String GET_COURSE_COMMENT_URL = URL_API_HOST
			+ "/omCommentDataController.view?method=courseCommentList";

	/**
	 * 删除课程评价
	 */
	public static String DELETE_COUESE_COMMENT_URL = URL_API_HOST
			+ "/omCommentData.cmd?method=deleteComment";

	/**
	 * 对URL进行格式处理
	 * 
	 * @param path
	 * @return
	 */
	private final static String formatURL(String path) {
		if (path.startsWith("http://") || path.startsWith("https://"))
			return path;
		return "http://" + URLEncoder.encode(path);
	}

	/**
	 * 对图片URL进行格式处理
	 * 
	 * @param path
	 * @return
	 */
	public final static String formatImgURL(String uri) {
		if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
			uri = URLs.URL_IMG_API_HOST + uri;
		}
		return uri;
	}

	/**
	 * 对图片URL进行格式处理
	 * 
	 * @param path
	 * @return
	 */
	public final static String formatImgURL(Photo photo) {
		String imgUri = "";
		if (photo != null) {
			String imgPath = photo.getThumbPath() + "/"
					+ photo.getThumbSaveName();
			if (!imgPath.startsWith("http://")
					&& !imgPath.startsWith("https://")) {
				imgUri = URLs.URL_IMG_API_HOST + imgPath;
			}
		}

		return imgUri;
	}

}

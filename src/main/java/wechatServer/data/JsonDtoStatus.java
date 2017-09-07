package wechatServer.data;
/**
 * 
 * @author yansong.wang
 *
 */
public interface JsonDtoStatus {
	// 未登陆
	public static final Integer NO_LOGGIN = -1;
	public static final String NO_LOGGIN_INFO = "请登录后重试";

	// 成功
	public static final Integer OK = 200;
	public static final String OK_INFO = "成功";

	// 失败
	public static final Integer FAILE = 201;
	public static final String FAILE_INFO = "失败";

	// 缺少参数的错误
	public static final Integer PARAM_MISSING = 101;
	public static final String PARAM_MISSING_INFO = "缺少参数{0}";

	public static final Integer INVALID_PARAM = 102;
	public static final String INVALID_PARAM_INFO = "参数{0}值错误";

	// 分页
	public static final Integer PAGE_UNKNOW_OPER = 110;
	public static final String PAGE_UNKNOW_OPER_INFO = "未知的翻页类型";

	public static final Integer PAGE_UNKNOW_TARGET = 111;
	public static final String PAGE_UNKNOW_TARGET_INFO = "无法获得分页页数信息";

	// 未归类的错误
	public static final Integer UNCLASSIFIED_ERROR = 998;

	// 未知错误
	public static final Integer ERROR_UNKNOW = 500;
	public static final String ERROR_UNKNOW_INFO = "服务内部错误";

}

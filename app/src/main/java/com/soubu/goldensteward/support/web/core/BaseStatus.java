package com.soubu.goldensteward.support.web.core;

/**
 * 网络状态常量
 * <p>
 * 作者：余天然 on 2016/12/7 下午4:43
 */
public class BaseStatus {

    public static final int USER_is_LOGOUT = -1;//用户被登出;

    public static final int PARAMS_VERIFY_FAILED = 101;//接口参数验证失败;

    public static final int DATABASE_QUERY_FAILED = 102;//数据库查询失败;

    public static final int SUCCESS = 200;//接口请求成功;

    public static final int HEADERS_PARSE_FAILED = 400;//headers解析失败;

    public static final int VERSION_ILLEGAL = 401;//版本号非法;

    public static final int System_Type_ILLEGAL = 402;//系统类型非法;

    public static final int DEvice_ILLEGAL = 403;//设备号非法;

    public static final int TOKEN_ILLEGAL = 404;//token非法;

    public static final int POST_PARAMS_ILLEGAL = 405;//提交参数非法;

    public static final int RESPONSE_STRUCTURE_FAILED = 406;//接口输出结构错误;
}

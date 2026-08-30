package com.zch.shortlink.admin.common.convention.errorcode;

/**
 * 基础错误码定义
 */
public enum BaseErrorCode implements IErrorCode {

    //调用我的构造器存值，然后最后一个常量的结尾是分号
    //枚举的每个常量就是一个枚举实例
    //有构造器 BaseErrorCode(String code, String message)，所以每个常量必须按这个参数顺序传值
    // ========== 一级宏观错误码 客户端错误 ==========
    CLIENT_ERROR("A000001", "用户端错误"),

    // ========== 二级宏观错误码 用户注册错误 ==========
    USER_REGISTER_ERROR("A000100", "用户注册错误"),
    USER_NAME_VERIFY_ERROR("A000110", "用户名校验失败"),
    USER_NAME_EXIST_ERROR("A000111", "用户名已存在"),
    USER_NAME_SENSITIVE_ERROR("A000112", "用户名包含敏感词"),
    USER_NAME_SPECIAL_CHARACTER_ERROR("A000113", "用户名包含特殊字符"),
    PASSWORD_VERIFY_ERROR("A000120", "密码校验失败"),
    PASSWORD_SHORT_ERROR("A000121", "密码长度不够"),
    PHONE_VERIFY_ERROR("A000151", "手机格式校验失败"),

    // ========== 二级宏观错误码 系统请求缺少幂等Token ==========
    IDEMPOTENT_TOKEN_NULL_ERROR("A000200", "幂等Token为空"),
    IDEMPOTENT_TOKEN_DELETE_ERROR("A000201", "幂等Token已被使用或失效"),

    // ========== 一级宏观错误码 系统执行出错 ==========
    SERVICE_ERROR("B000001", "系统执行出错"),
    // ========== 二级宏观错误码 系统执行超时 ==========
    SERVICE_TIMEOUT_ERROR("B000100", "系统执行超时"),

    // ========== 一级宏观错误码 调用第三方服务出错 ==========
    REMOTE_ERROR("C000001", "调用第三方服务出错");

    //每个常量各自的值
    private final String code;

    private final String message;

    //构造器
    BaseErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /*
    * 你方法名手滑写成 code()→codes()，或者参数写岔了——没有 @Override 时，
    * 编译器认为你定义了个新方法，接口的 code() 依然没实现，编译报错在很远的别处，难找
    * 加了 @Override，编译器立刻指着这行说“接口里没有这号方法”，错误当场暴露
    * */
    //@Override，表示这个方法是在重写/实现父类或接口里的方法
    //IErrorCode的方法体实现
    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
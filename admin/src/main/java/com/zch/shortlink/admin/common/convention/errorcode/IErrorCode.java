package com.zch.shortlink.admin.common.convention.errorcode;

/**
 * 平台错误码
 */
//interface 接口的关键字，统一错误形式，错误码＋错误信息
//只要，那个包内有import com.zch.shortlink.admin.common.convention.errorcode.IErrorCode
//就可以
public interface IErrorCode {

    /**
     * 错误码
     */
    String code();

    /**
     * 错误信息
     */
    String message();
}
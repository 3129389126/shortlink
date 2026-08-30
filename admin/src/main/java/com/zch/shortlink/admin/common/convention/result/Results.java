package com.zch.shortlink.admin.common.convention.result;



import com.zch.shortlink.admin.common.convention.errorcode.BaseErrorCode;
import com.zch.shortlink.admin.common.convention.exception.AbstractException;

import java.util.Optional;

/**
 * 全局返回对象构造器
 */
public final class Results {

    /**
     * 构造成功响应
     */
    //本次无数据返回
    //public static 静态方法，不依赖任何对象，直接用类名调用
    public static Result<Void> success() {
        //首先调用无参的构造器，现在里面的参数都是null，然后在这个新对象上调用setCode方法
        return new Result<Void>()
                .setCode(Result.SUCCESS_CODE);
    }

    /**
     * 构造带返回数据的成功响应
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(Result.SUCCESS_CODE)
                .setData(data);
    }

    /**
     * 构建服务端失败响应
     */

    public static Result<Void> failure() {
        return new Result<Void>()
                .setCode(BaseErrorCode.SERVICE_ERROR.code())
                .setMessage(BaseErrorCode.SERVICE_ERROR.message());
    }


    //Optional.ofNullable(x)   // 装盒：x 非 null → 盒里装着 x；x 是 null → 盒里明确标记"空"
    //盒.orElse(备胎)          // 拆盒：有值吐值，是空吐备胎
    /**
     * 通过 {@link AbstractException} 构建失败响应
     */
    //参数是抽象父类类型——所以传 ClientException、ServiceException、RemoteException 都能装进来（多态）
    public static Result<Void> failure(AbstractException abstractException) {
        //能取到错误码就用取到的；取不到（null）就默认按‘系统服务端错误’处理
        String errorCode = Optional.ofNullable(abstractException.getErrorCode())
                .orElse(BaseErrorCode.SERVICE_ERROR.code());
        String errorMessage = Optional.ofNullable(abstractException.getErrorMessage())
                .orElse(BaseErrorCode.SERVICE_ERROR.message());
        return new Result<Void>()
                .setCode(errorCode)
                .setMessage(errorMessage);
    }

    /**
     * 通过 errorCode、errorMessage 构建失败响应
     */
    //它是 failure 的字符串重载，让业务代码在预期内的失败场景下不抛异常、直接组装标准失败响应
    public static Result<Void> failure(String errorCode, String errorMessage) {
        return new Result<Void>()
                .setCode(errorCode)
                .setMessage(errorMessage);
    }
}
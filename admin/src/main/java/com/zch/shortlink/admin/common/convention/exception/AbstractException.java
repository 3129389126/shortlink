package com.zch.shortlink.admin.common.convention.exception;


import com.zch.shortlink.admin.common.convention.errorcode.IErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Optional;

//@see的意思是另请参阅
/**
 * 抽象项目中三类异常体系，客户端异常、服务端异常以及远程服务调用异常
 * 三类业务异常（客户端/服务端/远程调用）的公共父类
 * @see ClientException
 * @see ServiceException
 * @see RemoteException
 */
@Getter
public abstract class AbstractException extends RuntimeException {

    /*
     *选运行时异常 = 业务代码可以随手 throw new ClientException(...) 而不污染方法签名，
     * 最后由全局异常处理器统一兜底。这是 Spring 项目的标准玩法。
     * */
    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        //errorCode 参数是接口类型，.code() 调的是实际传进来的那个枚举。多态的案例
        this.errorCode = errorCode.code();
        //调用方传了 message 就用传的，没传，就是传入null，就兜底用错误码自带的默认 message
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.message());
    }
}
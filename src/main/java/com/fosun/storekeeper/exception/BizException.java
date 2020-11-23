package com.fosun.storekeeper.exception;

import com.fosun.storekeeper.common.ResponseCodeEnum;

/**
 * 业务异常类
 * @version 
 * @author hengjb 2017年7月23日下午5:13:10
 * @since 1.8
 */
public class BizException extends Exception {

    private static final long serialVersionUID = 7805907487081899539L;

    /**
     * 默认系统异常
     */
    private String code = ResponseCodeEnum.SYS_ERROR.getCode();
    

    public BizException() {
        super();
    }

    /**
     * 生成业务异常
     * @param code 异常提示信息code
     */
    public BizException(String code) {
        super();
        this.code = code;
    }

    /**
     * 生成业务异常
     * @param code 错误码
     * @param msg 错误信息
     */
    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 生成业务异常
     * @param code 错误码
     * @param msg 错误信息
     * @param cause 被包装错误类型
     */
    public BizException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return ResponseCodeEnum.getResultInfoEnumByCode(code).getMessage();
    }

}

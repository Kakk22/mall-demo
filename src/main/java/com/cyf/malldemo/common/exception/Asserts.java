package com.cyf.malldemo.common.exception;

import com.cyf.malldemo.common.IErrorCode;

/**断言处理类，用于抛出各种api异常
 * @author by cyf
 * @date 2020/6/24.
 */
public class Asserts {
    public static void fail(String message){
        throw new ApiException(message);
    }
    public static void fail(IErrorCode iErrorCode){
        throw new ApiException(iErrorCode);
    }
}

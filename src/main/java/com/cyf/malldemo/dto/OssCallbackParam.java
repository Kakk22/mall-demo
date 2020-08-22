package com.cyf.malldemo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**oss上传成功后的回调参数
 * @author by cyf
 * @date 2020/8/22.
 */
@Getter
@Setter
public class OssCallbackParam {

    @ApiModelProperty("请求的回调地址")
    private  String callbackUrl;
    @ApiModelProperty("回调传入request中的参数")
    private String callbackBody;
    @ApiModelProperty("回调时传入参数的格式，比如表单的提交形式")
    private String callbackBodyType;
}

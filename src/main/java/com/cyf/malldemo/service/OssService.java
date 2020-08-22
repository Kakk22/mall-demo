package com.cyf.malldemo.service;

import com.cyf.malldemo.dto.OssCallbackResult;
import com.cyf.malldemo.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/** oss 上传管理service
 * @author by cyf
 * @date 2020/8/22.
 */
public interface OssService {

    /**
     * oss 上传策略生成
     * @return
     */
    OssPolicyResult policy();

    /**
     * oss 上传成功回调
     * @param request
     * @return
     */
    OssCallbackResult callback(HttpServletRequest request);
}

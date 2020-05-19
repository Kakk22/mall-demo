package com.cyf.malldemo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author by cyf
 * @date 2020/5/16.
 * Mybatis配置类
 */

@Configuration
@MapperScan({"com.cyf.malldemo.mbg.mapper","com.cyf.malldemo.dao"})
public class MybatisConfig {
}

package com.cyf.malldemo;

import com.cyf.malldemo.mbg.model.UmsPermission;
import com.cyf.malldemo.service.UmsAdminService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
    class MallDemoApplicationTests {

    @Autowired
    private  UmsAdminService umsAdminService;
    @Test
    void contextLoads() {
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(1l);
        System.out.println("permissionlist size="+permissionList.size());
        for (UmsPermission umsPermission:
             permissionList) {
            System.out.println(umsPermission.getValue());
        }
    }


}

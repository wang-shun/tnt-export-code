/* 
 * Copyright © 2017 www.lvmama.com
 */

/*
 * 修订记录:
 * @author 钟勋（zhongxun@lvmama.com） 2017-08-21 13:53 创建
 */
package com.lvmama.tnt.test;

import com.lvmama.boot.core.App;
import com.lvmama.tnt.Main;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 单元测试父类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
public class AbstractTest {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    static {
        // 设置使用环境
        App.setProfileIfNotExists("ARK");
    }
}

/* 
 * Copyright © 2017 www.lvmama.com
 */

/*
 * 修订记录:
 * @author 钟勋（zhongxun@lvmama.com） 2017-08-04 15:51 创建
 */
package com.lvmama.tnt;

import com.github.ltsopensource.spring.boot.annotation.EnableTaskTracker;
import com.lvmama.boot.core.App;
import com.lvmama.boot.core.LmmBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序启动入口
 */
@LmmBootApplication(appName = "tnt-export-code", httpPort = 8082)
@EnableTaskTracker
public class Main {
    public static void main(String[] args) {
        App.setProfileIfNotExists("ARK");
        SpringApplication.run(Main.class, args);
    }
}

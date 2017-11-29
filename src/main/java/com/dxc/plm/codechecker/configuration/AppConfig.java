package com.dxc.plm.codechecker.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 扫描component范围，basePackage及其子包
@ComponentScan(basePackages = { "com.dxc.plm.codechecker" })
// 还可以通过指定class所在的位置来扫描bean，如下，扫描Application所在包下的所有component
// 好处是重构代码时不会像string那样需要手动修改
// @ComponentScan(basePackageClasses={Application.class})
public class AppConfig {

}

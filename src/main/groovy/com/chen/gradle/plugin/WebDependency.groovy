package com.chen.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author chen
 * @date 2017/07/17/11:21
 */
class WebDependency implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.dependencies() {
            // spring
            compile DependConst.sp_webmvc
            compile DependConst.sp_web
            compile DependConst.sp_test
            compile DependConst.sp_orm
            compile DependConst.sp_oxm
            // 测试依赖
            testRuntime DependConst.servlet_api
            testCompile DependConst.junit
            compileOnly DependConst.lombok
        }
    }
}

package com.chen.gradle.plugin

import org.gradle.api.Project

/**
 * springboot项目默认使用dependencyManager管理项目依赖，所以有时候会出现指定的版本号被高版本覆盖的问题
 * 所以需要显式的指定版本号
 * @author chen
 * date 2017/10/26 14:05
 */
class ProcessBootConfig {
    def static implicitVersion(Project project) {
        project.extensions['jetty.version'] = '9.2.21.v20170120'
        project.extensions['commons-beanutils.version'] = '1.8.0'
    }

    def static appendBootDependency = {
        apply plugin: 'org.springframework.boot'
        dependencyManagement {
            resolutionStrategy {
                cacheChangingModulesFor 0, 'seconds'
            }
        }
        springBoot {
            mainClass = "com.daydao.spring.boot.started.SpringBootStart"
        }
    }
}

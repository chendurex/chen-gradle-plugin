## chen-gradle-plugin

### 一、简介

1. build.gradle配置文件中加入以下配置
 
        ext{
            child_version = '0.0.1'// 项目版本号
            pro_group = 'com.chen'
        }
        apply plugin: 'java'
        // 插件请放入当前位置下方
        buildscript {
            repositories {
                maven { url "http://nexus.chen.com/nexus/content/groups/public/" }
            }
            dependencies {
                classpath 'com.chen:chen-gradle-plugin:+'
            }
            configurations.all {
                resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
            }
        }
        	  
2. RootConfig：用于构建`war`项目

	> 引入插件：build.gradle配置文件顶部
	>
	    
        apply plugin: "com.chen.rootconfig"

3. JarConfig：用于单独构建基础组件包

	> 引入插件：build.gradle配置文件顶部
	>
	    
        apply plugin: "com.chen.jarconfig"


       
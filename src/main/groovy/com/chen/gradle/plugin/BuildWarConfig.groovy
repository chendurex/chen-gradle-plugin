package com.chen.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.compile.JavaCompile

/**
 * @author chen
 * @date 2017/11/17/11:21
 */
class BuildWarConfig implements Plugin<Project> {

    @Override
    void apply(Project project) {
        ProcessResourcesUtils.processResources(project)
        project.defaultTasks = BuildConst.DEFAULT_TASK
        project.configure(project.rootProject, webConfig)
        project.configure(project.rootProject, dependenciesUrl)
        project.allprojects compileConfig
        project.subprojects subProject
        if (Commons.isBootOrCloudPro(project)) {
            ProcessBootConfig.implicitVersion(project)
            project.configure(project.rootProject, ProcessBootConfig.appendBootDependency)
        }
    }

    // 设置根目录依赖的上传地址，否则会出现在自定义个性化依赖时，找不到下载地址，当然可以显式的在根项目配置上传地址
    static def dependenciesUrl = {
        dependencies {
            repositories {
                maven { url BuildConst.publicDomain }
            }
        }
    }

    static def subProject = {
        apply plugin: 'maven'
        apply plugin: 'java'
        apply plugin: 'maven-publish'

        group = Commons.getGroup(project.rootProject)
        repositories {
            maven { url BuildConst.publicDomain}
        }
        version = Commons.getVersion(project.rootProject)
        publishing {
            publications {
                mavenJava(MavenPublication) {
                    from components.java
                    artifact sourceJar {
                        classifier "sources"
                    }
                }
            }
            repositories {
                maven {
                    name 'chen-maven-repo'
                    url Commons.getUploadUrl(project.rootProject)
                    credentials{
                        username = BuildConst.uploadName
                        password = BuildConst.uploadPassword
                    }
                }
            }
        }
    }

    def webConfig = {
        apply plugin: 'war'
        apply plugin: 'project-report'
        dependencies {
            compile subprojects
            testCompile 'junit:junit:4.12'
            compileOnly "org.projectlombok:lombok:1.16.10"
        }
        project.archivesBaseName = "ROOT"
    }

    static def compileConfig = {
        apply plugin: 'java'
        compileJava {
            options.encoding = 'utf-8'
            options.define compilerArgs: [
                    '-source', BuildConst.JDK_VERSION,
                    '-target', BuildConst.JDK_VERSION
            ]
        }
        sourceSets {
            main {
                resources {
                    srcDir 'src/main/java'
                }
            }
        }

        // 再次设置编码，否则会出现test包下面的中文乱码
        tasks.withType(JavaCompile) {
            options.encoding = 'UTF-8'
        }
        // 打包jar
        jar {
            manifest {
                attributes 'build-date': new Date().format('yyyy-MM-dd HH:mm:ss')
            }
            excludes = ['properties/**', 'spring/**', 'springmvc/**', '*.properties', 'generatorConfig.xml',
                         'fonts/**', 'generated/**', 'images/**', 'templates/**']
            if (Commons.isNotComponent(project.rootProject)) {
                excludes.addAll('**/META-INF/custom/**', '**/META-INF/controller/**', '**/META-INF/main/**',
                        '**/META-INF/dependency/**', 'excludeURL.xml')
            }
        }

        configurations.all {
            resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
        }
        configurations {
            compile.exclude module: 'log4j-over-slf4j'
            // 统一排除springboot对tomcat依赖，日志依赖
            if (Commons.isBootOrCloudPro(project.rootProject)) {
                compile.exclude module: 'spring-boot-starter-logging'
                compile.exclude module: 'spring-boot-starter-tomcat'
                compile.exclude group: 'org.apache.tomcat'
            }
            if (Commons.isBootPro(project.rootProject)) {
                compile.exclude module: 'infrastructure-spring-cloud-starter'
            }
        }
        // 部署的时候不执行测试
        compileTestJava.enabled=false
        processTestResources.enabled=false
        testClasses.enabled=false
        test.enabled=false
    }
}



package com.chen.gradle.plugin

import com.chen.gradle.plugin.Commons
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Jar

/**
 *
 * @author chen
 * @date 2017/7/18 15:15
 */
class ProcessResourcesUtils {
    static void processResources(Project project) {
        Set<Project> subProject = project.getSubprojects();
        for (Project subPro : subProject) {
            sourcesJar(subPro)
        }
        if (Commons.isNotComponent(project)) {
            copyTask(project)
        }
        //removeSources(project)
    }

    static void copyTask(Project project) {
        project.task('copyResources', type: Copy) {
            def dir = project.getProjectDir().getAbsolutePath()+"/src/main/resources"
            println "-------复制子项目的resources资源到$dir-------"
            for (Project subPro : project.getSubprojects()) {
                def subPath = subPro.getProjectDir().getAbsolutePath() + "/src/main/resources"
                from subPath
                println "-------从子项目中获取资源文件$subPath-------"
            }
            into dir
        }
        project.getTasks().getByName("processResources").dependsOn('copyResources')
    }

    static void removeSources(Project project) {
        project.task('removeSources') {
            if (Commons.hasSubject(project)) {
                doLast {
                    if (Commons.isBootOrCloudPro(project.rootProject)) {
                        def mainPath = project.getProjectDir().getAbsolutePath()
                        // 删除资源文件就好了，防止有些人在根目录存在源码导致把源码删除
                        def resourcesDir = mainPath+"/src/main/resources"
                        def webDir = new File(resourcesDir)
                        if (webDir.exists()) {
                            println 'remove resources'
                            webDir.deleteDir()
                        }
                        // 删除webapp目录资源
                        def webFilePath = mainPath+"/src/main/webapp"
                        def webFile = new File(webFilePath)
                        if (webFile.exists()) {
                            println 'remove webapp/'
                            webFile.deleteDir()
                        }
                    }
                }
            }
        }
        project.getTasks().getByName("build").dependsOn('removeSources')
    }

    static void sourcesJar(Project project) {
        project.task('sourceJar',type: Jar) {
            classifier = 'sources'
            from project.getProjectDir().getAbsolutePath() + "/src/main/java"
        }
    }
}
package com.chen.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author chen
 * @date 2017/07/17/11:21
 */
class BuildJarConfig implements Plugin<Project> {

    @Override
    void apply(Project project) {
        ProcessResourcesUtils.sourcesJar(project)
        project.configure(project.rootProject, BuildWarConfig.dependenciesUrl)
        project.configure(project.rootProject, BuildWarConfig.compileConfig)
        project.configure(project.rootProject, BuildWarConfig.subProject)
    }
}




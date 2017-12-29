package com.chen.gradle.plugin

import org.gradle.api.Project

/**
 *
 * @author chen
 * @date 2017/8/1 17:40
 */
class Commons {

    /**
     * 非组件，并且是在开发环境才加上快照后缀，否则都按照原始版本上传到release
     * @param project
     * @return
     */
    static String getVersion(Project project) {
        return getExtensionProp(project, "child_version")
    }

    /**
     * 带有SNAPSHOT则上传快照，否则正式包
     * @param project
     * @return
     */
    static String getUploadUrl(Project project) {
        def version = getVersion(project)
        return version.contains('-SNAPSHOT') ? BuildConst.snapshotDomain : BuildConst.releaseDomain
    }

    static boolean hasSubject(Project project) {
        !project.getSubprojects().isEmpty()
    }

    static boolean isBootOrCloudPro(Project project) {
        isBootPro(project) || isCloudPro(project)
    }

    static boolean isBootPro(Project project) {
        "boot".equals(getProType(project))
    }

    static boolean isCloudPro(Project project) {
        "cloud".equals(getProType(project))
    }

    static boolean isComponent(Project project) {
        "component".equals(getProType(project))
    }

    static boolean isNotComponent(Project project) {
        !isComponent(project)
    }

    static String getGroup(Project project) {
        hasExtensionProp(project, "pro_group") ? getExtensionProp(project, "pro_group") :  "com.chen"
    }

    private static String getProType(Project project) {
        hasExtensionProp(project, "pro_type") ? getExtensionProp(project, "pro_type") : ""
    }


    private static String getExtensionProp(Project project, String name) {
        project.getExtensions().getExtraProperties().get(name)
    }

    private static boolean hasExtensionProp(Project project, String name) {
        project.getExtensions().getExtraProperties().has(name)
    }
}
package com.chen.gradle.plugin
/**
 *
 * @author chen
 * @date 2017/8/1 17:36
 */
class BuildConst {
    static def uploadName = "admin";
    static def uploadPassword = "admin123";
    static def mavenDomain = 'http://nexus.chen.com'
    static def publicDomain = 'http://nexus.chen.com/nexus/content/groups/public/'
    static def releaseDomain = mavenDomain + '/nexus/content/repositories/releases/'
    static def snapshotDomain = mavenDomain + '/nexus/content/repositories/snapshots/'
    static def DEFAULT_TASK = ['clean', 'build']
    def static JDK_VERSION = 1.7
}

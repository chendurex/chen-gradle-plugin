package com.chen.gradle.plugin
/**
 *
 * @author chen
 * @date 2017/7/17 17:27
 */
class DependConst {
    // spring
    def static springframework = '4.3.10.RELEASE'
    def static sp_webmvc = "org.springframework:spring-webmvc:${springframework}"
    def static sp_web = "org.springframework:spring-web:${springframework}"
    def static sp_test = "org.springframework:spring-test:${springframework}"
    def static sp_orm = "org.springframework:spring-orm:${springframework}"
    def static sp_oxm = "org.springframework:spring-oxm:${springframework}"

    def static servlet_api = 'javax.servlet:javax.servlet-api:3.1.0'
    def static junit = 'junit:junit:4.12'
    def static lombok = 'org.projectlombok:lombok:1.16.10'

}

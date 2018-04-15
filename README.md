# MyTest


rxjava用法:
componet的inject方法接受父类类型参数，而调用时传入的是子类型对象则无法注入！！！
dagger2的单利不是java中的单利，其是依附在component的上的，不同的componet是不一样的



gradle基本配置（构建编译项目）

开发项目过程中，10MB的中等项目，编译时间会变久 androidStudio2.0提供一种instant run方式

1。使用deamon
构建初始化，很多是关于java虚拟机，全部的加载动作给一个单独的后台进程去做，如果要这么做，
只需要在gradle.properties,加上已句话 org.gradle.daemon=true;

2. 依赖库使用固定版本
 implementation 'com.google.code.gson:gson:2.2.4'／／直接使用固定版本（建议）

 以下写法会去jcenter中拉取新版本（网络差时构建速度变慢） implementation==compile
 implementation 'com.google.code.gson:gson:2.2.+'
 implementation 'com.google.code.gson:gson:2.+'
 mplementation 'com.google.code.gson:gson:+'

3.设置全局版本号

double a=24,b=24.0; project下的build.gradle 定义成员变量
每一个module可以直接使用

方式1；新建一个自定义的config.gradle 名称随意，在项目中gradle引用 apply from "config.gradle"
方式2：直接在根gradle下写
project.ext{
    applicationId="com.xxx"
    compileSdkVersion=26
    targetSdkVersion=26

}
引用 compileSdkVersion 26 ---compileSdkVersion rootProject.ext.compileSdkVersion

4.设置全局库版本
def supportVersion="26.1.0"
def butterKnifeVersion="5.1.5"
project.ext{
    applicationId="com.xxx"
    compileSdkVersion=26
    targetSdkVersion=26

    butterKnifeVersion= 'com.jakewharton:butterknife:${butterKnifeVersion}'
    libSupportAppcompat ='com.android.support:appcompat-v7:${supportVersion}'
}

module引用 implementation/compile 'com.jakewharton:butterknife:rootProject.ext.butterKnifeVersion'


提升效率：editor live template 创建代码的快捷键

设置--Gradle offline work 勾




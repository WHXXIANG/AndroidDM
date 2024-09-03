# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
#如果引用了v4或者v7包
-dontwarn android.support.**
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
#忽略警告
-ignorewarnings
# 保持哪些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.ext.BroadcastReceiver
-keep public class * extends android.ext.ContentProvider


# IOTSDK开始-------------
-keep class com.richinfo.mthttp.entity.**{*;}
-keep class com.richinfo.mthttp.MTSDK{ *; }
-keep class com.richinfo.mthttp.Constant{ *; }
-keep class com.richinfo.mthttp.utils.SPUtils{ *; }
-keep class com.dmyk.android.mt.IDeviceForMt{*;}
-keep public class * extends android.app.Service
-keep class com.richinfo.mthttp.service.IMTService{*;}
-keep public class * implements com.richinfo.mthttp.service.IMTService
# leshan jar 包混淆
-keep class ch.qos.logback.**{*;}
-keep class ch.qos.logback.**{*;}
-keep class com.fasterxml.jackson.**{*;}
-keep class com.sun.activation.**{*;}
-keep class com.sun.mail.**{*;}
-keep class com.upokecenter.**{*;}
-keep class javax.**{*;}
-keep class jline.**{*;}
-keep class models.**{*;}
-keep class org.eclipse.**{*;}
-keep class org.fusesource.**{*;}
-keep class org.slf4j.**{*;}
-keep class picocli.**{*;}
-keep class schemas.**{*;}
#lwm2m混淆结束
# IOTSDK结束-------------

-keep class ri.mt_demo.**{*;}


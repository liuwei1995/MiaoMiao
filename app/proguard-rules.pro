# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\anzhuang\androidstudio\androidStudio_2.2_SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

 #不做预校验
-dontpreverify

 #混淆时是否记录日志
-verbose

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护注解
-keepattributes *Annotation*

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService


#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment

#忽略警告
-ignorewarning

##记录生成的日志数据,gradle build时在本项目根目录输出##

#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt




#如果引用了v4或者v7包
-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**






-keep public class * extends android.view.View {
public <init>(android.content.Context);
public <init>(android.content.Context, android.util.AttributeSet);
public <init>(android.content.Context, android.util.AttributeSet, int);
public void set*(...);
}

#保持 native 方法不被混淆
#           -keepclasseswithmembernames class * {
#               native <methods>;
#           }

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
native <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet);
}

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
private <fields>;
!static !transient <fields>;
!private <fields>;
!private <methods>;
}


#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
static final long serialVersionUID;
private static final java.io.ObjectStreamField[] serialPersistentFields;
!static !transient <fields>;
!private <fields>;
!private <methods>;
private void writeObject(java.io.ObjectOutputStream);
private void readObject(java.io.ObjectInputStream);
java.lang.Object writeReplace();
java.lang.Object readResolve();
}

#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
#-keepclassmembers enum * {
#  public static **[] values();
#  public static ** valueOf(java.lang.String);
#}

-keepclassmembers class * {
public void *onClick(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
public static <fields>;
}


 #不混淆H5交互
-keepattributes *JavascriptInterface*



#start--------------------------------------- Glide----------------------------------------------start
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#end----------------------------------------- Glide----------------------------------------------end

#start 广点通
-dontwarn com.qq.e.**
-keep class com.qq.e.**{*;}
#end    广点通


#start--------------------------------------- com.sohuvideo.ui_plugin----------------------------------------------start


-libraryjars ../ui_plugin/libs/androidcommon-toolbox.jar
-libraryjars ../ui_plugin/libs/gson-2.2.2.jar
-libraryjars ../ui_plugin/libs/sohu_net.jar
-libraryjars ../ui_plugin/libs/SohuPlayer_V5.6.0__2017_07_04_16_16_release.jar


-dontwarn com.sohuvideo.ui_plugin.**
-keep class com.sohuvideo.ui_plugin.**{*;}

-dontwarn com.sohuvideo.player.**
-keep class com.sohuvideo.player.**{*;}

-dontwarn com.sohuvideo.api.**
-keep class com.sohuvideo.api.**{*;}

-dontwarn com.sohu.**
-keep class com.sohu.**{*;}

-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}

-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}

-dontwarn com.android.sohu.**
-keep class com.android.sohu.**{*;}

-dontwarn com.android.sohu.**
-keep class com.android.sohu.**{*;}

-dontwarn com.sohu.lib.**
-keep class com.sohu.lib.**{*;}

-dontwarn org.apache.**
-keep class org.apache.**{*;}

-dontwarn com.mediav.ads.sdk.**
-keep class com.mediav.ads.sdk.**{*;}

-dontwarn org.opencv.**
-keep class org.opencv.**{*;}

-dontskipnonpubliclibraryclassmembers


#Warning:com.android.sohu.sdk.common.toolbox.RSAUtils:
# can't find referenced method 'byte[] decodeBase64(java.lang.String)' in library class org.apache.commons.codec.binary.Base64

#end----------------------------------------- com.sohuvideo.ui_plugin----------------------------------------------end

#
#
#-keep public class * com.google.gson.**{*;}
#-keep public class * com.android.sohu.sdk.common.**{*;}
#-keep public class * com.sohu.**{*;}
#-keep public class * com.sohu.lib.**{*;}


# 嵌入广点通sdk时必须添加
-keep class com.qq.e.** {
    public protected *;
}

# 嵌入广点通sdk时必须添加
-keep class android.support.v4.**{ *;}

# Demo工程里用到了AQuery库，因此需要添加下面的配置
# 请开发者根据自己实际情况给第三方库的添加相应的混淆设置
-dontwarn com.androidquery.**
-keep class com.androidquery.** { *;}


-keep public class com.mi.adtracker.MiAdTracker{ *; }
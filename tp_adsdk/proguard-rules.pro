# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 这句话能够使我们的项目混淆后产生映射文件,文件在`build/outputs/mapping/debug/mapping.txt`(debug包)或者`build/outputs/mapping/release/mapping.txt`(release包)文件夹下
# 包含有类名->混淆后类名的映射关系,
-verbose
#忽略警告
-ignorewarning

##记录生成的日志数据,gradle build时在本项目根目录输出##
#apk 包内所有 class 的内部结构
-dump proguard/class_files.txt
#未混淆的类和成员
-printseeds proguard/seeds.txt
#列出从 apk 中删除的代码
-printusage proguard/unused.txt
#混淆前后的映射
-printmapping proguard/mapping.txt
########记录生成的日志数据，gradle build时 在本项目根目录输出-end######
# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改, 我没有使用这个东东
#-optimizations !code/simplification/cast,!field/*,!class/merging/*

# 保护代码中的Annotation不被混淆，这在JSON实体映射时非常重要，
-keepattributes *Annotation*
# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

##保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}


#保持枚举 enum 类不被混淆
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

-dontwarn com.squareup.**
#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
-dontwarn com.tianpeng.tp_adsdk.**
-keep class com.tianpeng.tp_adsdk.**{*;}
-keep interface com.tianpeng.tp_adsdk.**{*;}
-keep class com.android.**{*;}
-keep class cn.async.admobhttp.**{	*;	}
-keep class com.jaredrummler.android.processes.**{*;}
-keep class com.jaredrummler.android.processes.models.**{*;}
-dontwarn org.apache.commons.**
-keep class org.apache.**{	*;	}
#广点通sdk
-keep class com.qq.e.**	{public protected *;}
-keep class android.support.v4.**{public *;}
-keep class android.support.v7.**{public *;}
-keep class MTT.ThirdAppInfoNew	{*;}
-keep class com.tencent.**	{*;}
#百度sdk
-keepclassmembers class * extends android.app.Activity	{
public void *(android.view.View);}
-keepclassmembers enum *	{
public static **[]	values();
public static ** valueOf(java.lang.String);
		}
-keep class com.baidu.mobads.*.**{*;}
#ADMOB SDK
#-keep class admsdk.library.**{*;}
#讯飞sdk
#-keep class com.iflytek.**{*;}
#有道sdk
-ignorewarnings
-keep class com.youdao.sdk.**{*;}
#头条 穿山甲 sdk
-keep class com.bytedance.sdk.openadsdk.**	{	*;	}
-keep class com.androidquery.callback.**	{*;}
-keep class com.bytedance.sdk.openadsdk.service.TTDownloadProvider
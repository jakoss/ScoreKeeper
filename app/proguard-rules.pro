# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\jakub\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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

-dontwarn rx.Observable
-dontwarn rx.observables.BlockingObservable
-dontwarn retrofit2.Response
-dontwarn retrofit2.Call

-keep class pl.ownvision.scorekeeper.** { *; }

# AboutLibraries
-keep class .R
-keep class **.R$* {
    <fields>;
}

# ProGuard
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# FIXME Review following rules after bugs have been fixed: https://github.com/airbnb/MvRx/pull/138 and https://github.com/airbnb/MvRx/issues/126

-keep class kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoaderImpl
-keep class kotlin.reflect.jvm.internal.impl.load.java.FieldOverridabilityCondition
-keep class kotlin.reflect.jvm.internal.impl.load.java.ErasedOverridabilityCondition
-keep class kotlin.reflect.jvm.internal.impl.load.java.JavaIncompatibilityRulesOverridabilityCondition
 # If Companion objects are instantiated via Kotlin reflection and they extend/implement a class that Proguard
# would have removed or inlined we run into trouble as the inheritance is still in the Metadata annoation
# read by Kotlin reflection.
# FIXME Remove if Kotlin reflection is supported by Pro/Dexguard
-if class **$Companion extends **
-keep class <2>
-if class **$Companion implements **
-keep class <2>
 # https://medium.com/@AthorNZ/kotlin-metadata-jackson-and-proguard-f64f51e5ed32
-keep class kotlin.Metadata { *; }
 # https://stackoverflow.com/questions/33547643/how-to-use-kotlin-with-proguard
-dontwarn kotlin.**
 # https://app.bugsnag.com/airbnb/android-1/errors/59f6c07487a5c5001a49d882?filters[event.since][0]=all&filters[event.severity][0][value]=error&filters[event.severity][0][type]=eq
# https://github.com/square/okhttp/issues/3582
-keep class okhttp3.internal.publicsuffix.PublicSuffixDatabase
 # The code is safeguarded against API28 use so we can just ignore the warning bout Handler.createAsync(looper) not existing.
# Can be removed when compiled against API >=28
-dontwarn com.airbnb.epoxy.EpoxyAsyncUtil
# RxJava
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
 # Oddly need to keep that even though Evernote state is not used in the app.
-keepnames class * { @com.evernote.android.state.State *;}
 #
# From: https://github.com/square/retrofit/blob/master/retrofit/src/main/resources/META-INF/proguard/retrofit2.pro
#
 # Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod
 # Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
 # Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
 # Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
 # Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
 # Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions
 #
# From https://github.com/square/okhttp/blob/master/okhttp/src/main/resources/META-INF/proguard/okhttp3.pro
#
 # JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
 # A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
 # Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
 # OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
 #
# From: https://github.com/square/moshi/blob/master/moshi/src/main/resources/META-INF/proguard/moshi.pro
#
 # JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
 -keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
 -keep @com.squareup.moshi.JsonQualifier interface *
 # Enum field names are used by the integrated EnumJsonAdapter.
# Annotate enums with @JsonClass(generateAdapter = false) to use them with Moshi.
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
}
 # The name of @JsonClass types is used to look up the generated adapter.
-keepnames @com.squareup.moshi.JsonClass class *
 # Retain generated JsonAdapters if annotated type is retained.
-if @com.squareup.moshi.JsonClass class *
-keep class <1>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*
-keep class <1>_<2>JsonAdapter {
    <init>(...);
    <fields>;
}
# Proguard bug: https://sourceforge.net/p/proguard/bugs/731/
#-if @com.squareup.moshi.JsonClass class **$*$*
#-keep class <1>_<2>_<3>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*$*
#-keep class <1>_<2>_<3>_<4>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*$*$*
#-keep class <1>_<2>_<3>_<4>_<5>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
#-if @com.squareup.moshi.JsonClass class **$*$*$*$*$*
#-keep class <1>_<2>_<3>_<4>_<5>_<6>JsonAdapter {
#    <init>(...);
#    <fields>;
#}
 #
# From: https://github.com/square/moshi/blob/master/kotlin/reflect/src/main/resources/META-INF/proguard/moshi-kotlin.pro
#
 -keep class kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoaderImpl
 -keepclassmembers class kotlin.Metadata {
    public <methods>;
}
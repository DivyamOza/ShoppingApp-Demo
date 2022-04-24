# Here, We keep all models class same as it is
-keep class com.divyamoza.assesmentdemo.models.** {*;}

# -------- RETROFIT -------- #
# >> From: https://proguard-rules.blogspot.com/ #
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# >> From: Original Documentation:
# >> https://github.com/square/retrofit/blob/master/retrofit/src/main/resources/META-INF/proguard/retrofit2.pro
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

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
-dontwarn retrofit2.KotlinExtensions
#-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# -------- OkHttp3 -------- #
# >> From: https://proguard-rules.blogspot.com/ #
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontnote okhttp3.**

# -------- okio -------- #
# >> From: https://proguard-rules.blogspot.com/ #
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement


-dontnote rx.Observable

-dontnote android.security.KeyStore
-dontwarn okio.Okio
-dontwarn okio.DeflaterSink

-dontnote com.android.org.conscrypt.SSLParametersImpl
-dontnote org.apache.harmony.xnet.provider.jsse.SSLParametersImpl
-dontnote sun.security.ssl.SSLContextImpl

# -------- Glide -------- #
# >> From: https://proguard-rules.blogspot.com/ #
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# -------- Glide Transformations -------- #
-dontwarn jp.co.cyberagent.android.gpuimage.**

# -------- Glide Transformations -------- #
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.stream.** { *; }

# >> From: Original Documentation:
# >> https://github.com/square/retrofit/blob/master/retrofit/src/main/resources/META-INF/proguard/retrofit2.pro
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# -------- ROOM DATABASE -------- #
# >> https://stackoverflow.com/questions/53700773/how-to-set-proguard-rule-for-room-library-on-android
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**
-keepclassmembers,allowobfuscation class * {
@com.google.gson.annotations.SerializedName <fields>;
}
-dontwarn android.arch.util.paging.CountedDataSource
-dontwarn android.arch.persistence.room.paging.LimitOffsetDataSource


# -------- LOTTIE ANIMATION -------- #
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}

## Exclude Crashlytics with ProGuard
#-keep class com.crashlytics.** { *; }
#-dontwarn com.crashlytics.**


# -------- Disable Debuggable -------- #
#-dontskipnonpubliclibraryclasses
#-forceprocessing
#-optimizationpasses 5
#
#-keep class * extends android.app.Activity
#-assumenosideeffects class android.util.Log {
#    public static *** d(...);
#    public static *** v(...);
#}
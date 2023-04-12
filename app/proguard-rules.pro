-dontwarn com.google.android.gms.**
-keep class com.google.android** { *; }

-flattenpackagehierarchy
-ignorewarnings

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static *** d(...);
    public static *** w(...);
    public static *** v(...);
    public static *** i(...);
    public static *** e(...);
}

-keep class Jni.** {*;}
-keep public class com.status.videomaker.Activitiess.Videoo_Processingg_Act.**
-keep public class com.status.videomaker.Activitiess.MainApplication.**
-keep public class com.status.videomaker.Servicess.**
-keep class com.status.videomaker.Modelss.**{*;}

-obfuscationdictionary "D:\Sdk\tools\class_encode_dictionary.txt"
-classobfuscationdictionary "D:\Sdk\tools\class_encode_dictionary.txt"
-packageobfuscationdictionary "D:\Sdk\tools\class_encode_dictionary.txt"

-mergeinterfacesaggressively
-overloadaggressively
-repackageclasses "com.status.videomaker"
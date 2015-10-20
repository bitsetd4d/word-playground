-injars       /tmp/wordplaybuild/dst/wordspace.jar
-outjars      /tmp/wordplaybuild/dst/wordspace_out.jar
-libraryjars  /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/classes.jar
-libraryjars  /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/ui.jar
-printmapping proguard.map
-overloadaggressively
-defaultpackage ''
-allowaccessmodification

-keep public class d3bug.poc.launch.SpriteButtonWindow {
    public static void main(java.lang.String[]);
}
-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


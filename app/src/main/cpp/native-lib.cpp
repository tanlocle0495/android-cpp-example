#include <jni.h>
#include <string>

typedef std::string string;
extern "C" JNIEXPORT jstring JNICALL

//PackageName_ActivityName_MethodName.
Java_locle_android_com_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    string googleAPI_Key = "YOUR_API_KEY";

    return env->NewStringUTF(googleAPI_Key.c_str());
}





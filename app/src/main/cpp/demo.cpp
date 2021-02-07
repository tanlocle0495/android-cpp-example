//
// Created by Andrew on 2/7/21.
//



//#include <jni.h>
#include "../../../../../../../../../Library/Android/sdk/ndk/21.1.6352462/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/jni.h"
#include "../../../../../../../../../Library/Android/sdk/ndk/21.1.6352462/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/c++/v1/string"

typedef std::string string;
extern "C" JNIEXPORT jstring JNICALL

//PackageName_ActivityName_MethodName.
Java_locle_android_com_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {


    return env->NewStringUTF( "YOUR_API_KEY".c_str());
}

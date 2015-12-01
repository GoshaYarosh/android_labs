#include "alarm_clock_app.h"

JNIEXPORT jstring JNICALL Java_cats_wants_meow_alarmclock_fragments_AboutFragment_nativeGetAppName(JNIEnv* env, jclass cls)
{
    return env->NewStringUTF("Alarm Clock App");
}
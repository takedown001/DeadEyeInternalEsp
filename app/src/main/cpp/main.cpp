#include <jni.h>
#include <string>
#include <list>
#include <vector>
#include <string.h>
#include <pthread.h>
#include <cstring>
#include <jni.h>
#include <unistd.h>
#include <fstream>
#include "DRAWING/ESP.h"
#include "DRAWING/Hacks.h"
#include "Config.h"
#include <HOOK/Includes/Utils.h>
#if defined(__aarch64__)
#include <HOOK/And64InlineHook/And64InlineHook.hpp>
#else
#include <HOOK/Substrate/SubstrateHook.h>
#include <HOOK/Substrate/CydiaSubstrate.h>
#endif
#define SOCKET_NAME "\0xcode"
#include "HOOK/KittyMemory/MemoryPatch.h"
#include "HOOK/Includes/Logger.h"
#include "HOOK/Includes/Utils.h"
#include "DRAWING/socket.h"

void *instanceBtn;
ESP espOverlay;



extern "C" JNIEXPORT void JNICALL
Java_com_memory_xploiter_Loader_DrawOn(JNIEnv *env, jclass , jobject espView, jobject canvas) {
    espOverlay = ESP(env, espView, canvas);
    if (espOverlay.isValid()) {
        DrawESP(espOverlay, espOverlay.getWidth(), espOverlay.getHeight());
    }
}
extern "C" JNIEXPORT void JNICALL
Java_com_memory_xploiter_Loader_Close(JNIEnv *,  jobject ) {
    Close();
}
extern "C" JNIEXPORT void JNICALL
Java_com_memory_xploiter_Loader_Switch(JNIEnv *, jclass clazz,jint code,jboolean jboolean1) {
    switch((int)code){
        case 1:
            isPlayerBox = jboolean1;   break;
        case 2:
            isPlayerLine = jboolean1;  break;
        case 3:
            isPlayerDist = jboolean1;  break;
        case 4:
            isPlayerHealth = jboolean1;  break;
        case 5:
            isPlayerName = jboolean1;  break;
        case 8:
            isSkelton1 = jboolean1;  break;
        case 9:
            isGrenadeWarning = jboolean1;  break;
        case 146:
            iscenter = jboolean1;
            break;
        case 147:
            isdown = jboolean1;
            break;
        case 148:
            iscross =jboolean1;
            break;
        case 14:
            options.openState = jboolean1;
            break;
        case 15:
            options.tracingStatus = jboolean1;
            break;
        case 16:
            options.pour = jboolean1;
            break;


    }
}


extern "C"
JNIEXPORT void JNICALL
Java_com_memory_xploiter_Loader_Size(JNIEnv * env, jobject type, jint num,jfloat size) {
        switch (num)
        {
        case 1:
            itemSize = size;
            break;
        case 2:
            enemySize = size;
            break;
        case 3:
            vehicleSize = size;
            break;
        case 4:
            alertSize = size;
            break;
        default:
            break;
        }
    }


extern "C"
JNIEXPORT void JNICALL
Java_com_memory_xploiter_Loader_Range(JNIEnv *, jobject, jint range)
{
    options.aimingRange = 1 + range;
}

extern "C" JNIEXPORT void JNICALL
Java_com_memory_xploiter_Loader_BSpeed(JNIEnv *, jobject, jint BSpeed)
{
    options.BSpeed = 1 + BSpeed;
}
extern "C" JNIEXPORT void JNICALL
Java_com_memory_xploiter_Loader_SetAim(JNIEnv *,  jobject ,jint num, jint value) {
    switch((int)num){
        case 1:
           options.openState = (int)value;
            break;
        case 2:
            options.aimbotmode = (int)value;
            break;
        case 3:
            options.priority = (int)value;
            break;
        case 4:
            options.aimingState = (int)value;
            break;
        case 5:
           options.pour = (int)value;
            break;

    }
}

__attribute__((constructor))
void initializer() {

    pthread_t ptid;
}


JNIEXPORT void JNICALL
JNI_OnUnload(JavaVM *vm, void *reserved) {
	}



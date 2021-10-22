
#ifndef MOD_MENU_MEMORY_H
#define MOD_MENU_MEMORY_H

#include "KittyMemory/MemoryPatch.h"


struct Patch {
    MemoryPatch MidNight;
    MemoryPatch LessRecoil, Horizontal;
    MemoryPatch SmallCrosshair;
    MemoryPatch Aimbot;
    MemoryPatch Bullet_Track;
    MemoryPatch Bypass;
    MemoryPatch CarJump1;
    MemoryPatch XEffect; //New
    MemoryPatch CarSpeed; //New
    MemoryPatch UlrtaFlash,
            FixStuck,
            FixSitStuck;
    MemoryPatch Flash; //New
    MemoryPatch CarJump,
            Bypass1,
            Bypass2,
            Bypass3,
            Bypass4,
            Bypass5,
            Bypass6,
            Bypass7,
            Bypass8,
            Bypass9,
            Bypass10,
            Bypass11,
            Bypass12,
            Bypass13,
            Bypass14,
            Bypass15,
            Bypass16,
            Bypass17,
            Bypass18,
            Bypass19,
            Bypass20,
            Bypass21,
            Bypass22,
            Bypass23,
            Bypass24,
            Bypass25,
            Bypass26,
            Bypass27;
//             Bypass28,
//             Bypass29,
//             Bypass30,
//             Bypass31,
//             Bypass32,
//             Bypass33,
//             Bypass34,
//             Bypass35,
//             Bypass36,
//             Bypass37,
//             Bypass38,
//             Bypass39,
//             Bypass40,
//             Bypass41,
//             Bypass42,
//             Bypass43,
//             Bypass44,
//             Bypass45,
//             Bypass46,
//             Bypass47,
//             Bypass48,
//             Bypass49,
//             Bypass50,
//             Bypass51,
//             Bypass52,
//             Bypass53,
//             Bypass54,
//            Bypass55,
//            Bypass56,
//            Bypass57,
//            Bypass58,
//            Bypass59,
//            Bypass60;
//            Bypass61,
//            Bypass62,
//            Bypass63,
//            Bypass64,
//            Bypass65,
//            Bypass66,
//            Bypass67,
//            Bypass68,
//            Bypass69,
//            Bypass70,
//            Bypass71,
//            Bypass72,
//            Bypass73,
//            Bypass74,
//            Bypass75,
//            Bypass76,
//            Bypass77,
//            Bypass78,
//            Bypass79,
//            Bypass80,
//            Bypass81,
//            Bypass82,
//            Bypass83,
//            Bypass84,
//            Bypass85,
//            Bypass86,
//            Bypass87,
//            Bypass88,
//            Bypass89,
//            Bypass90,
//            Bypass91,
//            Bypass92,
//            Bypass93,
//            Bypass94,
//            Bypass95,
//            Bypass96,
//            Bypass97,
//            Bypass98,
//            Bypass99,
//            Bypass100,
//            Bypass101,
//            Bypass102,
//            Bypass103,
//            Bypass104,
//            Bypass105,
//            Bypass106,
//            Bypass107,
//            Bypass108,
//            Bypass109,
//            Bypass110,
//            Bypass111,
//            Bypass112,
//            Bypass113,
//            Bypass114,
//            Bypass115,
//            Bypass116,
//            Bypass117,
//            Bypass118,
//            Bypass119,
//            Bypass120,
//            Bypass121,
//            Bypass122,
//            Bypass123,
//            Bypass124,
//            Bypass125,
//            Bypass126,
//            Bypass127,
//            Bypass128,
//            Bypass129,
//            Bypass130,
//            Bypass131,
//            Bypass132,
//            Bypass133,
//            Bypass134,
//            Bypass135,
//            Bypass136,
//            Bypass137,
//            Bypass138,
//            Bypass139,
//            Bypass140,
//            Bypass141,
//            Bypass142,
//            Bypass143,
//            Bypass144,
//            Bypass145,
//            Bypass146,
//            Bypass147,
//            Bypass148,
//            Bypass149,
//            Bypass150,
//            Bypass151,
//            Bypass152,
//            Bypass153,
//            Bypass154,
//            Bypass155,
//            Bypass156,
//            Bypass175,
//            Bypass176,
//            Bypass178,
//            Bypass179,
//            Bypass180,
//            Bypass181,
//            Bypass182,
//            Bypass183,
//            Bypass184,
//            Bypass185,
//            Bypass186,
//            Bypass187,
//            Bypass188,
//            Bypass189,
//            Bypass190,
//            Bypass191,
//            Bypass192;
} xcode;

void *Super_thread(void *) {
     ProcMap libtprt;
     do {
         libtprt = KittyMemory::getLibraryMap("libtersafe.so");
     } while (!libtprt.isValid());
    sleep(1);
    xcode.Bypass1 = MemoryPatch::createWithHex("libtersafe.so", 0x35581C, "00 00 00 00");
     xcode.Bypass1.Modify();
    xcode.Bypass2 = MemoryPatch::createWithHex("libtersafe.so", 0xcd090, "00 00 00 00");
    xcode.Bypass2.Modify();
    xcode.Bypass3 = MemoryPatch::createWithHex("libtersafe.so", 0xcd1e4, "00 00 00 00");
    xcode.Bypass3.Modify();
    xcode.Bypass4 = MemoryPatch::createWithHex("libtersafe.so", 0xcd33c, "00 00 00 00");
    xcode.Bypass4.Modify();
    xcode.Bypass5 = MemoryPatch::createWithHex("libtersafe.so", 0xcd468, "00 00 00 00");
    xcode.Bypass5.Modify();
    xcode.Bypass6 = MemoryPatch::createWithHex("libtersafe.so", 0xcd4f0, "00 00 00 00");
    xcode.Bypass6.Modify();
    xcode.Bypass7 = MemoryPatch::createWithHex("libtersafe.so", 0xcd584, "00 00 00 00");
    xcode.Bypass7.Modify();
    xcode.Bypass8 = MemoryPatch::createWithHex("libtersafe.so", 0xcda6c, "00 00 00 00");
    xcode.Bypass8.Modify();
    xcode.Bypass9 = MemoryPatch::createWithHex("libtersafe.so", 0xcde04, "00 00 00 00");
    xcode.Bypass9.Modify();
    xcode.Bypass10 = MemoryPatch::createWithHex("libtersafe.so", 0xcde80, "00 00 00 00");
    xcode.Bypass10.Modify();
    return 0;

}


void *hack_thread(void *) {
    ProcMap il2cppMap;
    do {
        il2cppMap = KittyMemory::getLibraryMap("libUE4.so");
    } while (!il2cppMap.isValid());
   sleep(2);
    xcode.Bypass22 = MemoryPatch::createWithHex("libUE4.so", 0xEE29C, "00 00 00 00");
    xcode.Bypass22.Modify();
    xcode.Bypass23 = MemoryPatch::createWithHex("libUE4.so", 0xEE524, "00 00 00 00");
    xcode.Bypass23.Modify();
    xcode.Bypass24 = MemoryPatch::createWithHex("libUE4.so", 0xEEA88, "00 00 00 00");
    xcode.Bypass24.Modify();
    xcode.Bypass25 = MemoryPatch::createWithHex("libUE4.so", 0xEF3F4, "00 00 00 00");
    xcode.Bypass25.Modify();
    xcode.Bypass26 = MemoryPatch::createWithHex("libUE4.so", 0x6CDAC, "00 00 00 00");
    xcode.Bypass26.Modify();
    xcode.Bypass27 = MemoryPatch::createWithHex("libUE4.so", 0x3D9954, "00 00 00 00");
    xcode.Bypass27.Modify();

    return 0;
}


JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm) {
    JNIEnv *globalEnv;
    vm->GetEnv((void **) &globalEnv, JNI_VERSION_1_6);
    pthread_t ptid,tid;
    pthread_create(&ptid, NULL, Super_thread, NULL);
    pthread_create(&tid, NULL, hack_thread, NULL);
    return JNI_VERSION_1_6;
}


extern "C"
JNIEXPORT void JNICALL
Java_com_memory_xploiter_Loader_SwitchMemory(JNIEnv *env, jclass clazz, jint num) {
    switch (num) {
        case 1:
            xcode.LessRecoil = MemoryPatch("libUE4.so", 0x14EC4F4, "\x00\x00\x00\x00", 4);
            xcode.LessRecoil.Modify();
            break;
        case 2:
            xcode.LessRecoil.Restore();
            break;
        case 20:
            xcode.Horizontal = MemoryPatch("libUE4.so", 0x1F1E708, "\x00\x00\x00\x00", 4);
            xcode.Horizontal.Modify();
            break;
        case 21:
            xcode.Horizontal.Restore();
            break;
        case 3:
            xcode.SmallCrosshair = MemoryPatch("libUE4.so", 0x14EE248, "\x00\x00\xA0\x40", 4);
            xcode.SmallCrosshair.Modify();
            break;
        case 4:
            xcode.SmallCrosshair.Restore();
            break;
        case 5:
            xcode.Aimbot = MemoryPatch("libUE4.so", 0x28E462C, "\x00\x00\x00\x00", 4);
            xcode.Aimbot.Modify();
            break;
        case 6:
            xcode.Aimbot.Restore();
            break;
        case 7:
            xcode.Bullet_Track = MemoryPatch("libUE4.so", 0x3C849C4, "\x42\x30\x00\x00", 4);
            xcode.Bullet_Track = MemoryPatch("libUE4.so", 0x3FEAB98, "\x42\x30\x00\x00", 4);
            xcode.Bullet_Track.Modify();
            break;
        case 8:
            xcode.Bullet_Track.Restore();
            break;
        case 9:
            xcode.MidNight = MemoryPatch("libUE4.so", 0x32D2D20, "\x00\x00\x00\x00", 4);
            xcode.MidNight.Modify();
            break;
        case 10:
            xcode.MidNight.Restore();
            break;
        case 11:
             xcode.Bypass1.Modify();
//             xcode.Bypass2.Modify();
//             xcode.Bypass2.Modify();
//             xcode.Bypass3.Modify();
//             xcode.Bypass4.Modify();
//             xcode.Bypass5.Modify();
//             xcode.Bypass6.Modify();
//             xcode.Bypass7.Modify();
//             xcode.Bypass8.Modify();
//             xcode.Bypass9.Modify();
//             xcode.Bypass10.Modify();
//             xcode.Bypass11.Modify();
//             xcode.Bypass12.Modify();
//             xcode.Bypass13.Modify();
//             xcode.Bypass14.Modify();
//             xcode.Bypass15.Modify();
//             xcode.Bypass16.Modify();
//             xcode.Bypass17.Modify();
//             xcode.Bypass18.Modify();
//             xcode.Bypass19.Modify();
//             xcode.Bypass20.Modify();
//             xcode.Bypass21.Modify();
            xcode.Bypass22.Modify();
            xcode.Bypass23.Modify();
            xcode.Bypass24.Modify();
            xcode.Bypass25.Modify();
            xcode.Bypass26.Modify();
            xcode.Bypass27.Modify();
//             xcode.Bypass28.Modify();
//             xcode.Bypass29.Modify();
//             xcode.Bypass30.Modify();
//             xcode.Bypass31.Modify();
//             xcode.Bypass32.Modify();
//             xcode.Bypass33.Modify();
//             xcode.Bypass34.Modify();
//             xcode.Bypass35.Modify();
//             xcode.Bypass36.Modify();
//             xcode.Bypass37.Modify();
//             xcode.Bypass38.Modify();
//             xcode.Bypass39.Modify();
//             xcode.Bypass40.Modify();
//             xcode.Bypass41.Modify();
//             xcode.Bypass42.Modify();
//             xcode.Bypass43.Modify();
//             xcode.Bypass44.Modify();
//             xcode.Bypass45.Modify();
//             xcode.Bypass46.Modify();
//             xcode.Bypass47.Modify();
//             xcode.Bypass48.Modify();
//             xcode.Bypass49.Modify();
//             xcode.Bypass50.Modify();
//             xcode.Bypass51.Modify();
//             xcode.Bypass52.Modify();
//             xcode.Bypass53.Modify();
//             xcode.Bypass54.Modify();
//            xcode.Bypass55.Modify();
//            xcode.Bypass56.Modify();
//            xcode.Bypass57.Modify();
//            xcode.Bypass58.Modify();
//            xcode.Bypass59.Modify();
//            xcode.Bypass60.Modify();
            break;
        case 12:
            xcode.CarSpeed = MemoryPatch("libUE4.so", 0x4AA6B7C, "\x00\x00\x00\x00", 4);
            xcode.CarSpeed.Modify();
            break;
        case 13:
            xcode.CarSpeed.Restore();
            break;
             case 14:
                 xcode.XEffect = MemoryPatch("libUE4.so", 0x1C852B8, "\x00\x00\x00\x00", 4);
                xcode.XEffect.Modify();
                 break;
             case 15:
                 xcode.XEffect.Restore();
                 break;
        case 16:
            xcode.Flash = MemoryPatch("libUE4.so", 0x3C6F630, "\x00\x00\x00\x00", 4);
            xcode.Flash.Modify();
            break;
        case 17:
            xcode.Flash.Restore();
            break;
        case 18:
            xcode.CarJump = MemoryPatch("libUE4.so", 0x4B30FB0, "\x00\x00\x00\x00", 4);
            xcode.CarJump.Modify();
            xcode.CarJump1 = MemoryPatch("libUE4.so", 0x4B29DBC, "\x00\x00\x00\x00", 4);
            xcode.CarJump1.Modify();
            sleep(3);
            xcode.CarJump.Restore();
            xcode.CarJump1.Restore();
            break;
        case 19:
            xcode.CarJump.Restore();
            xcode.CarJump1.Restore();
            break;
        case 25:
            xcode.FixStuck = MemoryPatch("libUE4.so", 0x146AC28, "\x00\x00\x00\x00", 4);
            xcode.FixSitStuck = MemoryPatch("libUE4.so", 0x12FCB48, "\x00\x00\x50\xE3", 4);
            xcode.UlrtaFlash = MemoryPatch("libUE4.so", 0x3C7D1C8, "\x00\x00\x00\x00", 4);
            xcode.UlrtaFlash.Modify();
            xcode.FixStuck.Modify();
            xcode.FixSitStuck.Modify();
            break;
        case 26:
            xcode.UlrtaFlash.Restore();
            xcode.FixStuck.Restore();
            xcode.FixSitStuck.Restore();
            break;
        case 27:
//            xcode.Bypass1.Restore();
//            xcode.Bypass2.Restore();
//            xcode.Bypass2.Restore();
//            xcode.Bypass3.Restore();
//            xcode.Bypass4.Restore();
//            xcode.Bypass5.Restore();
//            xcode.Bypass6.Restore();
//            xcode.Bypass7.Restore();
//            xcode.Bypass8.Restore();
//            xcode.Bypass9.Restore();
//            xcode.Bypass10.Restore();
            break;
    }
}

#endif

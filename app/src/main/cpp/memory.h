
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
    MemoryPatch SpeedKnock; //New
    MemoryPatch CarSpeed; //New
    MemoryPatch UlrtaFlash;
    MemoryPatch Flash; //New
    MemoryPatch CarJump, //NeBypass,
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
            Bypass27,
            Bypass28,
            Bypass29,
            Bypass30,
            Bypass31,
            Bypass32,
            Bypass33,
            Bypass34,
            Bypass35,
            Bypass36,
            Bypass37,
            Bypass38,
            Bypass39,
            Bypass40,
            Bypass41,
            Bypass42,
            Bypass43,
            Bypass44,
            Bypass45,
            Bypass46,
            Bypass47,
            Bypass48,
            Bypass49,
            Bypass50,
            Bypass51,
            Bypass52,
            Bypass53,
            Bypass54;
//            Bypass55;
//            Bypass56,
//            Bypass57,
//            Bypass58,
//            Bypass59,
//            Bypass60,
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
    ProcMap libtersafe;
    do {
        libtersafe = KittyMemory::getLibraryMap("libtersafe.so");
        sleep(2);
    } while (!libtersafe.isValid());
    xcode.Bypass = MemoryPatch::createWithHex("libtersafe.so", 0x0, "00 00 00 00");
 //   xcode.Bypass.Modify();
    xcode.Bypass2 = MemoryPatch::createWithHex("libtersafe.so", 0x31D8, "00 00 00 00");
 //   xcode.Bypass2.Modify();
    xcode.Bypass3 = MemoryPatch::createWithHex("libtersafe.so", 0x31E0, "00 00 00 00");
 //   xcode.Bypass3.Modify();
    xcode.Bypass4 = MemoryPatch::createWithHex("libtersafe.so", 0x32E8, "00 00 00 00");
 //   xcode.Bypass4.Modify();
    xcode.Bypass5 = MemoryPatch::createWithHex("libtersafe.so", 0x33F8, "00 00 00 00");
 //   xcode.Bypass5.Modify();
    xcode.Bypass6 = MemoryPatch::createWithHex("libtersafe.so", 0x35F8, "00 00 00 00");
  //  xcode.Bypass6.Modify();
    xcode.Bypass7 = MemoryPatch::createWithHex("libtersafe.so", 0x37A0, "00 00 00 00");
 //   xcode.Bypass7.Modify();
    xcode.Bypass8 = MemoryPatch::createWithHex("libtersafe.so", 0x3EA8, "00 00 00 00");
 //   xcode.Bypass8.Modify();
    xcode.Bypass9 = MemoryPatch::createWithHex("libtersafe.so", 0x3EB0, "00 00 00 00");
//    xcode.Bypass9.Modify();
    return 0;

}


void *hack_thread(void *) {

    ProcMap il2cppMap;
    do {
        il2cppMap = KittyMemory::getLibraryMap("libUE4.so");
        sleep(3);
    } while (!il2cppMap.isValid());
    xcode.Bypass1 = MemoryPatch::createWithHex("libUE4.so", 0x662D4D8, "00 00 00 00");
     xcode.Bypass1.Modify();
    xcode.Bypass14 = MemoryPatch::createWithHex("libUE4.so", 0x23C9C4, "00 00 00 00");
    xcode.Bypass14.Modify();
    xcode.Bypass15 = MemoryPatch::createWithHex("libUE4.so", 0x23C9CC, "00 00 00 00");
    xcode.Bypass15.Modify();
    xcode.Bypass16 = MemoryPatch::createWithHex("libUE4.so", 0x23CAF4, "00 00 00 00");
    xcode.Bypass16.Modify();
    xcode.Bypass17 = MemoryPatch::createWithHex("libUE4.so", 0x23CAFC, "00 00 00 00");
    xcode.Bypass17.Modify();
    xcode.Bypass18 = MemoryPatch::createWithHex("libUE4.so", 0x23CCA4, "00 00 00 00");
    xcode.Bypass18.Modify();
    xcode.Bypass19 = MemoryPatch::createWithHex("libUE4.so", 0x23CFAC, "00 00 00 00");
    xcode.Bypass19.Modify();   // Crash Fix Bullet Track
    xcode.Bypass20 = MemoryPatch::createWithHex("libUE4.so", 0x23CCB4, "00 00 00 00");
    xcode.Bypass20.Modify(); // Crash Fix Bullet Track
    xcode.Bypass21 = MemoryPatch::createWithHex("libUE4.so", 0x23E62C, "00 00 00 00");
    xcode.Bypass21.Modify();
    xcode.Bypass22 = MemoryPatch::createWithHex("libUE4.so", 0x23CFB4, "00 00 00 00");
    xcode.Bypass22.Modify();
    xcode.Bypass23 = MemoryPatch::createWithHex("libUE4.so", 0x23E634, "00 00 00 00");
    xcode.Bypass23.Modify();
    xcode.Bypass24 = MemoryPatch::createWithHex("libUE4.so", 0x23F93C, "00 00 00 00");
    xcode.Bypass24.Modify();
    xcode.Bypass25 = MemoryPatch::createWithHex("libUE4.so", 0x241EF4, "00 00 00 00");
    xcode.Bypass25.Modify();
    xcode.Bypass26 = MemoryPatch::createWithHex("libUE4.so", 0x24025C, "00 00 00 00");
    xcode.Bypass26.Modify();
    xcode.Bypass27 = MemoryPatch::createWithHex("libUE4.so", 0x246DAC, "00 00 00 00");
    xcode.Bypass27.Modify();
    xcode.Bypass28 = MemoryPatch::createWithHex("libUE4.so", 0x24334C, "00 00 00 00");
    xcode.Bypass28.Modify();
    xcode.Bypass29 = MemoryPatch::createWithHex("libUE4.so", 0x24AAEC, "00 00 00 00");
    xcode.Bypass29.Modify();
    xcode.Bypass30 = MemoryPatch::createWithHex("libUE4.so", 0x246DB4, "00 00 00 00");
    xcode.Bypass30.Modify();
    xcode.Bypass31 = MemoryPatch::createWithHex("libUE4.so", 0x24AD34, "00 00 00 00");
    xcode.Bypass31.Modify();
    xcode.Bypass32 = MemoryPatch::createWithHex("libUE4.so", 0xBD2CB4, "00 00 00 00");
    xcode.Bypass32.Modify();
    xcode.Bypass33 = MemoryPatch::createWithHex("libUE4.so", 0x24B454, "00 00 00 00");
    xcode.Bypass33.Modify();
    xcode.Bypass34 = MemoryPatch::createWithHex("libUE4.so", 0xBCB204, "00 00 00 00");
    xcode.Bypass34.Modify();
    xcode.Bypass35 = MemoryPatch::createWithHex("libUE4.so", 0xBD016C, "00 00 00 00");
    xcode.Bypass35.Modify();
    xcode.Bypass36 = MemoryPatch::createWithHex("libUE4.so", 0xBD0174, "00 00 00 00");
    xcode.Bypass36.Modify();
    xcode.Bypass37 = MemoryPatch::createWithHex("libUE4.so", 0xBD2114, "00 00 00 00");
    xcode.Bypass37.Modify();
    xcode.Bypass38 = MemoryPatch::createWithHex("libUE4.so", 0xBD2144, "00 00 00 00");
    xcode.Bypass38.Modify();
    xcode.Bypass39 = MemoryPatch::createWithHex("libUE4.so", 0xBD24EC, "00 00 00 00");
    xcode.Bypass39.Modify();
    xcode.Bypass40 = MemoryPatch::createWithHex("libUE4.so", 0xBD2CBC, "00 00 00 00");
    xcode.Bypass40.Modify();
    xcode.Bypass41 = MemoryPatch::createWithHex("libUE4.so", 0xBD2CC4, "00 00 00 00");
    xcode.Bypass41.Modify();
    xcode.Bypass42 = MemoryPatch::createWithHex("libUE4.so", 0xBD2CDC, "00 00 00 00");
    xcode.Bypass42.Modify();
    xcode.Bypass43 = MemoryPatch::createWithHex("libUE4.so", 0x7822F40, "00 00 00 00");
    xcode.Bypass43.Modify();
    xcode.Bypass44 = MemoryPatch::createWithHex("libUE4.so", 0x10FFC, "00 00 00 00");
    xcode.Bypass44.Modify();
    xcode.Bypass45 = MemoryPatch::createWithHex("libUE4.so", 0x262FFC, "00 00 00 00");
    xcode.Bypass45.Modify();
    xcode.Bypass46 = MemoryPatch::createWithHex("libUE4.so", 0x12E8CC, "00 00 00 00");
    xcode.Bypass46.Modify();
    xcode.Bypass47 = MemoryPatch::createWithHex("libUE4.so", 0x15FFC, "00 00 00 00");
    xcode.Bypass47.Modify();
    xcode.Bypass48 = MemoryPatch::createWithHex("libUE4.so", 0x30C, "00 00 00 00");
    xcode.Bypass48.Modify();
    xcode.Bypass49 = MemoryPatch::createWithHex("libUE4.so", 0x188, "00 00 00 00");
    xcode.Bypass49.Modify();
    xcode.Bypass50 = MemoryPatch::createWithHex("libUE4.so", 0x249FFC, "00 00 00 00");
    xcode.Bypass50.Modify();
    xcode.Bypass51 = MemoryPatch::createWithHex("libUE4.so", 0x24CFFC, "00 00 00 00");
    xcode.Bypass51.Modify();
    xcode.Bypass52 = MemoryPatch::createWithHex("libUE4.so", 0x24D07C, "00 00 00 00");
    xcode.Bypass52.Modify();
    xcode.Bypass53 = MemoryPatch::createWithHex("libUE4.so", 0x24DFFC, "00 00 00 00");
    xcode.Bypass53.Modify();
    xcode.Bypass54 = MemoryPatch::createWithHex("libUE4.so", 0x24D194, "00 00 00 00");
    xcode.Bypass54.Modify();
   return 0;
}


JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
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
            xcode.LessRecoil = MemoryPatch("libUE4.so", 0x14CB778, "\x00\x00\x00\x00", 4);
            xcode.LessRecoil.Modify();
            break;
        case 2:
            xcode.LessRecoil.Restore();
            break;
        case 20:
            xcode.Horizontal = MemoryPatch("libUE4.so", 0x3BACD60, "\x00\x00\x00\x00", 4);
            xcode.Horizontal.Modify();
            break;
        case 21:
            xcode.Horizontal.Restore();
            break;
        case 3:
            xcode.SmallCrosshair = MemoryPatch("libUE4.so", 0x14CBD30, "\x00\x00\xA0\x40", 4);
            xcode.SmallCrosshair.Modify();
            break;
        case 4:
            xcode.SmallCrosshair.Restore();
            break;
        case 5:
            xcode.Aimbot = MemoryPatch("libUE4.so", 0x287D21C, "\x00\x00\x00\x00", 4);
            xcode.Aimbot.Modify();
            break;
        case 6:
            xcode.Aimbot.Restore();
            break;
        case 7:
            xcode.Bullet_Track = MemoryPatch("libUE4.so", 0x3FEAB98, "\x42\x30\x00\x00", 4);
            xcode.Bullet_Track.Modify();
            break;
        case 8:
            xcode.Bullet_Track.Restore();
            break;
        case 9:
            xcode.MidNight = MemoryPatch("libUE4.so", 0x3E65550, "\xB0\xC6\x27\xB7", 4);
            xcode.MidNight.Modify();
            break;
        case 10:
            xcode.MidNight.Restore();
            break;
        case 11:
            xcode.Bypass.Modify();
            xcode.Bypass1.Modify();
            xcode.Bypass2.Modify();
            xcode.Bypass2.Modify();
            xcode.Bypass3.Modify();
            xcode.Bypass4.Modify();
            xcode.Bypass5.Modify();
            xcode.Bypass6.Modify();
            xcode.Bypass7.Modify();
            xcode.Bypass8.Modify();
            xcode.Bypass9.Modify();
            xcode.Bypass10.Modify();
            xcode.Bypass11.Modify();
            xcode.Bypass12.Modify();
            xcode.Bypass13.Modify();
            xcode.Bypass14.Modify();
            xcode.Bypass15.Modify();
            xcode.Bypass16.Modify();
            xcode.Bypass17.Modify();
            xcode.Bypass18.Modify();
            xcode.Bypass19.Modify();
            xcode.Bypass20.Modify();
            xcode.Bypass21.Modify();
            xcode.Bypass22.Modify();
            xcode.Bypass23.Modify();
            xcode.Bypass24.Modify();
            xcode.Bypass25.Modify();
            xcode.Bypass26.Modify();
            xcode.Bypass27.Modify();
            xcode.Bypass28.Modify();
            xcode.Bypass29.Modify();
            xcode.Bypass30.Modify();
            xcode.Bypass31.Modify();
            xcode.Bypass32.Modify();
            xcode.Bypass33.Modify();
            xcode.Bypass34.Modify();
            xcode.Bypass35.Modify();
            xcode.Bypass36.Modify();
            xcode.Bypass37.Modify();
            xcode.Bypass38.Modify();
            xcode.Bypass39.Modify();
            xcode.Bypass40.Modify();
            xcode.Bypass41.Modify();
            xcode.Bypass42.Modify();
            xcode.Bypass43.Modify();
            xcode.Bypass44.Modify();
            xcode.Bypass45.Modify();
            xcode.Bypass46.Modify();
            xcode.Bypass47.Modify();
            xcode.Bypass48.Modify();
            xcode.Bypass49.Modify();
            xcode.Bypass50.Modify();
            xcode.Bypass51.Modify();
            xcode.Bypass52.Modify();
            xcode.Bypass53.Modify();
            xcode.Bypass54.Modify();
            break;
        case 12:
            xcode.CarSpeed = MemoryPatch("libUE4.so", 0x4AA6B7C, "\x00\x00\x00\x00", 4);
            xcode.CarSpeed.Modify();
            break;
        case 13:
            xcode.CarSpeed.Restore();
            break;
        case 14:
            xcode.SpeedKnock = MemoryPatch("libUE4.so", 0x12CF9FC, "\x00\x00\x00\x00", 4);
            xcode.SpeedKnock.Modify();
            break;
        case 15:
            xcode.SpeedKnock.Restore();
            break;
        case 16:
            xcode.Flash = MemoryPatch("libUE4.so", 0x422A554, "\xE1\x5D\x27\x41", 4);
            xcode.Flash.Modify();
            break;
        case 17:
            xcode.Flash.Restore();
            break;
        case 18:
            xcode.CarJump = MemoryPatch("libUE4.so", 0x4AADD70, "\x00\x00\x00\x00", 4);
            xcode.CarJump.Modify();
            break;
        case 19:
            xcode.CarJump.Restore();
            break;
        case 25:
            xcode.UlrtaFlash = MemoryPatch("libUE4.so",0x3C0AA18,"\x00\x00\x00\x00",4);
            xcode.UlrtaFlash.Modify();
            break;
        case 26:
            xcode.UlrtaFlash.Restore();
            break;
    }
}

#endif


#ifndef SUPPORT_H
#define SUPPORT_H

#include "Memory.h"
#include <sys/uio.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include<math.h>
#include <stdlib.h>
#include <assert.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/syscall.h>
#include <unistd.h>
#include <sys/uio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <errno.h>
#include <stdio.h>
#include <sys/un.h>
#include <time.h>
#include <ctype.h>
#include <iostream>

struct Ulevel {
    uintptr_t addr;
    int size;
};

struct Vector4 {
    float X, Y, Z, W;
};
struct Vector3 {
    float X, Y, Z;
};
struct Vector2 {
    float X, Y;
};
struct D3DMatrix {

    float _11, _12, _13, _14;

    float _21, _22, _23, _24;

    float _31, _32, _33, _34;

    float _41, _42, _43, _44;

};
struct Vector4 rot;
struct Vector3 scale, tran;

//deta
int height = 1080;
int width = 2340;
int pid = 0;
int isBeta, nByte;
float mx = 0, my = 0, mz = 0;

struct D3DMatrix
ToMatrixWithScale(struct Vector3 translation, struct Vector3 scale, struct Vector4 rot) {
    struct D3DMatrix m;
    m._41 = translation.X;
    m._42 = translation.Y;
    m._43 = translation.Z;


    float x2 = rot.X + rot.X;
    float y2 = rot.Y + rot.Y;
    float z2 = rot.Z + rot.Z;


    float xx2 = rot.X * x2;
    float yy2 = rot.Y * y2;
    float zz2 = rot.Z * z2;

    m._11 = (1.0f - (yy2 + zz2)) * scale.X;
    m._22 = (1.0f - (xx2 + zz2)) * scale.Y;
    m._33 = (1.0f - (xx2 + yy2)) * scale.Z;


    float yz2 = rot.Y * z2;
    float wx2 = rot.W * x2;

    m._32 = (yz2 - wx2) * scale.Z;
    m._23 = (yz2 + wx2) * scale.Y;

    float xy2 = rot.X * y2;
    float wz2 = rot.W * z2;

    m._21 = (xy2 - wz2) * scale.Y;
    m._12 = (xy2 + wz2) * scale.X;


    float xz2 = rot.X * z2;
    float wy2 = rot.W * y2;

    m._31 = (xz2 + wy2) * scale.Z;
    m._13 = (xz2 - wy2) * scale.X;


    m._14 = 0.0f;
    m._24 = 0.0f;
    m._34 = 0.0f;
    m._44 = 1.0f;

    return m;
}

struct Vector3 mat2Cord(struct D3DMatrix pM1, struct D3DMatrix pM2) {
    struct Vector3 pOut;

    pOut.X = pM1._41 * pM2._11 + pM1._42 * pM2._21 + pM1._43 * pM2._31 + pM1._44 * pM2._41;
    pOut.Y = pM1._41 * pM2._12 + pM1._42 * pM2._22 + pM1._43 * pM2._32 + pM1._44 * pM2._42;
    pOut.Z = pM1._41 * pM2._13 + pM1._42 * pM2._23 + pM1._43 * pM2._33 + pM1._44 * pM2._43;

    return pOut;
}


char *getText(uintptr_t addr) {
    static char txt[42];
    memset(txt, 0, 42);
    char buff[41];
    pvm(addr + 4 + SIZE, &buff, 41);
    int i;
    for (i = 0; i < 41; i++) {
        if (buff[i] == 0)
            break;

        txt[i] = buff[i];

        if (buff[i] == 67 && i > 10)
            break;

    }
    txt[i + 1] = '\0';
    return txt;
}

float getDistance(struct Vector3 mxyz, struct Vector3 exyz) {
    return sqrt((mxyz.X - exyz.X) * (mxyz.X - exyz.X) + (mxyz.Y - exyz.Y) * (mxyz.Y - exyz.Y) +
                (mxyz.Z - exyz.Z) * (mxyz.Z - exyz.Z)) / 100;

}

struct Vector3 World2Screen(struct D3DMatrix viewMatrix, struct Vector3 pos) {
    struct Vector3 screen;
    float screenW = (viewMatrix._14 * pos.X) + (viewMatrix._24 * pos.Y) + (viewMatrix._34 * pos.Z) +
                    viewMatrix._44;

    if (screenW < 0.01f)
        screen.Z = 1;
    else
        screen.Z = 0;


    float screenX = (viewMatrix._11 * pos.X) + (viewMatrix._21 * pos.Y) + (viewMatrix._31 * pos.Z) +
                    viewMatrix._41;
    float screenY = (viewMatrix._12 * pos.X) + (viewMatrix._22 * pos.Y) + (viewMatrix._32 * pos.Z) +
                    viewMatrix._42;
    screen.Y = (height / 2) - (height / 2) * screenY / screenW;
    screen.X = (width / 2) + (width / 2) * screenX / screenW;


    return screen;

}

struct Vector2 World2ScreenMain(struct D3DMatrix viewMatrix, struct Vector3 pos) {
    struct Vector2 screen;
    float screenW = (viewMatrix._14 * pos.X) + (viewMatrix._24 * pos.Y) + (viewMatrix._34 * pos.Z) +
                    viewMatrix._44;

    float screenX = (viewMatrix._11 * pos.X) + (viewMatrix._21 * pos.Y) + (viewMatrix._31 * pos.Z) +
                    viewMatrix._41;
    float screenY = (viewMatrix._12 * pos.X) + (viewMatrix._22 * pos.Y) + (viewMatrix._32 * pos.Z) +
                    viewMatrix._42;
    screen.Y = (height / 2) - (height / 2) * screenY / screenW;
    screen.X = (width / 2) + (width / 2) * screenX / screenW;
    return screen;

}

struct D3DMatrix getOMatrix(uintptr_t boneAddr) {
    float oMat[11];
    pvm(boneAddr, &oMat, sizeof(oMat));
    rot.X = oMat[0];
    rot.Y = oMat[1];
    rot.Z = oMat[2];
    rot.W = oMat[3];

    tran.X = oMat[4];
    tran.Y = oMat[5];
    tran.Z = oMat[6];

    scale.X = oMat[8];
    scale.Y = oMat[9];
    scale.Z = oMat[10];

    return ToMatrixWithScale(tran, scale, rot);
}

int getI(uintptr_t address) {
    int buff;
    pvm(address, &buff, 4);
    return buff;
}

uintptr_t getA(uintptr_t address) {
    uintptr_t buff;
    pvm(address, &buff, SIZE);
    return buff;
}

struct Options {
    int aimbotmode;
    bool openState;
    int aimingState;
    bool tracingStatus;
    int priority;
    bool pour;
    int aimingRange;
    int BSpeed;
};
struct PlayerBone {
    bool isBone = false;
    Vector2 neck;
    Vector2 chest;
    Vector2 pelvis;
    Vector2 lSh;
    Vector2 rSh;
    Vector2 lElb;
    Vector2 rElb;
    Vector2 lWr;
    Vector2 rWr;
    Vector2 lTh;
    Vector2 rTh;
    Vector2 lKn;
    Vector2 rKn;
    Vector2 lAn;
    Vector2 rAn;
};

enum Mode {
    InitMode = 1,
    ESPMode = 2,
    HackMode = 3,
    StopMode = 4,
};

struct Request {
    int Mode;
    int ScreenWidth;
    int ScreenHeight;
};

struct SetValue {
    int mode;
    int type;
};

struct GrenadeData {
    int type;
    float Distance;
    Vector3 Location;
};

struct PlayerData {
    char PlayerNameByte[100];
    int TeamID;
    float Health;
    float Distance;
    bool isBot;
    Vector3 HeadLocation;
    PlayerBone Bone;
};

struct Response {
    bool Success;
    int PlayerCount;
    int GrenadeCount;
    float fov;
    PlayerData Players[maxplayerCount];
    GrenadeData Grenade[maxgrenadeCount];
};

#endif
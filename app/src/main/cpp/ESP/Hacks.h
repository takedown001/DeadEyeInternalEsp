#ifndef HACKS_H
#define HACKS_H
#include "support.h"
#include "init.h"
#include "import.h"
#include "socket.h"
#include "Memory.h"
#include "Process.h"
#include <thread>
#include <sys/uio.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <math.h>
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

kaddr pBase;
int myTeamID = 9999;
int myplayeradd = 0;
float Distance;
Vec3 Location;
char PlayerNameByte[100];
float Health;
bool isBot;
Vec3 HeadLocation;
PlayerBone Bone;
kaddr vworld;
kaddr CM;
kaddr JZDZ;
float x,y;
char extra[30];
int playerCount,botCount;
Color clr,clrHealth;
Response response{};

char* getNameByte(kaddr address)
{
    char static lj[64];
    memset(lj, 0, 64);
    unsigned short int nameI[32];
    pvm(address, nameI, sizeof(nameI));
    char s[10];
    int i;
    for (i = 0; i < 32; i++)
    {
        if (nameI[i] == 0)
            break;
        sprintf(s, "%d:", nameI[i]);
        strcat(lj, s);
    }
    lj[63] = '\0';

    return lj;
}

PlayerBone getPlayerBone(uintptr_t pBase, struct D3DMatrix viewMatrix)
{
    PlayerBone b;
    b.isBone = true;
    struct D3DMatrix oMatrix;
    kaddr boneAddr = getPtr(pBase + 0x310);
    struct D3DMatrix baseMatrix = getOMatrix(boneAddr + 0x140);
    int bones[] = { 4, 4, 1, 11, 32, 12, 33, 63, 62, 52, 56, 53, 57, 54, 58 };
    boneAddr = getPtr(boneAddr + 0x5a0) + 0x30;
    oMatrix = getOMatrix(boneAddr + (bones[0]) * 48);
    b.neck = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[1]) * 48);
    b.chest = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[2]) * 48);
    b.pelvis = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[3]) * 48);
    b.lSh = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[4]) * 48);
    b.rSh = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[5]) * 48);
    b.lElb = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[6]) * 48);
    b.rElb = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[7]) * 48);
    b.lWr = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[8]) * 48);
    b.rWr = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[9]) * 48);
    b.lTh = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[10]) * 48);
    b.rTh = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[11]) * 48);
    b.lKn = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[12]) * 48);
    b.rKn = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[13]) * 48);
    b.lAn = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    oMatrix = getOMatrix(boneAddr + (bones[14]) * 48);
    b.rAn = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    return b;

}

char* getText(kaddr addr) {
    static char txt[42];
    memset(txt, 0, 42);
    char buff[41];
    pvm(addr + 4+SIZE, &buff, 41);
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

kaddr getbsaddr(int pid){
    FILE *fp;
    uintptr_t addr = 0;
    char filename[40], buffer[1024];
    snprintf(filename, sizeof(filename), "/proc/%d/maps", pid);
    fp = fopen(filename, "rt");
    if (fp != NULL) {
        while (fgets(buffer, sizeof(buffer), fp)) {
            if (strstr(buffer, "libUE4.so")) {
                addr = (uintptr_t)strtoul(buffer, NULL, 16);
                break;
            }
        }
        fclose(fp);
    }
    return addr;
}

void DrawESP(ESP esp, int screenWidth, int screenHeight) {
    botCount = 0;
    playerCount = 0;
    target_pid = find_pid("com.pubg.imobile");
    if (target_pid == 0) { return; }
    libbase = getbsaddr(target_pid);
    vworld = getPtr(libbase + 0x728E890);
    CM = getPtr(vworld + 32);
    JZDZ = CM + 512;
    struct Vec3 exyz;
    struct D3DMatrix vMat;
    int type = 69;
    float textsize = screenHeight / 45;
    height = screenHeight;
    width = screenWidth;
    response.Success = false;
    response.PlayerCount = 0;
    response.GrenadeCount = 0;
    pvm(JZDZ, &vMat, sizeof(vMat));
    kaddr uWorlds = getPtr(libbase + 0x6E2192C);
    kaddr uLevel = getPtr(uWorlds + 32);
    kaddr gameInstance = getPtr(uWorlds + 36);
    kaddr playerController = getPtr(gameInstance + 96);
    kaddr playerCarry = getPtr(playerController + 0x20);
    kaddr uMyObject = getPtr(playerCarry + 0x320);
    kaddr entityEntry = getPtr(uLevel + 112);
    kaddr entityCount = getPtr(uLevel + 116);
    if (gameInstance == 0)
        return;
    if (entityCount < 0) {
        entityCount = 0;
    } else if (entityCount > 1024) {
        entityCount = 1024;
    }

    for (int i = 0; i < entityCount; i++) {
        pBase = getPtr(entityEntry + i * 4);
        kaddr gname_buff[30];
        kaddr gname = getPtr(libbase + 0x70D3994);
        pvm(gname, &gname_buff, sizeof(gname_buff));
        char name[100];
        int ids = Read<int>(pBase + 8 + 2 * SIZE);
        int page = ids / 0x4000;
        int index = ids % 0x4000;
        if (page < 1 || page > 30)
            continue;
        if (gname_buff[page] == 0)
            gname_buff[page] = getPtr(gname + page * 4);
        strcpy(name, getText(getPtr(gname_buff[page] + index * 4)));
        if (strstr(name, "BP_Grenade_Shoulei_C") || strstr(name, "BP_Grenade_Burn_C")) {
            pvm(getPtr(pBase + 0x140) + 0x150, &exyz, sizeof(exyz));
            Location = World2Screen(vMat, exyz);
            float screenW =
                    (vMat._14 * exyz.X) + (vMat._24 * exyz.Y) + (vMat._34 * exyz.Z + vMat._44);
            Distance = (screenW / 100);
            if (Distance > 150)
                continue;
            if (strstr(name, "Shoulei")) {
                type = 1;
            }
                else if (strstr(name, "Burn")) {
                type = 2;
            }
            if (isGrenadeWarning) {
                esp.DrawTText(Color::Red(), "Warning Throwable Nearby :",
                             Vec22(screenWidth / 5, screenHeight / 15), textsize);
                if (Location.Z != 2.0f) {
                    if (type == 1)
                        esp.DrawText(Color::Red(), "Grenade", Vec22(Location.X, Location.Y),
                                     textsize);
                    else
                        esp.DrawText(Color::Red(), "Molotov", Vec22(Location.X, Location.Y),
                                     textsize);
                }
            }
        }
        if (strstr(name, "BP_PlayerPawn_") && !strstr(name, "BP_PlayerPawn_Statue_")) {
            pvm(pBase + 0x918, healthbuff, sizeof(healthbuff));
            if (healthbuff[1] > 200.0f || healthbuff[1] < 50.0f || healthbuff[0] > healthbuff[1] ||
                healthbuff[0] < 0.0f)
                continue;
            Health = healthbuff[0] / healthbuff[1] * 100;
            int TeamID = Read<int>(pBase + 0x660);
            if (pBase == uMyObject) {
                myTeamID = TeamID;
                myplayeradd = pBase;
                continue;
            }
            if (TeamID == myTeamID) {
                myplayeradd = pBase;
                continue;
            }
            pvm(getPtr(pBase + 0x2804) + 0xfc, &exyz, sizeof(exyz));
            HeadLocation = World2Screen(vMat, exyz);
            float screenW =
                    (vMat._14 * exyz.X) + (vMat._24 * exyz.Y) + (vMat._34 * exyz.Z + vMat._44);
            Distance = (screenW / 100);
            if (Distance > 600)
                continue;
            pvm(pBase + 0x6D8, &isBot, sizeof(isBot));
            strcpy(PlayerNameByte, "66:111:116:");
            strcpy(PlayerNameByte, getNameByte(getPtr(pBase + 0x638)));
            if (strlen(PlayerNameByte) < 4)
                continue;
            float wzzb = Read<float>(pBase + 0x1764);
            if (wzzb != 479.5) {
                continue;
            }
            x = HeadLocation.X;
            y = HeadLocation.Y;
            float magic_number = (Distance);
            float mx = (screenWidth / 4) / magic_number;
            float my = (screenWidth / 1.38) / magic_number;
            float top = y - my + (screenWidth / 1.7) / magic_number;
            float bottom = y + my + screenHeight / 4 / magic_number;
            long int object = getPtr(pBase + 0x140);
            float d_x = Read<float>(object + 0xFC);
            float d_y = Read<float>(object + 0x100);
            float d_z = Read<float>(object + 0x104);
            float matrix[50];
            for (int i = 0; i < 16; i++) {
                float matrixaddr = Read<float>(JZDZ + i * 4);
                matrix[i] = matrixaddr;
            }

            float camear_r = matrix[3] * d_x + matrix[7] * d_y + matrix[11] * d_z + matrix[15];
            float r_x =
                    screenWidth + (matrix[0] * d_x + matrix[4] * d_y + matrix[8] * d_z +
                                   matrix[12]) / camear_r * screenWidth;
            float r_y =
                    screenHeight - (matrix[1] * d_x + matrix[5] * d_y + matrix[9] * (d_z - 5) +
                                    matrix[13]) / camear_r * screenHeight;
            float r_w =
                    screenHeight - (matrix[1] * d_x + matrix[5] * d_y + matrix[9] * (d_z + 205) +
                                    matrix[13]) / camear_r * screenHeight;
            float x2 = r_x - (r_y - r_w) / 4;
            float y2 = r_y;
            float w = (r_y - r_w) / 2;
            float w_2 = y2 - w;
            if (y2 > screenHeight - w - 20 && y2 < screenHeight + w + 20 &&
                x2 > screenWidth - w_2 - 20 && x2 < screenWidth + w_2 + 20) {
                clr.r = 0;
                clr.g = 232;
                clr.b = 0;
                clr.a = 255;
            } else {
                if (isBot) {
                    clr.r = 255;
                    clr.g = 255;
                    clr.b = 255;
                    clr.a = 255;
                } else {
                    clr.r = 255;
                    clr.g = 0;
                    clr.b = 0;
                    clr.a = 255;
                }
            }
            if (isBot) {
                botCount++;
            } else {
                playerCount++;
            }
            if (HeadLocation.Z != 1) {
                if (x > -50 && x < screenWidth + 50) {
                    if (isSkelton1) {
                        if (isBot) {
                            Color skoclr = Color::Cyan();
                            Bone = getPlayerBone(pBase, vMat);
                            esp.DrawCircle(skoclr, 3, Vec22(HeadLocation.X, HeadLocation.Y),
                                           screenHeight / 8 / magic_number);
                            esp.DrawLine(skoclr, 2, Vec22(x, y),
                                         Vec22(Bone.neck.X, Bone.neck.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.neck.X, Bone.neck.Y),
                                         Vec22(Bone.chest.X, Bone.chest.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.chest.X, Bone.chest.Y),
                                         Vec22(Bone.pelvis.X, Bone.pelvis.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.neck.X, Bone.neck.Y),
                                         Vec22(Bone.lSh.X, Bone.lSh.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.neck.X, Bone.neck.Y),
                                         Vec22(Bone.rSh.X, Bone.rSh.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.lSh.X, Bone.lSh.Y),
                                         Vec22(Bone.lElb.X, Bone.lElb.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.rSh.X, Bone.rSh.Y),
                                         Vec22(Bone.rElb.X, Bone.rElb.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.lElb.X, Bone.lElb.Y),
                                         Vec22(Bone.lWr.X, Bone.lWr.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.rElb.X, Bone.rElb.Y),
                                         Vec22(Bone.rWr.X, Bone.rWr.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.pelvis.X, Bone.pelvis.Y),
                                         Vec22(Bone.lTh.X, Bone.lTh.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.pelvis.X, Bone.pelvis.Y),
                                         Vec22(Bone.rTh.X, Bone.rTh.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.lTh.X, Bone.lTh.Y),
                                         Vec22(Bone.lKn.X, Bone.lKn.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.rTh.X, Bone.rTh.Y),
                                         Vec22(Bone.rKn.X, Bone.rKn.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.lKn.X, Bone.lKn.Y),
                                         Vec22(Bone.lAn.X, Bone.lAn.Y));
                            esp.DrawLine(skoclr, 2, Vec22(Bone.rKn.X, Bone.rKn.Y),
                                         Vec22(Bone.rAn.X, Bone.rAn.Y));
                        } else {
                            Color skclr = Color(255, 10, 0);
                            Bone = getPlayerBone(pBase, vMat);
                            esp.DrawCircle(skclr, 3, Vec22(HeadLocation.X, HeadLocation.Y),
                                           screenHeight / 8 / magic_number);
                            esp.DrawLine(skclr, 2, Vec22(x, y),
                                         Vec22(Bone.neck.X, Bone.neck.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.neck.X, Bone.neck.Y),
                                         Vec22(Bone.chest.X, Bone.chest.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.chest.X, Bone.chest.Y),
                                         Vec22(Bone.pelvis.X, Bone.pelvis.Y));
                            esp.DrawLine(skclr, 3, Vec22(Bone.neck.X, Bone.neck.Y),
                                         Vec22(Bone.lSh.X, Bone.lSh.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.neck.X, Bone.neck.Y),
                                         Vec22(Bone.rSh.X, Bone.rSh.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.lSh.X, Bone.lSh.Y),
                                         Vec22(Bone.lElb.X, Bone.lElb.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.rSh.X, Bone.rSh.Y),
                                         Vec22(Bone.rElb.X, Bone.rElb.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.lElb.X, Bone.lElb.Y),
                                         Vec22(Bone.lWr.X, Bone.lWr.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.rElb.X, Bone.rElb.Y),
                                         Vec22(Bone.rWr.X, Bone.rWr.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.pelvis.X, Bone.pelvis.Y),
                                         Vec22(Bone.lTh.X, Bone.lTh.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.pelvis.X, Bone.pelvis.Y),
                                         Vec22(Bone.rTh.X, Bone.rTh.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.lTh.X, Bone.lTh.Y),
                                         Vec22(Bone.lKn.X, Bone.lKn.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.rTh.X, Bone.rTh.Y),
                                         Vec22(Bone.rKn.X, Bone.rKn.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.lKn.X, Bone.lKn.Y),
                                         Vec22(Bone.lAn.X, Bone.lAn.Y));
                            esp.DrawLine(skclr, 2, Vec22(Bone.rKn.X, Bone.rKn.Y),
                                         Vec22(Bone.rAn.X, Bone.rAn.Y));
                        }

                    }

                    if (isPlayerBox) {
                        if (isBot) {
                            esp.DrawRRect(Color::Cyan(), screenHeight / 500, Vec22(x - mx, top),
                                          Vec22(x + mx, bottom));
                        } else {
                            esp.DrawRRect(Color::Red(), screenHeight / 500, Vec22(x - mx, top),
                                          Vec22(x + mx, bottom));
                        }
                    }

                    if (isPlayerHealth) {
                        float healthLength = screenWidth / 50;
                        if (healthLength < mx)
                            healthLength = mx;
                        if (Health < 25)
                            clrHealth = Color(200, 0, 0, 135);
                        else if (Health < 50)
                            clrHealth = Color(200, 145, 0, 135);
                        else if (Health < 75)
                            clrHealth = Color(200, 200, 0, 135);
                        else
                            clrHealth = Color(0, 200, 0, 135);
                        if (Health == 0)
                            esp.DrawText(Color::Black(), "KNOCKED",
                                         Vec22(x, top - screenHeight / 225), textsize);
                        else {
                            esp.DrawFilledRect3(clrHealth,
                                                Vec22(x - healthLength, top - screenHeight / 40),
                                                Vec22(x - healthLength +
                                                      (2 * healthLength) *
                                                      Health / 100, top - screenHeight / 230));
                            esp.DrawRect2(Color(0, 0, 0), screenHeight / 1800,
                                          Vec22(x - healthLength, top - screenHeight / 40),
                                          Vec22(x + healthLength, top - screenHeight / 230));
                        }
                    }
                    if (isPlayerName)
                        esp.DrawName(Color::White(), PlayerNameByte,
                                     TeamID,
                                     Vec22(x, top - screenHeight / 60), textsize);
                    if (isPlayerDist) {
                        sprintf(extra, "%0.0f m", Distance);
                        esp.DrawText(Color::Almond(), extra,
                                     Vec22(x, bottom + screenHeight / 60),
                                     textsize);
                    }
                    if (isPlayerLine) {
                        if (isBot) {
                            Color lcolor = Color::Cyan();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vec22(screenWidth / 2, screenHeight / 10.5 + 8),
                                         Vec22(x, top));
                        } else {
                            Color lcolor = Color::Red();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vec22(screenWidth / 2, screenHeight / 10.5 + 8),
                                         Vec22(x, top));
                        }
                    }
                    if (iscenter) {
                        if (isBot) {
                            Color lcolor = Color::Cyan();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vec22(screenWidth / 2, screenHeight / 2),
                                         Vec22(x, top));
                        } else {
                            Color lcolor = Color::Red();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vec22(screenWidth / 2, screenHeight / 2),
                                         Vec22(x, top));
                        }
                    }
                    if (isdown) {
                        if (isBot) {
                            Color lcolor = Color::Cyan();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vec22(screenWidth / 2, screenHeight), Vec22(x, top));
                        } else {
                            Color lcolor = Color::Red();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vec22(screenWidth / 2, screenHeight), Vec22(x, top));
                        }
                    }
                }
            }
        }
    }
    if(true) {
        sprintf(extra, "%d", playerCount + botCount);
        esp.DrawTText(Color::Red(), extra,
                      Vec22(screenWidth / 2, 80),
                      60);
        if (botCount + playerCount == 0) {
            esp.DrawTText(Color::Alien(), "0",
                          Vec22(screenWidth / 2, 80),
                          60);
        }
    }
}

#endif

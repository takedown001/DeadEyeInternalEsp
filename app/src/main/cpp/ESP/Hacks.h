#ifndef DEADEYE_HACKS_H
#define DEADEYE_HACKS_H

// non updated

#include "support.h"
#include "init.h"
#include "import.h"
#include "Memory.h"
#include "Process.h"
#include <thread>
#include <sys/uio.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <thread>
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

#define PI 3.141592653589793238

char *getNameByte(kaddr address) {
    char static lj[64];
    memset(lj, 0, 64);
    unsigned short int nameI[32];
    pvm(address, nameI, sizeof(nameI));
    char s[10];
    int i;
    for (i = 0; i < 32; i++) {
        if (nameI[i] == 0)
            break;
        sprintf(s, "%d:", nameI[i]);
        strcat(lj, s);
    }
    lj[63] = '\0';

    return lj;
}

PlayerBone getPlayerBone(uintptr_t pBase, struct D3DMatrix viewMatrix) {
    PlayerBone b;
    b.isBone = true;
    struct D3DMatrix oMatrix;
    kaddr boneAddr = getPtr(pBase + 0x320);
    struct D3DMatrix baseMatrix = getOMatrix(boneAddr + 0x150);
    int bones[] = {6, 5, 2, 12, 33, 13, 34, 64, 63, 53, 57, 54, 58, 55, 59};
    boneAddr = getPtr(boneAddr + 0x5b0);// + 0x30;
    oMatrix = getOMatrix(boneAddr + (bones[0]) * 48);
    b.neck = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //chest 1
    oMatrix = getOMatrix(boneAddr + (bones[1]) * 48);
    b.chest = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //pelvis 2
    oMatrix = getOMatrix(boneAddr + (bones[2]) * 48);
    b.pelvis = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //lSh 3
    oMatrix = getOMatrix(boneAddr + (bones[3]) * 48);
    b.lSh = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //rSh 4
    oMatrix = getOMatrix(boneAddr + (bones[4]) * 48);
    b.rSh = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //lElb 5
    oMatrix = getOMatrix(boneAddr + (bones[5]) * 48);
    b.lElb = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //rElb 6
    oMatrix = getOMatrix(boneAddr + (bones[6]) * 48);
    b.rElb = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //lWr 7
    oMatrix = getOMatrix(boneAddr + (bones[7]) * 48);
    b.lWr = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //rWr 8
    oMatrix = getOMatrix(boneAddr + (bones[8]) * 48);
    b.rWr = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //lTh 9
    oMatrix = getOMatrix(boneAddr + (bones[9]) * 48);
    b.lTh = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //rTh 10
    oMatrix = getOMatrix(boneAddr + (bones[10]) * 48);
    b.rTh = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //lKn 11
    oMatrix = getOMatrix(boneAddr + (bones[11]) * 48);
    b.lKn = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //rKn 12
    oMatrix = getOMatrix(boneAddr + (bones[12]) * 48);
    b.rKn = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //lAn 13
    oMatrix = getOMatrix(boneAddr + (bones[13]) * 48);
    b.lAn = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));
    //rAn 14
    oMatrix = getOMatrix(boneAddr + (bones[14]) * 48);
    b.rAn = World2ScreenMain(viewMatrix, mat2Cord(oMatrix, baseMatrix));

    return b;
}

kaddr getbsaddr(int pid) {
    FILE *fp;
    uintptr_t addr = 0;
    char filename[40], buffer[1024];
    snprintf(filename, sizeof(filename), "/proc/%d/maps", pid);
    fp = fopen(filename, "rt");
    if (fp != NULL) {
        while (fgets(buffer, sizeof(buffer), fp)) {
            if (strstr(buffer, "libUE4.so")) {
                addr = (uintptr_t) strtoul(buffer, NULL, 16);
                break;
            }
        }
        fclose(fp);
    }
    return addr;
}

void p_write(uintptr_t address, void *buffer, int size) {
    struct iovec local[1];
    struct iovec remote[1];

    local[0].iov_base = (void *) buffer;
    local[0].iov_len = size;
    remote[0].iov_base = (void *) address;
    remote[0].iov_len = size;

    //   vm_writev((void*)address, (void*)buffer, size);
    process_vm_writev(target_pid, local, 1, remote, 1, 0);
}

Vector2 getPointingAngle(Vector3 camera, Vector3 xyz, float distance) {
    Vector2 PointingAngle;
    float ytime = distance / 88000;

    xyz.Z = xyz.Z + 360 * ytime * ytime;

    float zbcx = xyz.X - camera.X;
    float zbcy = xyz.Y - camera.Y;
    float zbcz = xyz.Z - camera.Z;
    PointingAngle.Y = atan2(zbcy, zbcx) * 180 / PI; // 57.3
    PointingAngle.X = atan2(zbcz, sqrt(zbcx * zbcx + zbcy * zbcy)) * 180 / PI;

    return PointingAngle;

}

Options options
        {
                1, false, 3, false, 1, false, 150};

void DrawESP(ESP esp, int screenWidth, int screenHeight) {
    response.Success = false;

    botCount = 0;
    playerCount = 0;
    grenadeCount = 0;


    aimRadius = (float) options.aimingRange;
    aimFor = options.aimbotmode;
    aimbot = options.openState;
    aimWhen = options.aimingState;
    aimBy = options.priority;
    aimKnoced = options.pour;
    BSpeed = options.BSpeed;
    char loaded[1000], loadedpn[20];
    char name[100];
    int type = 69;

    float textsize = screenHeight / 50;
    height = screenHeight;
    width = screenWidth;


    target_pid = find_pid(version);

//    GNames = 0x7364894;   //GLobal
//    GWorld = 0x70AAE1C;
//    ViewMatrix = 0x753EF90;
//    ViewWorld = 0x753AC10;

    GNames = 0x742D894;
    GWorld = 0x7172AFC;   //BGMI
    ViewMatrix = 0x76085A0;
    ViewWorld = 0x7603580;

    if (target_pid == 0) {
        return;
    }
    libbase = getbsaddr(target_pid);


    kaddr uWorlds = getPtr(libbase + GWorld);
    kaddr uLevel = getPtr(uWorlds + 0x20);
    kaddr gameInstance = getPtr(uWorlds + 0x24);
    kaddr playerController = getPtr(gameInstance + 0x60);
    kaddr playerCarry = getPtr(playerController + 0x20);
    kaddr uMyObject = getPtr(playerCarry + 0x330);
    kaddr entityEntry = getPtr(uLevel + 0x70);
    kaddr entityCount = getPtr(uLevel + 0x74);


    if (gameInstance == 0)
        return;
    if (entityCount < 0) {
        entityCount = 0;
    } else if (entityCount > 1024) {
        entityCount = 1024;
    }


    getMatrix = getPtr(getPtr(libbase + ViewMatrix) + 0x68);
    kaddr vMatrix = getMatrix;   //Vew Matrix
    kaddr cLoc = vMatrix + 0x6c0;
    kaddr fovPntr = vMatrix + 0x5d0;
    vMatrix = vMatrix + 0x6d0;
    pvm(cLoc, &xyz, sizeof(xyz));


    kaddr gname_buff[30];
    kaddr gname = getPtr(libbase + GNames);
    pvm(gname, &gname_buff, sizeof(gname_buff));

    kaddr CM = getPtr(ViewWorld + 32);
    kaddr JZDZ = CM + 512;
    pvm(JZDZ, &vMat, sizeof(vMat));
    getEntityAddr = getPtr(getPtr(libbase + GWorld) + 0x20) + 0x70;

    if ((xyz.Z == 88.441124f || xyz.X == 0 || xyz.Z == 5278.43f || xyz.Z == 88.440918f) &&
        !isBeta) {
    }

    pvm(fovPntr, &response.fov, 4);
    pvm(vMatrix, &vMat, sizeof(vMat));

    Ulevel ulevel;
    pvm(getEntityAddr, &ulevel, sizeof(ulevel));


    if (ulevel.size < 1 || ulevel.size > 0x1000) {
    }
    memset(loaded, 0, 1000);
    float nearest = -1.0f;
    firing = 0;
    ads = 0;
    for (int i = 0; i < entityCount; i++) {
        uintptr_t pBase = getPtr(entityEntry + i * SIZE);
        if (!isValid32(pBase))
            continue;
        if (getI(pBase + SIZE) != 8)
            continue;
        int ids = getI(pBase + 8 + 2 * SIZE);
        int page = ids / 0x4000;
        int index = ids % 0x4000;
        if (page < 1 || page > 30)
            continue;
        if (gname_buff[page] == 0)
            gname_buff[page] = getA(gname + page * SIZE);
        strcpy(name, getText(getA(gname_buff[page] + index * SIZE)));
        if (strlen(name) < 5)
            continue;
//        if (strstr(name, "BP_Grenade_Shoulei_C") || strstr(name, "BP_Grenade_Burn_C") ||
//            strstr(name, "BP_Grenade_Stun_C") || strstr(name,"BP_Grenade_Smoke_C"))   // Grenade                                                                                        // Warning
//        {                       // Grenade Warning
//            pvm(getPtr(pBase + 0x14c) + 0x160, &exyz, sizeof(exyz));
//            Location = World2Screen(vMat, exyz);
//            Distance = getDistance(xyz, exyz);
//            if (Distance > 200)
//                continue;
//            if (strstr(name, "Shoulei"))
//                sprintf(extra, "Grenade [%0.0fM] ", Distance);
//            else if (strstr(name, "Burn"))
//                sprintf(extra, "Molotov [%0.0fM] ", Distance);
//            else if (strstr(name, "Stun"))
//                sprintf(extra, " Stun [%0.0fM] ", Distance);
//            else if (strstr(name, "Smoke"))
//                sprintf(extra, "Smoke [%0.0fM] ", Distance);
//            if (!isGrenadeWarning)
//                continue;
//            esp.DrawText(Color(255, 0, 0), "Warning ! There is Granade Near You",
//                         Vector22(screenWidth / 2, 170), 30);
//            if (Location.Z == 1.0f || Location.X > width + 100 || Location.X < -50) {
//                esp.DrawText(Color(255, 0, 0), extra,
//                                 Vector22(Location.X, Location.Y), textsize);
//                esp.DrawCircle(Color::RedT(), 1.5f, Vector22(Location.X, Location.Y), 10);
//            }
//        }
//        if (strstr(name, "BP_AirDropPlane_C") || strstr(name, "PlayerDeadInventoryBox_C") ||
//           strstr(name, "BP_AirDropBox_C")) {//Items
//            char ItemName[50];
//            pvm(getPtr(pBase + 0x14c) + 0x160, &exyz, sizeof(exyz));
//            Location = World2Screen(vMat, exyz);
//            if (Location.Z == 1.0f || Location.X > width + 100 || Location.X < -50)
//                continue;
//            float screenW =
//                    (vMat._14 * exyz.X) + (vMat._24 * exyz.Y) + (vMat._34 * exyz.Z + vMat._44);
//            Distance = (screenW / 100);
//            strcpy(ItemName, name);
//            if (Location.Z != 1.0f) {
//                esp.DrawItems(ItemName, Distance, Vector22(Location.X, Location.Y), itemSize);
//            }
//        }


//        if (strstr(name, "BP_MZJ_6X_Pickup") || strstr(name, "BP_MZJ_3X_Pickup") ||
//            strstr(name, "BP_Pistol_Flaregun_Wrapper_C") ||
//            strstr(name, "BP_AirDropBox_C") || strstr(name, "BP_Ammo_556mm_Pickup_C") ||
//            strstr(name, "BP_Ammo_762mm_Pickup")){//Items
//            char ItemName[50];
//            pvm(getPtr(pBase + 0x14c) + 0x160, &exyz, sizeof(exyz));
//            Location = World2Screen(vMat, exyz);
//            if (Location.Z == 1.0f || Location.X > width + 100 || Location.X < -50)
//                continue;
//            float screenW =
//                    (vMat._14 * exyz.X) + (vMat._24 * exyz.Y) + (vMat._34 * exyz.Z + vMat._44);
//            Distance = (screenW / 100);
//            if (Distance > 200)
//                continue;
//            strcpy(ItemName, name);
//            if (Location.Z != 1.0f) {
//                esp.DrawItems(ItemName, Distance, Vector22(Location.X, Location.Y), itemSize);
//            }
//        }


        if (strstr(name, "BP_Rifle_M762_Wrapper_C") || strstr(name, "BP_Rifle_SCAR_Wrapper_C") ||
            strstr(name, "BP_Rifle_AKM_Wrapper_C") ||
            strstr(name, "BP_Other_DP28_Wrapper_C") || strstr(name, "BP_Rifle_M416_Wrapper_C") ||
            strstr(name, "Firstaid_Pickup") || strstr(name, "BP_MZJ_3X_Pickup") || strstr(name, "BP_Pistol_Flaregun_Wrapper_C") ){//Items
            char ItemName[50];
            pvm(getPtr(pBase + 0x14c) + 0x160, &exyz, sizeof(exyz));
            Location = World2Screen(vMat, exyz);
            if (Location.Z == 1.0f || Location.X > width + 100 || Location.X < -50)
                continue;
            float screenW =
                    (vMat._14 * exyz.X) + (vMat._24 * exyz.Y) + (vMat._34 * exyz.Z + vMat._44);
            Distance = (screenW / 100);
            if (Distance > 200)
                continue;
            strcpy(ItemName, name);
            if (Location.Z != 1.0f) {
                esp.DrawItems(ItemName, Distance, Vector22(Location.X, Location.Y), itemSize);
            }
        }

        if (strstr(name, "BP_VH_Buggy") || (strstr(name, "VH_Dacia_")  || strstr(name, "VH_UAZ"))) {//Vehicle
            char VehicleName[50];
            pvm(getPtr(pBase + 0x14c) + 0x160, &exyz, sizeof(exyz));
            Location = World2Screen(vMat, exyz);
            if (Location.Z == 1.0f || Location.X > width + 200 || Location.X < -200)
                continue;
            Distance = getDistance(xyz, exyz);
            strcpy(VehicleName, name);
            if (Location.Z != 1.0f) {
                esp.DrawVehicles(VehicleName, Distance, Vector22(Location.X, Location.Y),
                                 vehicleSize);
            }
        }

        if (strstr(name, "BP_PlayerPawn") && !strstr(name, "BP_PlayerPawn_Statue_") || strstr(name, "BP_PlayerCharacter"))
        {

            int oType = getI(pBase + 0x70);
            if (getI(pBase + 0x964))
                continue;
            sprintf(loadedpn, "%lx,", pBase);
            if (strstr(loaded, loadedpn))
                continue;
            strcat(loaded, loadedpn);

            if (getI(pBase + 0x964))
                continue;
            pvm(pBase + 0x928, healthbuff, sizeof(healthbuff));
            if (healthbuff[1] > 200.0f || healthbuff[1] < 50.0f ||
                healthbuff[0] > healthbuff[1] || healthbuff[0] < 0.0f)
                continue;
            Health = healthbuff[0] / healthbuff[1] * 100;


            kaddr me = getI(pBase + 0xf8);
            int TeamID = Read<int>(pBase + 0x670);
            //       myTeamID =Read<int>(pBase + 0x644);
            if (me == 258) {

//                if (aimbot) {
//                    yawPitch = getA(pBase +
//                                    0x2fc /*Class: Pawn.Actor.Object to Controller* Controller Update 1.4 0x2ec*/) +
//                               0x2f8/*Class: Controller.Actor.Object to Rotator ControlRotation update 1.4 0x2e8*/;
//                    if (aimWhen == 3 || aimWhen == 2)
//                        pvm(pBase + 0xe20, &firing, 1); //bool bIsWeaponFiring; update 0xdf0
//                    if (aimWhen == 3 || aimWhen == 1)
//                        pvm(pBase + 0xa99, &ads, 1); //bool bIsGunADS; update 0xa89
//                }
                myTeamID = TeamID;
            } else if (me != 257)
                continue;


            if (TeamID == myTeamID)
                continue;
            pvm(getA(pBase + 0x285C) + 0x104, &exyz, sizeof(exyz));
            HeadLocation = World2Screen(vMat, exyz);
            Distance = getDistance(xyz, exyz);

            if (Distance > 600)
                continue;
            strcpy(PlayerNameByte, "66:111:116:");
            pvm(pBase + 0x6e8, &isBot, sizeof(isBot));
            if (!isBot) {
                Bone = getPlayerBone(pBase, vMat);
                strcpy(PlayerNameByte, getNameByte(getPtr(pBase + 0x648)));
                if (strlen(PlayerNameByte) < 4)
                    continue;
            }

//            if (HeadLocation.Z != 1.0f && (!aimKnoced || Health > 0) && aimbot) {
//                float centerDist = sqrt(
//                        (HeadLocation.X - width / 2) * (HeadLocation.X - width / 2) +
//                        (HeadLocation.Y - height / 2) * (HeadLocation.Y - height / 2));
//
//                if (centerDist < aimRadius) {
//                    if (aimBy != 1)
//                        centerDist = Distance;
//                    if (nearest > centerDist || nearest < 0) {
//                        nearest = centerDist;
//                        if (aimFor == 1)
//                            pointingAngle = getPointingAngle(xyz, exyz, Distance);
//                        else if (aimFor == 2) {
//
//                            uintptr_t boneAddr = getA(pBase + 0x320);   //SkeletalMeshComponent*
//                            struct D3DMatrix baseMatrix = getOMatrix(boneAddr + 0x150);
//                            boneAddr = getA(boneAddr + 0x5b0);
//                            struct D3DMatrix oMatrix = getOMatrix(boneAddr + 5 * 48);
//                            pointingAngle = getPointingAngle(xyz, mat2Cord(oMatrix, baseMatrix), Distance);
//                        }
//                        else if (aimFor == 3) {
//                            uintptr_t boneAddr = getA(pBase + 0x320);   //SkeletalMeshComponent*
//                            struct D3DMatrix baseMatrix = getOMatrix(boneAddr + 0x150);
//                            boneAddr = getA(boneAddr + 0x5b0);
//                            struct D3DMatrix oMatrix = getOMatrix(boneAddr + 63 * 48);
//                            pointingAngle = getPointingAngle(xyz, mat2Cord(oMatrix, baseMatrix), Distance);
//                        }
//                        else if (aimFor == 4)  {
//                            uintptr_t boneAddr = getA(pBase + 0x320);   //SkeletalMeshComponent*
//                            struct D3DMatrix baseMatrix = getOMatrix(boneAddr + 0x150);
//                            boneAddr = getA(boneAddr + 0x5b0);
//                            struct D3DMatrix oMatrix = getOMatrix(boneAddr + 2 * 48);
//                            pointingAngle = getPointingAngle(xyz, mat2Cord(oMatrix, baseMatrix),Distance);
//                        }
//                    }
//                }
//            }
//
//            if ((firing || ads) && nearest > 0) {
//                p_write(yawPitch, &pointingAngle, 8);
//            }
            float wzzb = Read<float>(pBase + 0x17A8);
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
                    botCount++;
                } else {
                    playerCount++;
                    clr.r = 0;
                    clr.g = 232;
                    clr.b = 0;
                    clr.a = 255;
                }
            }

            if (myTeamID == TeamID)
                continue;
            if (HeadLocation.Z != 1) {

                if (x > -50 && x < screenWidth + 50) {
                    if (isSkelton1) {
                        if (isBot) {
                            Color skoclr = Color::Cyan();
                            Bone = getPlayerBone(pBase, vMat);
                            esp.DrawCircle(skoclr, 3, Vector22(HeadLocation.X, HeadLocation.Y),
                                           screenHeight / 8 / magic_number);
                            esp.DrawLine(skoclr, 2, Vector22(x, y),
                                         Vector22(Bone.neck.X, Bone.neck.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.neck.X, Bone.neck.Y),
                                         Vector22(Bone.chest.X, Bone.chest.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.chest.X, Bone.chest.Y),
                                         Vector22(Bone.pelvis.X, Bone.pelvis.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.neck.X, Bone.neck.Y),
                                         Vector22(Bone.lSh.X, Bone.lSh.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.neck.X, Bone.neck.Y),
                                         Vector22(Bone.rSh.X, Bone.rSh.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.lSh.X, Bone.lSh.Y),
                                         Vector22(Bone.lElb.X, Bone.lElb.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.rSh.X, Bone.rSh.Y),
                                         Vector22(Bone.rElb.X, Bone.rElb.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.lElb.X, Bone.lElb.Y),
                                         Vector22(Bone.lWr.X, Bone.lWr.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.rElb.X, Bone.rElb.Y),
                                         Vector22(Bone.rWr.X, Bone.rWr.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.pelvis.X, Bone.pelvis.Y),
                                         Vector22(Bone.lTh.X, Bone.lTh.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.pelvis.X, Bone.pelvis.Y),
                                         Vector22(Bone.rTh.X, Bone.rTh.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.lTh.X, Bone.lTh.Y),
                                         Vector22(Bone.lKn.X, Bone.lKn.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.rTh.X, Bone.rTh.Y),
                                         Vector22(Bone.rKn.X, Bone.rKn.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.lKn.X, Bone.lKn.Y),
                                         Vector22(Bone.lAn.X, Bone.lAn.Y));
                            esp.DrawLine(skoclr, 2, Vector22(Bone.rKn.X, Bone.rKn.Y),
                                         Vector22(Bone.rAn.X, Bone.rAn.Y));
                        } else {
                            Color skclr = Color(255, 10, 0);
                            Bone = getPlayerBone(pBase, vMat);
                            esp.DrawCircle(skclr, 3, Vector22(HeadLocation.X, HeadLocation.Y),
                                           screenHeight / 8 / magic_number);
                            esp.DrawLine(skclr, 2, Vector22(x, y),
                                         Vector22(Bone.neck.X, Bone.neck.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.neck.X, Bone.neck.Y),
                                         Vector22(Bone.chest.X, Bone.chest.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.chest.X, Bone.chest.Y),
                                         Vector22(Bone.pelvis.X, Bone.pelvis.Y));
                            esp.DrawLine(skclr, 3, Vector22(Bone.neck.X, Bone.neck.Y),
                                         Vector22(Bone.lSh.X, Bone.lSh.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.neck.X, Bone.neck.Y),
                                         Vector22(Bone.rSh.X, Bone.rSh.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.lSh.X, Bone.lSh.Y),
                                         Vector22(Bone.lElb.X, Bone.lElb.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.rSh.X, Bone.rSh.Y),
                                         Vector22(Bone.rElb.X, Bone.rElb.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.lElb.X, Bone.lElb.Y),
                                         Vector22(Bone.lWr.X, Bone.lWr.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.rElb.X, Bone.rElb.Y),
                                         Vector22(Bone.rWr.X, Bone.rWr.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.pelvis.X, Bone.pelvis.Y),
                                         Vector22(Bone.lTh.X, Bone.lTh.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.pelvis.X, Bone.pelvis.Y),
                                         Vector22(Bone.rTh.X, Bone.rTh.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.lTh.X, Bone.lTh.Y),
                                         Vector22(Bone.lKn.X, Bone.lKn.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.rTh.X, Bone.rTh.Y),
                                         Vector22(Bone.rKn.X, Bone.rKn.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.lKn.X, Bone.lKn.Y),
                                         Vector22(Bone.lAn.X, Bone.lAn.Y));
                            esp.DrawLine(skclr, 2, Vector22(Bone.rKn.X, Bone.rKn.Y),
                                         Vector22(Bone.rAn.X, Bone.rAn.Y));
                        }
                    }

                    if (isPlayerBox) {
                        esp.DrawRRect(isBot ? Color::Cyan() : Color::Red(), screenHeight / 500,
                                      Vector22(x - mx, top),
                                      Vector22(x + mx, bottom));
                    }
                    if (iscross) {
                        esp.DrawCrosshair(Color(0, 0, 0, 255),
                                          Vector22(screenWidth / 2, screenHeight / 2), 42);
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
                            esp.DrawText(Color::RedLight(), "Knocked",
                                         Vector22(x, top - screenHeight / 225), textsize);
                        else {
                            esp.DrawFilledRect3(clrHealth,
                                                Vector22(x - healthLength,
                                                         top - screenHeight / 40),
                                                Vector22(x - healthLength +
                                                         (2 * healthLength) *
                                                         Health / 100,
                                                         top - screenHeight / 230));
                            esp.DrawRect2(Color(0, 0, 0), screenHeight / 1800,
                                          Vector22(x - healthLength, top - screenHeight / 40),
                                          Vector22(x + healthLength, top - screenHeight / 230));
                        }
                    }
                    if (isPlayerName)
                        esp.DrawName(Color::White(), PlayerNameByte,
                                     TeamID,
                                     Vector22(x, top - screenHeight / 60), textsize);
                    if (isPlayerDist) {
                        sprintf(extra, "%0.0f m", Distance);
                        esp.DrawText(Color::Almond(), extra,
                                     Vector22(x, bottom + screenHeight / 60),
                                     textsize);
                    }

                    if (isPlayerLine) {
                        if (isBot) {
                            Color lcolor = Color::Cyan();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vector22(screenWidth / 2, screenHeight / 10.5 + 8),
                                         Vector22(x, top));
                        } else {
                            Color lcolor = Color::Red();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vector22(screenWidth / 2, screenHeight / 10.5 + 8),
                                         Vector22(x, top));
                        }
                    }
                    if (iscenter) {
                        if (isBot) {
                            Color lcolor = Color::Cyan();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vector22(screenWidth / 2, screenHeight / 2),
                                         Vector22(x, top));
                        } else {
                            Color lcolor = Color::Red();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vector22(screenWidth / 2, screenHeight / 2),
                                         Vector22(x, top));
                        }
                    }
                    if (isdown) {
                        if (isBot) {
                            Color lcolor = Color::Cyan();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vector22(screenWidth / 2, screenHeight),
                                         Vector22(x, top));
                        } else {
                            Color lcolor = Color::Red();
                            esp.DrawLine(lcolor, screenHeight / 500,
                                         Vector22(screenWidth / 2, screenHeight),
                                         Vector22(x, top));
                        }
                    }
                }
            }

            if (HeadLocation.Z == 1.0f) {
                if(!isr360Alert)
                    continue;
                if (y > screenHeight - screenHeight / 12)
                    y = screenHeight - screenHeight / 12;
                else if (y < screenHeight / 12)
                    y = screenHeight / 12;
                if (x < screenWidth / 2) {
                    esp.DrawFilledCircle(Color::Red(), Vector22(screenWidth, y),
                                         screenHeight / 14);
                    sprintf(extra, "%0.0f m",Distance);

                    esp.DrawText(Color::White(), extra,
                                 Vector22(screenWidth - screenWidth / 80, y), textsize);

                } else {
                    esp.DrawFilledCircle(Color::Red(), Vector22(0, y),
                                         screenHeight / 14);
                    sprintf(extra, "%0.0f m",Distance);
                    esp.DrawText(Color::White(), extra,
                                 Vector22(screenWidth / 80, y), textsize);
                }
            }
            else if (x < -screenWidth / 10 || x > screenWidth + screenWidth / 10) {
                if(!isr360Alert)
                    continue;
                if (y > screenHeight - screenHeight / 12)
                    y = screenHeight - screenHeight / 12;
                else if (y < screenHeight / 12)
                    y = screenHeight / 12;
                if (x > screenWidth / 2) {
                    esp.DrawFilledCircle(Color::Red(), Vector22(screenWidth, y),
                                         screenHeight / 14);
                    sprintf(extra, "%0.0f m",Distance);

                    esp.DrawText(Color::White(), extra,
                                 Vector22(screenWidth - screenWidth / 80, y), textsize);

                } else {
                    esp.DrawFilledCircle(Color::Red(), Vector22(0, y),
                                         screenHeight / 14);
                    sprintf(extra, "%0.0f m",Distance);
                    esp.DrawText(Color::White(), extra,
                                 Vector22(screenWidth / 80, y), textsize);
                }
            }
        }
    }

    if (true)
    {
        sprintf(extra, "Enemy : %d", playerCount + botCount);
        esp.DrawTText(Color::Red(), extra,
                      Vector22(screenWidth / 2, 80),
                      35);
        if (botCount != 0 && playerCount == 0)
        {
            sprintf(extra, "Enemy : %d", playerCount + botCount);
            esp.DrawTText(Color::Cyan(), extra,
                          Vector22(screenWidth / 2, 80),
                          35);
        }
        else if (botCount + playerCount == 0)
        {
            esp.DrawTText(Color::Green(), "Enemy : 0",
                          Vector22(screenWidth / 2, 80),
                          35);
        }
    }

    if (aimbot) {
        esp.DrawCircle(Color::Red(), 1.5f,
                       Vector22(screenWidth / 2, screenHeight / 2), options.aimingRange);
    }
    if (iscross) {
        esp.DrawCrosshair(Color(0, 0, 0, 255), Vector22(screenWidth / 2, screenHeight / 2), 42);
    }
}


#endif //DEADEYE_HACKS_H


#ifndef INIT_H
#define INIT_H

char version[50] = "com.pubg.imobile";
float healthbuff[2];
kaddr GWorld = 0, GNames = 0, ViewMatrix = 0, ViewWorld = 0;
int myTeamID = 9999;
int myplayeradd = 0;
float Distance;
Vector3 Location;
char ItemName[50];
char PlayerNameByte[100];
float Health;
bool isBot;
Vector3 HeadLocation;
PlayerBone Bone;
float x, y;
kaddr getMatrix;
kaddr getEntityAddr;
Response response{};
char extra[30];
struct Vector3 exyz;
struct D3DMatrix vMat;
struct Vector3 xyz;
bool aimbot = false;
bool aimKnoced = false;
int firing = 0, ads = 0;
float aimRadius = 0;
Vector2 pointingAngle;
uintptr_t yawPitch = 0;
int aimFor = 1;
int aimBy = 1;
int aimWhen = 3;
int botCount, playerCount, grenadeCount;
Color clr, clrHealth;
#endif
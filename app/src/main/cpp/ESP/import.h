#ifndef IMPORT_H
#define IMPORT_H

#include <jni.h>
#include <string>
#include <cstdlib>
#include <unistd.h>
#include <sys/mman.h>
#include <android/log.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <cerrno>
#include <sys/un.h>
#include <cstring>
#include <string>
#include <cmath>
#include "struct.h"


bool isPlayerBox = false;
bool isPlayerLine = false;
bool isPlayerDist = false;
bool isPlayerHealth = false;
bool isPlayerName = false;
bool isSkelton1 = false;
bool iscenter = false;
bool isdown = false;
bool iscross = false;
bool isGrenadeWarning = false;

//weapons
bool isM416 = false;
bool isAkm = false;
bool isAug = false;
bool ismk47 = false ;
bool issks = false;
bool isscarl = false;
bool iskar98 = false;
bool ism416a4 = false;
bool isG36C = false;
bool isQBZ = false;
bool isGroza = false;
bool isBizon = false;
bool isuzi = false;
bool ismp5k = false;
bool isUmp = false;
bool ismk14= false;
bool ism249 = false;
bool isvector = false;
bool isdp28 =false;
bool isAWM = false;
bool isQBU = false;
bool isSLR = false;
bool isMini14 = false;
bool ism24 = false;
bool isvss = false;
bool iswin94 = false;
bool ism762 =false;
bool istommy =false;

//ammo
bool isssm =false;
bool isffm =false;
bool isACP =false;
bool is9mm =false;
bool is300magneum =false;
bool isarrow =false;
bool is12guage =false;

//armos
bool bagl1= false;
bool bagl2= false;
bool bagl3= false;
bool helmet1= false;
bool helmet2= false;
bool helmet3= false;
bool armor3 =false;
bool armor2 = false;
bool armor1 = false;

//scopes
bool hollow = false;
bool Caneted = false;
bool Reddot = false;
bool is8x = false;
bool is4x = false;
bool is2x = false;
bool is3x = false;
bool is6x = false;

//special

bool isflare = false;
bool Gilli = false;
bool Airdrop = false;
bool DropPlane = false;
bool Crate = false;
//throwadble
bool isspiketrap = false;
bool isstickeybomb =false;
bool isgranade = false;
bool issmoke = false;
bool ismolo = false;
#endif


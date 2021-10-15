package com.xcode.donators;

import androtrainer.Flags;
import androtrainer.MemoryScanner;
import androtrainer.Ranges;

public class InjectorService {

   // Sit Fly
   private static native String AAAA();

   private static native String AAAAA();

   private static native String AA();

   private static native String AAAAAAA();
   public static native void Hook(int num);
   // Anti Gravity
   private static native String BRUUUH();

   private static native String BRUH();

   private static native String BRUUH();

   private static native String BRUUUUH();

   private static native String XDD();

   private static native String XD();

   private static native String XXDD();

   // Headshot
   private static native String DUMMY();

   private static native String DUMMMY();

   private static native String DUUMMY();

   private static native String DUUMMMY();

   // Magic Bullet
   private static native String LMFAO();

   private static native String LMAO();

   private static native String LMAAAO();

   private static native String LMFFAO();

   static MemoryScanner mem = new MemoryScanner();
   public static void StartService() {
      mem.clearResults();
      mem.setRanges(new int[]{Ranges.ANONYMOUS});
      mem.searchNumber(AAAA(), Flags.FLOAT);
      mem.refineNumber(AAAAA(), Flags.FLOAT, -8);
      mem.refineNumber(AA(), Flags.FLOAT, 44);
      mem.editAll(AAAAAAA(), Flags.FLOAT, 44);
      mem.clearResults();

   }
   public static void AntiGravity(){

      // Anti Gravity
      mem.clearResults();
      mem.setRanges(new int[]{Ranges.C_ALLOC});
      mem.searchNumber(BRUUUH(), Flags.FLOAT);
      mem.refineNumber(BRUH(), Flags.FLOAT, 0x0);
      mem.refineNumber(BRUUH(), Flags.FLOAT, 0x0);
      mem.getResults(2);
      mem.editAll(BRUUUUH(), Flags.FLOAT, 0x0);
      mem.clearResults();
      mem.setRanges(new int[]{Ranges.ANONYMOUS});
      mem.searchNumber(XDD(), Flags.FLOAT);
      mem.refineNumber(XD(), Flags.FLOAT, 0x0);
      mem.getResults(1000);
      mem.editAll(XXDD(), Flags.FLOAT, 0x0);
      mem.clearResults();
   }

   public static void HeadShot(){
//      // HeadShot
      mem.clearResults();
      mem.setRanges(new int[]{Ranges.ANONYMOUS});
      mem.searchNumber(DUMMY(), Flags.FLOAT);
      mem.refineNumber(DUMMMY(), Flags.FLOAT, 28);
      mem.refineNumber(DUUMMY(), Flags.FLOAT, 32);
      mem.getResults(1000);
      mem.editAll(DUUMMMY(), Flags.FLOAT, 28);
      mem.editAll(DUUMMMY(), Flags.FLOAT, 32);
      mem.clearResults();

   }

   public static void magicbullet(){
      // Magic Bullet
      mem.clearResults();
      mem.setRanges(new int[]{Ranges.ANONYMOUS});
      mem.searchNumber(LMFAO(), Flags.FLOAT);
      mem.refineNumber(LMAO(), Flags.FLOAT, 4);
      mem.refineNumber(LMAAAO(), Flags.FLOAT, 8);
      mem.getResults(1000);
      mem.editAll(LMFFAO(), Flags.FLOAT, 4);
      mem.editAll(LMFFAO(), Flags.FLOAT, 8);
      mem.clearResults();

   }

}

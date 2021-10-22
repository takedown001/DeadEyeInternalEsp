package com.xcode.flash;

import androtrainer.Flags;
import androtrainer.MemoryScanner;
import androtrainer.Ranges;

public class InjectorService {

   static native String ValueOne();
   static native String ValueTwo();
   static native String ValueThree();
   static native String ValueFour();

   static MemoryScanner mem = new MemoryScanner();
   public static void StartService() {
      mem.clearResults();
      mem.setRanges(new int[]{Ranges.ANONYMOUS});
      mem.searchNumber(ValueOne(), Flags.FLOAT);
      mem.refineNumber(ValueTwo(), Flags.FLOAT, -8);
      mem.refineNumber(ValueThree(), Flags.FLOAT, 44);
      mem.editAll(ValueFour(), Flags.FLOAT, 44);
      mem.clearResults();
   }
}

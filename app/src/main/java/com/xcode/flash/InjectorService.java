package com.xcode.flash;

import com.memory.xploiter.Loader;

import androtrainer.Flags;
import androtrainer.MemoryScanner;
import androtrainer.Ranges;

public class InjectorService {

   public static native String ValueOne();

   public static native String ValueTwo();

   public static native String ValueThree();

   public static native String ValueFour();

   public static void StartService() {
      final MemoryScanner mem = new MemoryScanner();
      mem.clearResults();
      mem.setRanges(new int[]{Ranges.ANONYMOUS});
      mem.searchNumber(ValueOne(), Flags.FLOAT);
      mem.refineNumber(ValueTwo(), Flags.FLOAT, -8);
      mem.refineNumber(ValueThree(), Flags.FLOAT, 44);
      mem.editAll(ValueFour(), Flags.FLOAT, 44);
      mem.clearResults(); }
}

package com.cherrypicks.tcc.util;

public class Test {
    public static void main(final String[] args){
        final String uuid10 = getIdByUUID();
        final String millis = ""+getID();
        System.out.println(uuid10);
        System.out.println(millis);
    }

    public static String getIdByUUID() {

        final String uuid =  java.util.UUID.randomUUID().toString();
        final byte[] digest = uuid.getBytes();
        final long ll = java.nio.ByteBuffer.wrap(digest).asLongBuffer().get();
        final String trunc10 = (""+ll).substring(0,10);

        //System.out.println(ll);
        //System.out.println(trunc10);
        return trunc10;
    }

   private static final long LIMIT = 10000000000L;
   private static long last = 0;

    public static long getID() {
      // 10 digits.
      long id = System.currentTimeMillis() % LIMIT;
      if ( id <= last ) {
        id = (last + 1) % LIMIT;
      }
      return last = id;
    }
}

package com.estonianport.geservapp.commons;

import java.util.Random;

public class CodeGenerator {
   
    public static String getBase26Only4Letters() {
    	Random random = new Random();
    	char[] base26chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    	
        var sb = new StringBuilder(4);

        for (int i=0; i<4; i++) {
            sb.append(base26chars[random.nextInt(24)]);
        }

        return sb.toString();
    }
}

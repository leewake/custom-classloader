package com.pangpang.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by leewake on 2017/8/21 0021.
 */
public class CommonFindClass {

    public static byte[] commonFindClass(String byteCode_Path, String name){

        byte value[] = null;
        BufferedInputStream in = null;

        try {
            in = new BufferedInputStream(new FileInputStream(byteCode_Path + name + ".class"));
            value = new byte[in.available()];
            in.read(value);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{//释放资源
            try {
                if(null != in)  in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return value;
    }
}

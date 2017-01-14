package com.nyakokishi.zhihu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class SdCardUtil {
    public static void save(File file, byte[] bytes){
        if (file.exists()){
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

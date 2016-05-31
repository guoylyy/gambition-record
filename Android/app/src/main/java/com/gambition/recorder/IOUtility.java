package com.gambition.recorder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtility {
    public static String inputStream2String(InputStream is) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

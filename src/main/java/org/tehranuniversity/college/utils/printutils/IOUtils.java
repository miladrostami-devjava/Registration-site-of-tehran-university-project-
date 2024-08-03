package org.tehranuniversity.college.utils.printutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtils {
    public static void print(String message) {
        System.out.println(message);
    }

    public static String getInput() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bf.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}

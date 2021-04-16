package test;

import java.io.File;

public class test {

    public static void main(String[] args) {
        String FileDirectory = "/Users/xsun0035/Downloads/IMGDroid";
        File f = new File(FileDirectory);
        File[] list = f.listFiles();

        System.out.println(list.length);
    }
}

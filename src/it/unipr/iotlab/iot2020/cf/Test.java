package it.unipr.iotlab.iot2020.cf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String string = "1 supercalifagilistichespiralidoso 55555 Casa\n0 supercalifagilistichespiralidoso 55555 caca\n2 supercalifagilistichespiralidoso 55555 vaa";

        String[] s = string.split("\n", 0);

        List<String> goodsList = new ArrayList<String>();
        goodsList = Arrays.asList(s);
        System.out.println(goodsList.contains(1));

        

        for(String w : s){
            System.out.println(w);
        }
    }

}
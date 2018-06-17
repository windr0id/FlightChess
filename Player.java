package cc.windroid.flightchess;

import java.util.Random;

public class Player {
    final static int G = 0;
    final static int R = 1;
    final static int Y = 2;
    final static int B = 3;

    static Random r = new Random();
    static boolean isAI[] = {true, true, true, true};

    public void seed(int seed){
        r = new Random(seed);
    }

    public static int dise(int color){
        if(isAI[color]){
            return r.nextInt(6)+1;
        }else{
            //todo
            //get dise from service
        }
        return -1;
    }
}

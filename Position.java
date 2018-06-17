package cc.windroid.flightchess;

public class Position {
    static int[] c_r = {-1, -1, -1, -1};
    static int[] c_b = {-1, -1, -1, -1};
    static int[] c_g = {-1, -1, -1, -1};
    static int[] c_y = {-1, -1, -1, -1};

    private static int[] px = {80,80,80,102,165,220,280,325,305,305,325,390,445};
    private static int[] py = {500,445,390,325,305,305,325,285,225,165,120,95,95};
    private static int[] midx = {160, 211, 263, 318, 375, 440, 555, 617, 675, 730, 783, 835};
    private static int[] midy = {500};

    final static int MID_INDEX = 50;
    final static int END_INDEX = 55;

    public static void init(){
        Position.c_r[0] = 0;
        Position.c_g[0] = 0;
        Position.c_b[0] = 0;
        Position.c_y[0] = 0;
    }

    public static int get_x(int color,  int index){
        boolean onMid = false;
        if(index >= MID_INDEX) onMid = true;
        if(index >= END_INDEX) index = END_INDEX;
        switch (color){
            case Player.G:
                if(onMid) return midx[index-MID_INDEX];
                index += 3;
                break;
            case Player.R:
                if(onMid) return midy[0];
                index += 3+13;
                break;
            case Player.Y:
                if(onMid) return midx[MID_INDEX-index+11];
                index += 3+13+13;
                break;
            case Player.B:
                if(onMid) return midy[0];
                index += 3+13+13+13;
                break;
        }
        index %= 52;
        if(index < 13){
            return px[index];
        }else if(index < 26){
            return 1000-py[index-13];
        }else if(index < 39){
            return 1000-px[index-26];
        }else if(index < 52){
            return py[index-39];
        }
        return 0;
    }
    public static int get_y(int color, int index){
        boolean onMid = false;
        if(index >= MID_INDEX) onMid = true;
        if(index >= END_INDEX) index = END_INDEX;
        switch (color){
            case Player.G:
                if(onMid) return midy[0];
                index += 3;
                break;
            case Player.R:
                if(onMid) return midx[index-MID_INDEX];
                index += 3+13;
                break;
            case Player.Y:
                if(onMid) return midy[0];
                index += 3+13+13;
                break;
            case Player.B:
                if(onMid) return midx[MID_INDEX-index+11];
                index += 3+13+13+13;
                break;
        }
        index %= 52;
        if(index < 13){
            return py[index];
        }else if(index < 26){
            return px[index-13];
        }else if(index < 39){
            return 1000-py[index-26];
        }else if(index < 52){
            return 1000-px[index-39];
        }
        return 0;
    }
}

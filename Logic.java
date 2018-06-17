package cc.windroid.flightchess;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class Logic implements Runnable {
    private View chessView;

    private static int step = 1;
    public boolean over = false;

    private String[] infoText = {"绿色玩家", "红色玩家", "黄色玩家", "我方"};

    Logic(View v){
        this.chessView = v;
    }

    @Override
    public void run() {
        Log.i("debug", "logic run");
        playing(Player.B);
        playing(Player.G);
        playing(Player.R);
        playing(Player.Y);
        refreshButton();
    }

    private void playing(int color){
        if(over) return;
        refreshInfo(infoText[color]+"行动中");
        int dise = Player.dise(color);
        refreshDise(dise);
        refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 掷出了点数: "+String.valueOf(dise));
        refreshView(color, dise);

        boolean anotherChance = false;
        switch (color){
            case Player.G:
                anotherChance = checkAnotherChance(Position.c_g[0]);
                if(jump(Position.c_g[0])){
                    Position.c_g[0] += 4;
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 进行了一次飞行");
                }else if(fly(Position.c_g[0])){
                    Position.c_g[0] += 12;
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 进行了一次超级飞行");
                }
                if(Position.c_g[0] >= Position.END_INDEX){
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 胜利");
                    refreshInfo("["+infoText[color]+"] 胜利");
                    gameOver();
                }
                break;
            case Player.R:
                anotherChance = checkAnotherChance(Position.c_r[0]);
                if(jump(Position.c_r[0])){
                    Position.c_r[0] += 4;
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 进行了一次飞行");
                }else if(fly(Position.c_r[0])){
                    Position.c_r[0] += 12;
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 进行了一次超级飞行");
                }
                if(Position.c_r[0] >= Position.END_INDEX){
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 胜利");
                    refreshInfo("["+infoText[color]+"] 胜利");
                    gameOver();
                }
                break;
            case Player.Y:
                anotherChance = checkAnotherChance(Position.c_y[0]);
                if(jump(Position.c_y[0])){
                    Position.c_y[0] += 4;
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 进行了一次飞行");
                }else if(fly(Position.c_y[0])){
                    Position.c_y[0] += 12;
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 进行了一次超级飞行");
                }
                if(Position.c_y[0] >= Position.END_INDEX){
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 胜利");
                    refreshInfo("["+infoText[color]+"] 胜利");
                    gameOver();
                }
                break;
            case Player.B:
                anotherChance = checkAnotherChance(Position.c_b[0]);
                if(jump(Position.c_b[0])){
                    Position.c_b[0] += 4;
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 进行了一次飞行");
                }else if(fly(Position.c_b[0])){
                    Position.c_b[0] += 12;
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 进行了一次超级飞行");
                }
                if(Position.c_b[0] >= Position.END_INDEX){
                    refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 胜利");
                    refreshInfo("["+infoText[color]+"] 胜利");
                    gameOver();
                }
                break;
        }
        chessView.postInvalidate();
        sleep(800);
        if(anotherChance){
            refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 得到一次额外机会");
            playing(color);
        }
        if(dise == 6){
            refreshTable(String.valueOf(step++)+"\t["+infoText[color]+"] 得到一次额外机会");
            playing(color);
        }
    }

    private void gameOver(){
        over = true;
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putString("game", "over");
        msg.setData(b);
        MainActivity.handler.sendMessage(msg);
    }

    private boolean checkAnotherChance(int pos){
        int[] chance = {2, 6, 8, 15, 19, 21, 28, 32, 34};
        for (int aChance : chance) {
            if (pos == aChance) return true;
        }
        return false;
    }

    private boolean jump(int pos) {
        return pos < Position.MID_INDEX - 1 && (pos - 1) % 4 == 0;
    }

    private boolean fly(int pos){
        return pos == 17;
    }

    private void refreshView(int color, int dise){
        for(int i=0; i<dise; i++){
            switch (color){
                case Player.G:
                    Position.c_g[0] += 1;
                    break;
                case Player.R:
                    Position.c_r[0] += 1;
                    break;
                case Player.Y:
                    Position.c_y[0] += 1;
                    break;
                case Player.B:
                    Position.c_b[0] += 1;
                    break;
            }
            chessView.postInvalidate();
            sleep(200);
        }
    }


    private void refreshTable(String content){
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putString("table", content);
        msg.setData(b);
        MainActivity.handler.sendMessage(msg);
    }
    private void refreshInfo(String info){
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putString("info", info);
        msg.setData(b);
        MainActivity.handler.sendMessage(msg);
        sleep(500);
    }
    private void refreshDise(int dise){
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putInt("dise", dise);
        msg.setData(b);
        MainActivity.handler.sendMessage(msg);
        sleep(500);
    }
    private void refreshButton(){
        if(over) return;
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putString("logic", "over");
        msg.setData(b);
        MainActivity.handler.sendMessage(msg);
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

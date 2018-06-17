package cc.windroid.flightchess;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView t_info;
    TextView t_dise;
    LinearLayout table;
    ScrollView tablescroll;
    Button b_play;
    Logic logic;
    static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t_info = findViewById(R.id.textInfo);
        t_dise = findViewById(R.id.textDise);
        table = findViewById(R.id.table);
        tablescroll = findViewById(R.id.tablescroll);
        b_play = findViewById(R.id.bplay);
        b_play.setOnClickListener(this);
        handler = new MainHandler();
        init();
    }

    private void init(){
        logic = new Logic(findViewById(R.id.chessView));
        Position.init();
        findViewById(R.id.chessView).postInvalidate();
        table.removeAllViews();
        t_info.setText("准备开始");
        t_dise.setText("*");
        b_play.setText("开始游戏");
    }

    @Override
    public void onClick(View v) {
        if(logic.over){
            init();
            return;
        }
        new Thread(logic).start();
        b_play.setClickable(false);
        b_play.setText("行动中");
    }

    @SuppressLint("HandlerLeak")
    class MainHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();

            String s_logic = b.getString("logic");
            if("over".equals(s_logic)){
                b_play.setClickable(true);
                b_play.setText("投掷");
                t_info.setText("投掷骰子");
            }
            String s_info = b.getString("info");
            if(s_info != null){
                t_info.setText(s_info);
            }
            int dise = b.getInt("dise");
            if(dise != -1){
                t_dise.setText(String.valueOf(dise));
            }
            String s_table = b.getString("table");
            if(s_table != null){
                TextView text = new TextView(MainActivity.this);
                text.setText(s_table);
                table.addView(text);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tablescroll.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
            String s_ganme = b.getString("game");
            if("over".equals(s_ganme)){
                b_play.setClickable(true);
                b_play.setText("再来一把");
            }
        }
    }

}

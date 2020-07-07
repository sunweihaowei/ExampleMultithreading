package tracker.gps.com.examplemultithreading;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.button4)
    Button mButton4;
    @BindView(R.id.button5)
    Button mButton5;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //第一种
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("哈哈1");
                            }
                        });
                    }
                }.start();

                break;
            case R.id.button2:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //第二种
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("哈哈2");
                            }
                        });
                    }
                }.start();

                break;
            case R.id.button3:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //第三种
                        tv.postDelayed(new Runnable() {


                            @Override
                            public void run() {
                                tv.setText("哈哈3");
                            }
                        }, 1000);
                    }
                }.start();
                break;
            case R.id.button4:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //第四种
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("哈哈4");
                            }
                        });
                    }
                }.start();
                break;
            case R.id.button5:
                new TestTask().execute(3,4,5);
                break;
        }
    }

    public class TestTask extends AsyncTask<Integer, Integer, Integer> {//参数：传进去的参数，进度条，结果

        @Override
        protected void onPreExecute() {
            //main thread,before doInBackground()
            tv.setText("加载中……");
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            //work thread
            //download parse xml
            int i = integers[0] * 2 + 2;
            return i;
        }

        @Override
        protected void onPostExecute(Integer internalError) {
            super.onPostExecute(internalError);
            //main thread ,after doInBackground();
            tv.setText("加载完成.结果是" + internalError);
        }
    }
}
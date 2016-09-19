package com.funtory.rxAndroidTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.funtory.rxAndroidTest.viewmodel.RxViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by JuL on 16. 6. 10..
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_console)
    TextView tvConsole;

    RxViewModel rxViewModel = new RxViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_zip)
    void onZipTest(){


        rxViewModel.zip().subscribe(s -> {
            tvConsole.setText(s);
        });
    }
}

package com.funtory.rxAndroidTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JuL on 16. 6. 10..
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_console)
    TextView mTvConsole;

    StringBuilder mConsole;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_zip)
    void onZipTest(){
        /*
         * 시나리오
         * 1. 유저 기본 정보를 얻어오는 작업
         * 2. 유저 취미 정보를 얻어오는 작업
         * 3. 1, 2 작업이 완료 후 취합하여 ui에 뿌려주는 작업.
         *
         * 각 thread에서 동작하는 옵저버블 생성 후 zip 오퍼레이션을 사용해 후처리를 해본다.
         */

        mTvConsole.setText("");
        mConsole = new StringBuilder();
        Observable.zip(
                Observable.defer(() -> {
                    Log.i("test", Thread.currentThread().getName()+" / getUserInfo");

                    mConsole.append("\n");
                    mConsole.append("getUserInfo("+Thread.currentThread().getName()+")");

                    UserInfoModel model = new UserInfoModel();
                    model.name = "Jul.ee";
                    model.no = 1205;
                    return Observable.just(model);
                }).subscribeOn(Schedulers.newThread()),
                Observable.defer(() -> {
                    Log.i("test", Thread.currentThread().getName()+" / getUserFavoriteInfo");

                    mConsole.append("\n\n\n");
                    mConsole.append("getUserFavoriteInfo("+Thread.currentThread().getName()+")");

                    UserFavoriteModel model = new UserFavoriteModel();
                    model.title = "왕좌의 게임";
                    model.category = "미드";
                    return Observable.just(model);
                }).subscribeOn(Schedulers.newThread()),
                (resultA, resultB) -> "UserInfoModel : " + resultA + "\n UserFavoriteModel : " + resultB
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                s -> {
                    Log.e("test", Thread.currentThread().getName()+"\n result = "+s);

                    mConsole.append("\n\n\n");
                    mConsole.append(s+"("+Thread.currentThread().getName()+")");
                    mTvConsole.append(mConsole.toString());
                },
                e -> Log.e("test", "err = " + e.toString()),
                () -> Log.e("test", "complete")
        );
    }

    class UserInfoModel{
        String name;
        int no;

        @Override
        public String toString() {
            return "UserInfoModel{" +
                    "name='" + name + '\'' +
                    ", no=" + no +
                    '}';
        }
    }


    class UserFavoriteModel{
        String title;
        String category;

        @Override
        public String toString() {
            return "UserFavoriteModel{" +
                    "title='" + title + '\'' +
                    ", category='" + category + '\'' +
                    '}';
        }
    }
}

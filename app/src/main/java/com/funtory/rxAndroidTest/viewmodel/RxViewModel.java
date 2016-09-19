package com.funtory.rxAndroidTest.viewmodel;

import android.util.Log;

import com.funtory.rxAndroidTest.log.Logg;
import com.funtory.rxAndroidTest.model.UserFavoriteModel;
import com.funtory.rxAndroidTest.model.UserInfoModel;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by JuL on 2016. 9. 19..
 */

public class RxViewModel {

    /* 생성자 */
    public Observable<List<Integer>> just(){
        /*
         * from 과는 다르게, collector 자체가 한번 발행된다.
         */
        return Observable.just(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
    }

    public Observable<Integer> from(){
        /*
         * just 와는 다르게, collector 안의 값 하나하나가 각각 발행된다.
         */
        return Observable.from(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
    }




    /* 연산자 */
    public Observable<String> zip() {
        /*
         * 시나리오
         * 1. 유저 기본 정보를 얻어오는 작업
         * 2. 유저 취미 정보를 얻어오는 작업
         * 3. 1, 2 작업이 완료 후 취합하여 ui에 뿌려주는 작업.
         *
         * 각 thread에서 동작하는 옵저버블 생성 후 zip 오퍼레이션을 사용해 후처리를 해본다.
         */
        Observable<UserInfoModel> getUserInfoObs = Observable.create((Observable.OnSubscribe<UserInfoModel>) subscriber -> {
            Logg.i("getUserInfoObs call");

            for(int i = 0 ; i < 10000000 ; i++){ //네트워크 시간 지연 모사

            }

            UserInfoModel model = new UserInfoModel();
            model.name = "JuL";
            model.no = 1205;
            subscriber.onNext(model);
        }).subscribeOn(Schedulers.io());


        Observable<UserFavoriteModel> getUserFavoriteObs = Observable.create((Observable.OnSubscribe<UserFavoriteModel>) subscriber -> {
            Logg.i("getUserFavoriteObs call");

            UserFavoriteModel model = new UserFavoriteModel();
            model.title = "실리콘벨리";
            model.category = "미드";
            subscriber.onNext(model);
        }).subscribeOn(Schedulers.io());


        return Observable.zip(getUserInfoObs, getUserFavoriteObs, (userInfoModel, userFavoriteModel) -> {
            Logg.i("zip call =======================");
            Logg.i("userInfoModel = " + userInfoModel);
            Logg.i("userFavoriteModel = " + userFavoriteModel);
            return "userInfoModel = " + userInfoModel + "\n" + "userFavoriteModel = " + userFavoriteModel;
        }).observeOn(AndroidSchedulers.mainThread());
    }
}

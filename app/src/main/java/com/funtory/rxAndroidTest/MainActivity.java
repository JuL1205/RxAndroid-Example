package com.funtory.rxAndroidTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.funtory.rxAndroidTest.log.Logg;
import com.funtory.rxAndroidTest.viewmodel.RxViewModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
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

    @Bind(R.id.spinner)
    Spinner spinner;

    private RxViewModel rxViewModel = new RxViewModel();
    private String selectedOp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        initViews();

    }

    private void initViews(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operators, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @OnItemSelected(R.id.spinner)
    void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        selectedOp = (String) parent.getItemAtPosition(position);
        tvConsole.setText("");
    }

    @OnClick({R.id.btn_run})
    void onClick(View v){
        if(v.getId() == R.id.btn_run){
            if(selectedOp.equalsIgnoreCase("just")){
                rxViewModel.just().subscribe(integers -> {
                    tvConsole.append(integers.toString());
                    tvConsole.append("\n");
                });
            } else if(selectedOp.equalsIgnoreCase("from")){
                rxViewModel.from().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                });
            } else if(selectedOp.equalsIgnoreCase("from")){
                rxViewModel.from().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                });
            } else if(selectedOp.equalsIgnoreCase("map")){
                rxViewModel.map().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                });
            } else if(selectedOp.equalsIgnoreCase("flatMap")){
                rxViewModel.flatMap().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                });
            } else if(selectedOp.equalsIgnoreCase("concatMap")){
                rxViewModel.concatMap().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                });
            } else if(selectedOp.equalsIgnoreCase("zip")){
                rxViewModel.zip().subscribe(s -> {
                    tvConsole.append(s);
                    tvConsole.append("\n");
                });
            }
        }
    }

    

}

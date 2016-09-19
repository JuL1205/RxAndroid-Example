package com.funtory.rxAndroidTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.funtory.rxAndroidTest.viewmodel.RxViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

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

    private CompositeSubscription subscriptions = new CompositeSubscription();

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
        subscriptions.clear();
    }

    @OnClick({R.id.btn_run})
    void onClick(View v){
        if(v.getId() == R.id.btn_run){
            if(selectedOp.equalsIgnoreCase("just")){
                subscriptions.add(rxViewModel.just().subscribe(integers -> {
                    tvConsole.append(integers.toString());
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("from")){
                subscriptions.add(rxViewModel.from().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("from")){
                subscriptions.add(rxViewModel.from().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("map")){
                subscriptions.add(rxViewModel.map().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("flatMap")){
                subscriptions.add(rxViewModel.flatMap().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("concatMap")){
                subscriptions.add(rxViewModel.concatMap().subscribe(integer -> {
                    tvConsole.append(String.valueOf(integer));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("zip")){
                subscriptions.add(rxViewModel.zip().subscribe(s -> {
                    tvConsole.append(s);
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("publishSubject")){
                rxViewModel.publishSubject(s -> {
                    tvConsole.append(s);
                    tvConsole.append("\n");
                });
            } else if(selectedOp.equalsIgnoreCase("behaviorSubject")){
                rxViewModel.behaviorSubject(s -> {
                    tvConsole.append(s);
                    tvConsole.append("\n");
                });
            }
        }
    }

    

}

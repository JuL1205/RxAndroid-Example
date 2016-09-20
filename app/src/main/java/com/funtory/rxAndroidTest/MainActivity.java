package com.funtory.rxAndroidTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.funtory.rxAndroidTest.log.Logg;
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

    @Bind(R.id.group_check)
    LinearLayout groupCheck;

    @Bind(R.id.cb_check1)
    CheckBox cbCheck1;

    @Bind(R.id.cb_check2)
    CheckBox cbCheck2;

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
        initValues();
    }

    private void initValues(){
        tvConsole.setText("");
        subscriptions.clear();
        groupCheck.setVisibility(View.GONE);
    }

    @OnClick({R.id.btn_run})
    void onClick(View v){
        if(v.getId() == R.id.btn_run){
            if(selectedOp.equalsIgnoreCase("just")){
                subscriptions.add(rxViewModel.just().subscribe(is -> {
                    tvConsole.append(is.toString());
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("from")){
                subscriptions.add(rxViewModel.from().subscribe(i -> {
                    tvConsole.append(String.valueOf(i));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("from")){
                subscriptions.add(rxViewModel.from().subscribe(i -> {
                    tvConsole.append(String.valueOf(i));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("map")){
                subscriptions.add(rxViewModel.map().subscribe(i -> {
                    tvConsole.append(String.valueOf(i));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("flatMap")){
                subscriptions.add(rxViewModel.flatMap().subscribe(i -> {
                    tvConsole.append(String.valueOf(i));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("concatMap")){
                subscriptions.add(rxViewModel.concatMap().subscribe(i -> {
                    tvConsole.append(String.valueOf(i));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("zip")){
                subscriptions.add(rxViewModel.zip().subscribe(s -> {
                    tvConsole.append(s);
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("combineLatest")){
                groupCheck.setVisibility(View.VISIBLE);
                subscriptions.add(rxViewModel.combineLatest(cbCheck1, cbCheck2).subscribe(s -> {
                    tvConsole.append(s);
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("range")){
                subscriptions.add(rxViewModel.range().subscribe(i -> {
                    tvConsole.append(String.valueOf(i));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("interval")){
                subscriptions.add(rxViewModel.interval().subscribe(l -> {
                    tvConsole.append(String.valueOf(l));
                    tvConsole.append("\n");
                }));
            } else if(selectedOp.equalsIgnoreCase("timer")){
                subscriptions.add(rxViewModel.timer().subscribe(l -> {
                    tvConsole.append(String.valueOf(l));
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

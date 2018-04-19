package com.example.xuchichi.mytest.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.di.UserManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDaggerActivity extends AppCompatActivity {

    @Inject
    UserManager userManager;
    @BindView(R.id.btn)
    Button btn;
//
//
//    //    @Named("dev")
//    @TestAnnotation
//    @Inject
//    ApiService apiService_dev;

    //    @Named("release")
//    @ReleaseAnnotation
//    @Inject
//    ApiService apiService_release;

    private boolean is_dev = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);
//
//        DaggerUserComponent.builder()
//                .userModule(new UserModule(this))
//                .appComponent(MyApplication.getAppComponent())
//                .build()
//                .inject(this);
//
//        DaggerUserComponent.builder()
//                .userModule(new UserModule(this))
//                .httpModule(new HttpModule())
//                .build()
//                .inject(this);

        userManager.register();

//        if (is_dev) {
//
//            apiService_dev.register();
//
//        } else {
//            apiService_release.register();
//        }

//        userManager.register();

//        DaggerUserComponent.create().inject(this);

//        userManager = new UserManager();
//        apiService.register();

    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}

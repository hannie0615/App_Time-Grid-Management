package com.example.test01application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    LinearLayout drawer;
    Button btn_intent;
    Button btn_write;

    // 앱 잠금 On/Off
    boolean shouldLock = false;
    private AppLock appLock;    /*SharedPreferences*/


    // Activity 뜨고, 가장 먼저 실행되는 메소드
    @Override
    protected void onStart() {
        super.onStart();

        if(shouldLock&&appLock.isLocked()){ // 앱이 밖에 나갔다가 들어올 때
            // 잠금설정이 되어있으면,
            Intent intent = new Intent(MainActivity.this, LockActivity.class);
            intent.putExtra(INTENT_TYPE.TYPE, INTENT_TYPE.UNLOCK);
            startActivityForResult(intent, INTENT_TYPE.UNLOCK);         // 잠금 해제

        }
    }// OnStart() 끝


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // view 객체 연결
        drawerLayout = findViewById(R.id.drawerLayout);
        drawer = findViewById(R.id.drawer);
        btn_intent = findViewById(R.id.btn_intent);

        // '+' 서랍 버튼 클릭
        FloatingActionButton fab_write = (FloatingActionButton) findViewById(R.id.fab_write);
        fab_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawer);
            }
        });

        // '그리드' 버튼 클릭
        btn_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DaygridActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // '테이블' 버튼 클릭
        Button btn_grid = (Button) findViewById(R.id.btn_grid);
        btn_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TableActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // '잠금 아이콘' 버튼 클릭
        FloatingActionButton fab_lock = (FloatingActionButton) findViewById(R.id.fab_lock);
        fab_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.lock_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_on:
                                Intent intent = new Intent(MainActivity.this, LockActivity.class);
                                intent.putExtra(INTENT_TYPE.TYPE, INTENT_TYPE.SET_LOCK);
                                startActivityForResult(intent, INTENT_TYPE.SET_LOCK);   // 잠금 설정 활성화
                                break;
                            case R.id.item_off:
                                Intent intent2 = new Intent(MainActivity.this, LockActivity.class);
                                intent2.putExtra(INTENT_TYPE.TYPE, INTENT_TYPE.SET_UNLOCK);
                                startActivityForResult(intent2, INTENT_TYPE.SET_UNLOCK);   // 잠금 설정 비활성화
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                }); popupMenu.show();
            }
        });

        // '작성' 버튼 클릭
        btn_write = findViewById(R.id.btn_write);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.putExtra(INTENT_Category.TYPE, INTENT_Category.DO1);
                startActivity(intent);
                finish();
            }
        });

        appLock = new AppLock(this);

    }// OnCreate() 끝

    @Override
    protected void onStop() {
        super.onStop();

        shouldLock = true; // 잠금 활성화
    }


    // startActivityForResult 결과값을 받는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {                   // 내가 정해서 보낸 requestCode (어떤 Activity에서 결과를 보내왔는가)
            case INTENT_TYPE.SET_LOCK:                       // 잠금 설정을 하고 온 거면,
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "잠금 설정 적용 중", Toast.LENGTH_SHORT).show();
                    shouldLock = false;         // 어플을 사용 중이기 때문에, 잠글 필요 X
                    break;
                }
            case INTENT_TYPE.SET_UNLOCK:
                break;
            case INTENT_TYPE.UNLOCK:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
                    shouldLock = false;         // 어플을 사용 중이기 때문에, 잠글 필요 X
                    break;
                }
        }
    }

    class FABClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            // FAB Click 이벤트 처리 구간
        }
    }


}


package com.example.test01application;

import android.content.Context;
import android.content.SharedPreferences;

public class AppLock {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AppLock(Context context) {
        sharedPreferences = context.getSharedPreferences("AppLock", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /*** 잠금 설정 되어있는지 확인 == 비밀번호가 있는지 확인 ***/
    public boolean isLocked() {
        if(sharedPreferences.contains("pwd")){      // pwd라는 변수가 있으면,
            return true;                            // 잠금 설정이 되어있다.
        }
        return false;                               // 없으면, 잠금 설정이 안 되어있다.
    }

    /*** Shared Preferences에 비밀번호 저장 ***/
    public void setPassword(String pwd) {
        editor.putString("pwd", pwd);           // pwd라는 변수에 비밀번호 저장
        editor.commit();                            // SharedPreferences Commit
    }

    /*** Shared Preferences 초기화 ***/
    public void resetPassword(){
        editor.clear();
        editor.commit();
    }

    /*** 비밀번호가 맞는지 확인 ***/
    public boolean checkPassword(String pwd) {
        return sharedPreferences.getString("pwd", "").equals(pwd);
    }
}
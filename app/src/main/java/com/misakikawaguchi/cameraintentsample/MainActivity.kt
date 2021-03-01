package com.misakikawaguchi.cameraintentsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 画面がタップされた時の処理
    fun onCameraImageClick(view: View) {
        // Intentオブジェクトを生成
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // アクティビティを起動
        startActivityForResult(intent, 200)
    }
}
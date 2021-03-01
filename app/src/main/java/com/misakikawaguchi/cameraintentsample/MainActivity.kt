package com.misakikawaguchi.cameraintentsample

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    // 保存された画像のURI
    private var _imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 処理が元のアクティビティに戻ってきたときに実行されるメソッド
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // カメラアプリからの戻りでかつ撮影成功の場合
        if(requestCode == 200 && resultCode == RESULT_OK) {
            // 撮影されたビットマップデータを取得
            val bitmap = data?.getParcelableExtra<Bitmap>("data")
            // 画像を表示するImageViewを取得
            val ivCamera = findViewById<ImageView>(R.id.ivCamera)
            // 撮影された画像をImageVIewに設定
            ivCamera.setImageBitmap(bitmap)
        }
    }

    // 画面がタップされた時の処理
    fun onCameraImageClick(view: View) {
        // Intentオブジェクトを生成
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // アクティビティを起動
        startActivityForResult(intent, 200)
    }
}
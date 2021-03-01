package com.misakikawaguchi.cameraintentsample

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*

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
            // 画像を表示するImageVewを取得
            val ivCamera = findViewById<ImageView>(R.id.ivCamera)
            // フィールドの画像URIをImageVIewに設定
            ivCamera.setImageURI(_imageUri)
        }
    }

    // 画面がタップされた時の処理
    fun onCameraImageClick(view: View) {
        // WRITE_EXTERNAL_STORAGEの許可が降りていない場合
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // WRITE_EXTERNAL_STORAGEの許可を求めるダイアログを表示。その際、リクエストコードを2000に設定
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, 200)
            return
        }

        // 日時データを「yyyyMMddHHmmss」の形式に整形するフォーマッタを生成
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        // 現在の日時を取得
        val now = Date()
        // 取得した日時データを「yyyyMMddHHmmss」形式に整形した文字列を生成
        val nowStr = dateFormat.format(now)
        // ストレージに格納する画像のファイル名を生成。ファイルの一意性を確保するためにタイムスタンプの値を利用
        val fileName = "UseCameraActivityPhoto_${nowStr}.jpg"

        // ContentValuesオブジェクトを生成
        val values = ContentValues()
        /// 画像ファイル名を設定
        values.put(MediaStore.Images.Media.TITLE, fileName)
        // 画像ファイルの種類を設定
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

        //ContentResolverを使ってURIオブジェクトを生成
        _imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        // Intentオブジェクトを生成
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // アクティビティを起動
        startActivityForResult(intent, 200)
    }
}
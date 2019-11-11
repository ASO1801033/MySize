package jp.ac.asojuku.s.mysize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //再表示のたびに呼ばれるライフサイクルのコールバックメソッド
    override fun onResume() {
        super.onResume()

        //入力値を端末内に保存
        //共有プリファレンスのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        //共有プリファレンスのインスタンスから4つの保存済みの値を取り出す
        val editNeck = pref.getString("NECK", "") //首回りの保存値を取得
        val editSleeve = pref.getString("SLEEVE", "") //裄丈の保存値を取得
        val editWaist = pref.getString("WAIST", "") //胴回りの保存値を取得
        val editInseam = pref.getString("INSEAM", "") //股下の保存値を取得

        //取得した保存値で各入力欄の表示を上書きする
        neck.setText(editNeck) //取得した首回りの値で表示を更新
        sleeve.setText(editSleeve) //取得した裄丈の値で表示を更新
        waist.setText(editWaist) //取得した胴回りの値で表示を更新
        inseam.setText(editInseam) //取得した股下の値で表示を更新

        //保存ボタンが押された時の処理
        save.setOnClickListener{ onSaveTapped() }

        //身長アイコンボタンをクリック時の画面遷移を設定
        heightButton.setOnClickListener {
            //クリックされた時にOSを呼び出す処理を実装
            //インテントを定義(どこからどこまでなのか)
            val intent = Intent(this, HeightActivity::class.java) //MainActivityからHeightActivityまで

            //画面遷移メソッド(startActivity)を実行
            this.startActivity(intent);
        }
    }

    //保存ボタンが押されたらOSがコールバックする処理
    private fun onSaveTapped(){
        //画面表示の値を共有プリファレンスに保存する
        //共有プリファレンスのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this);

        //共有プリファレンスを更新(editメソッド)
        pref.edit{ //ラムダ式でインスタンスに対して引き続き処理を実行
            this.putString("NECK", neck.text.toString()); //首回りの入力値を共有プリファレンスに保存
            this.putString("SLEEVE", sleeve.text.toString()); //裄丈の入力値を共有プリファレンスに保存
            this.putString("WAIST", waist.text.toString()); //胴回りの入力値を共有プリファレンスに保存
            this.putString("INSEAM", inseam.text.toString()); //股下の入力値を共有プリファレンスに保存
        }
    }
}

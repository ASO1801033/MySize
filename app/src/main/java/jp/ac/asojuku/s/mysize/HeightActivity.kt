package jp.ac.asojuku.s.mysize

import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Spinner
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_height.*

class HeightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_height)
    }

    //再表示の際に呼ばれるライフサイクルのコールバックメソッド
    override fun onResume() {
        super.onResume()

        //スピナーにitem(選択肢)が選ばれた時のコールバックメソッドを設定
        spinner.onItemSelectedListener = //スピナーにアイテムを選んだ時の動きを持ったクラスの匿名インスタンスを代入する
            object : AdapterView.OnItemSelectedListener{
                override
                //アイテムを選んだ時の処理
                fun onItemSelected(
                    parent: AdapterView<*>?, //選択が発生したビュー(スピナーのこと)
                    view: View?, //選択されたビュー(選択したアイテム = 値のこと)
                    position: Int, //選んだ選択肢が何番目か
                    id: Long //選択されたアイテムのID
                    )
                {
                    //選択値を取得するためにスピナーのインスタンスを取得する
                    val spinner = parent as? Spinner //キャスト
                    val item = spinner?.selectedItem as? String //選択値(今回は170などの文字列)を取得

                    //取得した値を身長の値のテキストビューに上書きする
                    item?.let {
                        if(it.isNotEmpty()) height.text = it //身長の値が空文字でなければ、身長のテキストビュー(height)に代入
                    }
                }

                override
                fun onNothingSelected(parent: AdapterView<*>?) { //アイテムを何も選ばなかった時の処理
                    //何もしない
                }
            }

        //シークバーの処理を定義する
        //共有プリファレンスから身長設定を取得する(シークバーの表示値のため)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val heightVal = pref.getInt("HEIGHT", 160) //身長値を変数に保存
        height.text = heightVal.toString() //「身長」ラベルの値もこの取得値で上書き
        seekBar.progress = heightVal //シークバーの現在値(progress)も取得値で上書き

        //シークバーの値が更新されたらコールバックされるメソッドをもつ
        //匿名クラスのインスタンスを引き渡す
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                //1つめのオーバーライドメソッド
                override fun onProgressChanged(
                    seekBar: SeekBar?, //値が変化したシークバーのインスタンス
                    progress: Int, //値が変化したシークバーの現在値
                    fromUser: Boolean //ユーザーが操作したか
                ) {
                    //ユーザーの指定値で「身長」の表示をかえる
                    height.text = progress.toString() //heightラベルの表示値を上書き
                }

                //2つめのオーバーライドメソッド
                override fun onStartTrackingTouch(seekBar: SeekBar?) { }
                //3つめのオーバーライドメソッド
                override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            })

        //ラジオボタンの処理を実装する
        //ラジオグループに選択されたときに反応するコールバックメソッドを待機するリスナーを設定
        radioGroup.setOnCheckedChangeListener{
            //2つの引数(第1引数:ラジオボタングループ), (第2引数:選択されたラジオボタンのid)を受け取って実行する処理
            group, checkedId ->
                //「身長」ラベルを上書き(ラジオグループの選ばれたIDのボタンのText属性の値で上書き)
                height.text = findViewById<RadioButton>(checkedId).text
        }
    }

    //画面が閉じられるときに呼ばれるライフサイクルコールバックメソッドをオーバーライド
    override fun onPause() {
        super.onPause()
        //身長の現在値を共有プリファレンスに保存するメソッド
        //共有プリファレンスのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit {
            this.putInt("HEIGHT", height.text.toString().toInt())
        }
    }
}

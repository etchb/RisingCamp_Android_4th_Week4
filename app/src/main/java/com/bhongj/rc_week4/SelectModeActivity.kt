package com.bhongj.rc_week4

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.bhongj.rc_week4.databinding.ActivitySelectModeBinding

class SelectModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLotto.setOnClickListener {
            val intent = Intent(this, OnlyLottoActivity::class.java)
            intent.putExtra("mode", "onlyLotto")
            startActivity(intent)
        }
        binding.btnDart301.setOnClickListener {
            val intent = Intent(this, DartMainActivity::class.java)
            intent.putExtra("mode", "301")
            startActivity(intent)
        }
        binding.btnDart501.setOnClickListener {
            val intent = Intent(this, DartMainActivity::class.java)
            intent.putExtra("mode", "501")
            startActivity(intent)
        }
        binding.btnDartDual.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("알림")
            builder.setMessage("아직 구현되지 않았습니다.\n다른 모드를 이용해 주세요.")
            builder.setPositiveButton("뒤로 가기") { dialogInterface: DialogInterface, i: Int ->
            }
            builder.show()
        }
    }
}
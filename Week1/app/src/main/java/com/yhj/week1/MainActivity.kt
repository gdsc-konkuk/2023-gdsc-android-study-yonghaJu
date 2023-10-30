package com.yhj.week1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yhj.week1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.nameRepo = ServiceLocator.getService<NameRepository>()
        binding.action = { startActivity(Intent(this@MainActivity, EditActivity::class.java)) }
        setContentView(binding.root)
    }
}

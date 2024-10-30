package robert.paba.latihanfragment

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }


        val hal1 = findViewById<TextView>(R.id.hal1)
        val hal2 = findViewById<TextView>(R.id.hal2)
        val hal3 = findViewById<TextView>(R.id.hal3)

        //HALAMAN 1
        hal1.setOnClickListener {
            val fragment1 = GameFragment()
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.menu, fragment1, GameFragment::class.java.simpleName)
                commit()
            }
        }

        //HALAMAN 2
        hal2.setOnClickListener {
            val fragment2 = ResultFragment()
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.menu, fragment2, ResultFragment::class.java.simpleName)
                commit()
            }
        }

        //HALAMAN 3
        hal3.setOnClickListener {
            val fragment3 = SettingsFragment()
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.menu, fragment3, SettingsFragment::class.java.simpleName)
                commit()
            }
        }
    }
}
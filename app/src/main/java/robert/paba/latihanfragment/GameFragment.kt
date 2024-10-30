package robert.paba.latihanfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var scoreTextView: TextView
    private lateinit var buttons: List<Button>
    private var angkaList = mutableListOf<Int>()
    private var score: Int = 50
    private var matchedIndices = mutableListOf<Int>()
    private var selectedButtonIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        scoreTextView = view.findViewById(R.id.tv_score)
        val giveUpButton = view.findViewById<Button>(R.id.btn_give_up)
        buttons = listOf(
            view.findViewById(R.id.btn1),
            view.findViewById(R.id.btn2),
            view.findViewById(R.id.btn3),
            view.findViewById(R.id.btn4),
            view.findViewById(R.id.btn5),
            view.findViewById(R.id.btn6),
            view.findViewById(R.id.btn7),
            view.findViewById(R.id.btn8),
            view.findViewById(R.id.btn9),
            view.findViewById(R.id.btn10)
        )

        generateRandomNumbers()

        for (i in buttons.indices) {
            buttons[i].setOnClickListener {
                checkNumber(i)
            }
        }

        giveUpButton.setOnClickListener {
            goResultFragment()
        }

        return view
    }

    private fun generateRandomNumbers() {
        angkaList.clear()

        val limit = arguments?.getInt("LIMIT", 1) ?: 1

//        for (i in limit..5) {
//            angkaList.add(i)
//            angkaList.add(i)
//        }

        //generate angka sesuai batas bawah dan menduplikasinya
        while (angkaList.size < buttons.size) {
            for (i in limit..5) {
                angkaList.add(i)
                angkaList.add(i)
                if (angkaList.size >= buttons.size) break
            }
        }

        // Potong angkaList jika terlalu banyak dari yang diperlukan
        angkaList = angkaList.take(buttons.size).toMutableList()

        // Acak urutan angka sebelum ditampilkan di tombol
        angkaList.shuffle()

        for (i in buttons.indices) {
            buttons[i].text = ""
            buttons[i].isEnabled = true
        }
    }


    private fun checkNumber(index: Int) {
        // Cek tombol sudah di-pair atau tidak
        if (matchedIndices.contains(index)) {
            return
        }
        // Ambil angka dari tombol yang diklik
        val selectedNumber = angkaList[index]

        // Jika belum ada tombol yang dipilih
        if (selectedButtonIndex == null) {
            // Tampilkan angka dan simpan index yang dipilih
            buttons[index].text = selectedNumber.toString()
            buttons[index].isEnabled = false // Disable tombol sementara
            selectedButtonIndex = index
        } else {
            // Tampilkan angka pada tombol kedua yang dipilih
            buttons[index].text = selectedNumber.toString()

            // Cek apakah angka dari kedua tombol sama
            val previousSelectedNumber = angkaList[selectedButtonIndex!!]
            if (selectedNumber == previousSelectedNumber) {
                // Tebakan benar
                score += 10

                matchedIndices.add(index)
                matchedIndices.add(selectedButtonIndex!!)

                // Disable kedua tombol yang match
                buttons[index].isEnabled = false
                buttons[selectedButtonIndex!!].isEnabled = false

                if (matchedIndices.size == buttons.size) {
                    goResultFragment()
                }

            } else {
                // Tebakan salah
                score -= 5

                // Reset tombol jika tebakan salah
                buttons[index].text = ""
                buttons[selectedButtonIndex!!].text = ""
                buttons[selectedButtonIndex!!].isEnabled = true // Re-enable tombol sebelumnya
            }
            // Reset selected button setelah selesai
            selectedButtonIndex = null
        }

            updateScore()
        }

    private fun updateScore() {
        scoreTextView.text = "Score: $score"
    }

    //passing angka akhir untuk ke halaman 2
    private fun goResultFragment() {
        val fragment = ResultFragment()
        val bundle = Bundle()
        bundle.putInt("FINAL_SCORE", score) // Kirim skor terakhir
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.menu, fragment, ResultFragment::class.java.simpleName)
            commit()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
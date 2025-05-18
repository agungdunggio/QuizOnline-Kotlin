package com.example.quizonline

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizonline.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.log
import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quizModelList: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()

    }

    private fun setupRecyclerView(){
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase() {
        val db = Firebase.database(
            "https://quizonline-d0dc1-default-rtdb.asia-southeast1.firebasedatabase.app"
        )
        db.reference
            .get()
            .addOnSuccessListener { snapshot ->
                quizModelList.clear()

                if (snapshot.exists()) {
                    snapshot.children.forEach { child ->
                        child.getValue(QuizModel::class.java)?.let { quiz ->
                            quizModelList.add(quiz)
                        }
                    }
                }
                setupRecyclerView()
            }
    }

//    private fun getDataFromFirebase(){
//
//    FirebaseDatabase.getInstance().reference
//        .get()
//        .addOnSuccessListener { dataSnapshot ->
//            if (dataSnapshot.exists()) {
//                for (snapshot in dataSnapshot.children) {
//                    val quizModel = snapshot.getValue(QuizModel::class.java)
//                    if (quizModel != null) {
//                        quizModelList.add(quizModel)
//                        // Cetak setiap QuizModel yang berhasil diambil
//                        Log.d("FirebaseData", "QuizModel: id=${quizModel.id}, title=${quizModel.title}")
//                    }
//                }
//            } else {
//                Log.d("FirebaseData", "Tidak ada data di Firebase")
//            }
//
//            Log.d("FirebaseData", "Total QuizModel: ${quizModelList.size}")
//            setupRecyclerView()
//        }
//        .addOnFailureListener { exception ->
//            Log.e("FirebaseError", "Gagal mengambil data: ${exception.message}")
//        }
//
////        val listQuestionModel = mutableListOf<QuestionModel>()
////        listQuestionModel.add(QuestionModel("sasadad", mutableListOf("dsd","sdsd","asd","asd"),"dsd"))
////        listQuestionModel.add(QuestionModel("sasadad", mutableListOf("dsd","sdsd","asd","asd"),"dsd"))
////        listQuestionModel.add(QuestionModel("sasadad", mutableListOf("dsd","sdsd","asd","asd"),"dsd"))
////
////        quizModelList.add(QuizModel("1","saddasd", "jsabkjsndadkjnas", "10", listQuestionModel))
////
////        setupRecyclerView()
//    }
}
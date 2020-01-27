package br.aula.trabalhojogo

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import br.aula.trabalhojogo.ContatoRepository
import br.aula.trabalhojogo.Jogo
import br.aula.trabalhojogo.R
import kotlinx.android.synthetic.main.activity_jogos.*
import java.text.SimpleDateFormat
import java.util.*

class JogosActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var jogo: Jogo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogos)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent?.getSerializableExtra("jogo") != null) {
            jogo = intent?.getSerializableExtra("jogo") as Jogo
            txtNome?.setText(jogo?.nome)
            txtPlataforma?.setText(jogo?.plataforma)
            //data = cal.timeInMillis
        }else{
            jogo = Jogo()
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        txtLancamento.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@JogosActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        btnCadastro.setOnClickListener {
            jogo?.nome = txtNome?.text.toString()
            jogo?.plataforma = txtPlataforma?.text.toString()
            jogo?.data= cal.timeInMillis

            if(jogo?.id?.equals(0)!!){
                ContatoRepository(this).create(jogo!!)
            }else{
                ContatoRepository(this).update(jogo!!)
            }

            finish()
        }

    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtLancamento.text = sdf.format(cal.getTime())
    }
}
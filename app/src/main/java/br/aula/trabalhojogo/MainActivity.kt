package br.aula.trabalhojogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import br.aula.trabalhojogo.JogosActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.novo -> {
                val intent = Intent(this, JogosActivity::class.java)
                startActivity(intent)
                return false
            }

            R.id.novo -> {
                Toast.makeText(this, "Enviar", Toast.LENGTH_LONG).show()
                return false
            }

            R.id.lista -> {
                Toast.makeText(this, "Receber", Toast.LENGTH_LONG).show()
                return false
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val jogos = ContatoRepository(this).findAll()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jogos)

        lista.adapter = adapter
        adapter.notifyDataSetChanged()

        lista.setOnItemClickListener{ _, _, position, id ->
            val intent = Intent (this, JogosActivity::class.java)
            intent.putExtra("contato", jogos.get(position))
            startActivity(intent)
        }
    }
}

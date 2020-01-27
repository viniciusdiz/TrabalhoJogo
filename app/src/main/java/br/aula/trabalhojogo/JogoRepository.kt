package br.aula.trabalhojogo

import android.content.Context
import br.aula.trabalhojogo.ConstantsDb.JOGOS_DB_TABLE
import org.jetbrains.anko.db.*

class ContatoRepository(val context: Context) {

    fun findAll() : ArrayList<Jogo> = context.database.use {
        val jogos = ArrayList<Jogo>()

        select(JOGOS_DB_TABLE, "id", "nome", "plataforma", "data")
            .parseList(object: MapRowParser<List<Jogo>> {
                override fun parseRow(columns: Map<String, Any?>): List<Jogo> {
                    val jogo = Jogo(
                        id = columns.getValue("id").toString()?.toLong(),
                        nome = columns.getValue("nome") as String,
                        plataforma = columns.getValue("plataforma") as String
                        //data = (columns.getValue("datanascimento") as String)?.toLong()
                    )

                        jogos.add(jogo)
                        return jogos

                }
            })

        jogos
    }


    fun create(jogo: Jogo) = context.database.use {
        insert(JOGOS_DB_TABLE,
            "nome" to jogo.nome,
            "plataforma" to jogo.plataforma
            //"data" to jogo.data
        )
    }

    fun update(jogo: Jogo) = context.database.use {
        val updateResult = update(JOGOS_DB_TABLE)
        insert(JOGOS_DB_TABLE,
            "nome" to jogo.nome,
            "plataforma" to jogo.plataforma
            //"data" to jogo.data
        )
    }

    fun delete(id: Long) = context.database.use {
        delete(JOGOS_DB_TABLE, whereClause = "id = {jogoId}", args = *kotlin.arrayOf("jogoId" to id))
    }
}

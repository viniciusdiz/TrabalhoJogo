package br.aula.trabalhojogo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.Telephony
import br.aula.trabalhojogo.ConstantsDb.JOGOS_DB_NAME
import br.aula.trabalhojogo.ConstantsDb.JOGOS_DB_TABLE
import org.jetbrains.anko.db.*
import org.w3c.dom.Text
import java.sql.Types


class BancoDadosHelper(context: Context) :
    ManagedSQLiteOpenHelper(ctx = context,
        name = "$JOGOS_DB_NAME", version = 1) {

    private val scriptSQLCreate = arrayOf(
        "INSERT INTO $JOGOS_DB_TABLE VALUES(1,'Gof of War','PS4',NULL);",
        "INSERT INTO $JOGOS_DB_TABLE VALUES(2,'GEARS 5','XONE',NULL);")


    //singleton da classe
    companion object {
        private var instance: BancoDadosHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): BancoDadosHelper {
            if (instance == null) {
                instance = BancoDadosHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação de tabelas
        db.createTable("$JOGOS_DB_TABLE", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "nome" to TEXT,
            "plataforma" to TEXT,
            "data" to INTEGER
        )

        // insere dados iniciais na tabela
        scriptSQLCreate.forEach {sql -> db.execSQL(sql) }

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("$JOGOS_DB_TABLE ", true)
        onCreate(db)
    }

}

val Context.database: BancoDadosHelper get() = BancoDadosHelper.getInstance(getApplicationContext())
package br.aula.trabalhojogo

import java.io.Serializable

data class Jogo(
    var id: Long = 0,
    var nome: String? = null,
    var plataforma: String? = null,
    var data: Long? = null) : Serializable {

    override fun toString(): String {
        return nome.toString()
    }
}

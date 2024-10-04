package com.example.aulasqlite.bancodedados

import android.content.Context
import android.util.Log
import com.example.aulasqlite.model.Produto
import java.lang.Exception

class ProdutoDAO(context:Context):IProdutoDAO {
    val escrita = DatabaseHelper(context).writableDatabase
    val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(produto: Produto): Boolean {
        val titulo = produto.titulo

        try{
            val sql = "INSERT INTO produtos VALUES(null,'$titulo','512gb')"
            escrita.execSQL(sql)
            Log.i("db_info","Produto salvo")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("db_info","Erro ao salvar produto")
            return false
        }

        return true

    }

    override fun atualizar(produto: Produto): Boolean {
        TODO("Not yet implemented")
    }

    override fun remover(idProduto: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun listar(): List<Produto> {
        TODO("Not yet implemented")
    }

}
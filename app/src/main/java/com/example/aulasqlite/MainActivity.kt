
package com.example.aulasqlite

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulasqlite.bancodedados.DatabaseHelper
import com.example.aulasqlite.bancodedados.ProdutoDAO
import com.example.aulasqlite.model.Produto
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    //aula 01/10/24
    private val bancoDados by lazy{
        DatabaseHelper(this)
    }

    private lateinit var editNomeProduto:EditText
    private lateinit var btnSalvar:Button
    private lateinit var btnListar:Button
    private lateinit var btnAtualizar:Button
    private lateinit var btnDeletar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editNomeProduto = findViewById(R.id.editNomeProduto)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnListar = findViewById(R.id.btnListar)
        btnAtualizar = findViewById(R.id.btnAtualizar)
        btnDeletar = findViewById(R.id.btnDeletar)


        btnSalvar.setOnClickListener{
            salvar()
        }

        btnListar.setOnClickListener{
            listar()
        }

        btnAtualizar.setOnClickListener{
            atualizar()
        }

        btnDeletar.setOnClickListener{
            remover()
        }


    }

    private fun salvar(){
        val nomeProduto = editNomeProduto.text.toString()

        val produtoDAO = ProdutoDAO(this)

        val produto = Produto(
            -1,nomeProduto,"descrição..."
        )

        produtoDAO.salvar(produto)


    }

    private fun listar(){
        val sql = "SELECT * FROM produtos"
        val cursor = bancoDados.readableDatabase.rawQuery(sql, null)

        while (cursor.moveToNext()){
            val idProduto = cursor.getInt(0)
            val titulo = cursor.getString(1)
            val descricao = cursor.getString(2)

            Log.i("db_info","PRODUTOS: $idProduto - $titulo - $descricao")
        }
    }

    private fun atualizar(){
        val nomeProduto = editNomeProduto.text.toString()

        val sql = "UPDATE produtos SET titulo = '$nomeProduto' WHERE id_produto=2;"

        try{
            bancoDados.writableDatabase.execSQL(sql)
            Log.i("db_info","Registro atualizado com suceso.")
        }catch (e:Exception){
            e.printStackTrace()
            Log.i("db_info","Error ao atualizar..")
        }
    }

    private fun remover(){
        val sql = "DELETE FROM produtos WHERE id_produto=1"

        try {
            bancoDados.writableDatabase.execSQL(sql)
            Log.i("db_info","Produto removido com sucesso..")
        }catch (e:Exception){
            e.printStackTrace()
            Log.i("db_info","Error ao excluir..")
        }
    }

}
package org.jsantamariap.eh_ho

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button_login)

        // 1. Listener a partir definición de interfaz a partir de una clase
        //button.setOnClickListener(Listener())

        // 2. Listener a partir definición de una clase anónima
        /*
        val listener: View.OnClickListener = object: View.OnClickListener {
            override fun onClick(view: View?) {
                Toast.makeText(view?.context, "Hola clase anónima", Toast.LENGTH_SHORT).show()
            }
        }
        button.setOnClickListener(listener)
         */

        // 3. Listener mediante funciones anónimas/lambdas
        /*
        val listener: (View) -> Unit = {
            Toast.makeText(it.context, "Hola función lambda", Toast.LENGTH_SHORT).show()
        }
        button.setOnClickListener(listener)
         */

        // 3. Listener mediante lambda abreviada
        /*
        button.setOnClickListener {
            Toast.makeText(it?.context, "Hola lambda breve", Toast.LENGTH_SHORT).show()
        }
         */
        val inputUsername: EditText = findViewById(R.id.input_username)

        button.setOnClickListener {
            Toast.makeText(it?.context, "Welcome to Eh-Ho ${inputUsername.text}", Toast.LENGTH_SHORT).show()
        }

    }
}

// 1. Definición de interfaz a partir de una clase
/*
class Listener: View.OnClickListener {
    override fun onClick(view: View?) {
        Toast.makeText(view?.context, "Hola interfaz", Toast.LENGTH_SHORT).show()
    }
}
*/
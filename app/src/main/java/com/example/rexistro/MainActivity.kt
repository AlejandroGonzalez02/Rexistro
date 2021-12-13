package com.example.rexistro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //INICIACIÓN DO FIREBASE AUTH
        auth = Firebase.auth


        //REXISTRO
        val rexistro: Button = findViewById(R.id.rexistrarse)
        rexistro.setOnClickListener{
            val usuario: EditText = findViewById(R.id.correo)
            val contraseña: EditText = findViewById(R.id.contrasinal)
            //Llamo al metodo donde de crear cuenta y le paso de variables el correo y la contraseña
            crearConta(usuario.text.toString(),contraseña.text.toString())
        }
        
        //ENTRADA
        val accederCuenta: Button = findViewById(R.id.entrar)
        accederCuenta.setOnClickListener{
            val usuario: EditText = findViewById(R.id.correo)
            val contrasinal: EditText = findViewById(R.id.contrasinal)
            //Llamo al metodo donde accedo a la cuenta y le paso de variables el correo y la contraseña
            accederConta(usuario.text.toString(),contrasinal.text.toString())
        }
    }
    
    public override fun onStart() {
        super.onStart()
        //Compruebe si el usuario ha iniciado sesión (no nulo) y actualice la interfaz de usuario en consecuencia.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    
    private fun crearConta(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Creacion de conta
                    Log.i("Creación correcta", "Crear Usuario Bien")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // Fallo de creación
                    Log.i("Creación incorrecta", "Crear Usuario Mal", task.exception)
                    Toast.makeText(baseContext, "Fallo en autenticación.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    

    private fun accederConta (email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Iniciar sesión correctamente
                    Log.d("Acceso correcto", "Acceso correcto")
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(baseContext, "Bien.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    // Inicio de sesión falla
                    Log.w("Acceso negativo", "Acceso fallido", task.exception)
                    Toast.makeText(baseContext, "Fallo en la autenticacion",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {

    }


}
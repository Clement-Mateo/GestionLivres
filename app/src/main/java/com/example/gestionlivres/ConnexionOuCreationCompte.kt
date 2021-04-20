package com.example.gestionlivres

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class ConnexionOuCreationCompte : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123

        fun connexionOuCreationCompte(activity: Activity) {
            // Choose authentication providers
            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

            activity.startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading)

        connexionOuCreationCompte(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser

                Toast.makeText(this, "Connexion réussie en tant que " + user.displayName +"!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, BookList::class.java))

            } else {
                Toast.makeText(this, "Echec de onnexion !", Toast.LENGTH_SHORT).show()
                connexionOuCreationCompte(this)
            }
        }
    }
}
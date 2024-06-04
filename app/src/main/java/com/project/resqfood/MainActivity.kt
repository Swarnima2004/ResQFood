package com.project.resqfood


import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.project.resqfood.presentation.login.BottomNavigation.MainScreen
import com.project.resqfood.presentation.login.Destinations
import com.project.resqfood.presentation.login.ForgotPassword
import com.project.resqfood.presentation.login.OTPVerificationUI
import com.project.resqfood.presentation.login.SignInUI
import com.project.resqfood.presentation.login.SignInUsingEmail
import com.project.resqfood.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    companion object{
        var phoneNumber: String = ""
        var storedVerificationId = ""
        var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
        var countDownTime = MutableStateFlow(60000)
        var isResendButtonEnabled = MutableStateFlow(false)
    }
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alreadyLoggedIn = FirebaseAuth.getInstance().currentUser != null
        enableEdgeToEdge()
        setContent {
            AppTheme {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = if(alreadyLoggedIn)
                    Destinations.MainScreen.route else Destinations.SignIn.route) {

                        composable(Destinations.SignIn.route){
                            SignInUI(navController = navController)
                        }
                        composable(Destinations.OtpVerification.route){
                            OTPVerificationUI(navController = navController)
                        }
                        composable(Destinations.EmailSignIn.route){
                            SignInUsingEmail(navController = navController)
                        }
                        composable(Destinations.ForgotPassword.route){
                            ForgotPassword(navController = navController)
                        }
                        composable(Destinations.MainScreen.route){
                            MainScreen(navController = navController)
                        }
                    }
                }
            }
        }

    override fun onBackPressed() {
        super.onBackPressed()
        BackButton()
    }


    private fun BackButton() {
        val BackDialog = AlertDialog.Builder(this)
        BackDialog.setCancelable(false)



        BackDialog.setTitle("Exit")
        BackDialog.setMessage("Want to stay more with RestQFood?")
        BackDialog.setCancelable(false)

        BackDialog.setPositiveButton("No"){dialog, which ->

            dialog.dismiss()
            super.onBackPressed()
        }
        BackDialog.setNegativeButton("Yes"){dialog, which ->

        }
        BackDialog.create()
        BackDialog.show()
    }
}








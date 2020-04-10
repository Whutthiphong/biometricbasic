package dohome.co.th.biometricbasic

import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val biometricManager = androidx.biometric.BiometricManager.from(applicationContext)
        val biometricPrompt = instanceOfBiometricPrompt()
        val promptInfo = getPromptInfo()
        val canAuthenticate = biometricManager.canAuthenticate()

        if (canAuthenticate == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            Log.d("MainActivity", "could not authenticate because: $canAuthenticate")
        }
    }
    private fun getPromptInfo(): androidx.biometric.BiometricPrompt.PromptInfo {
        return androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("My App's Authentication")
                .setSubtitle("Please login to get access")
                .setDescription("My App is using Android biometric authentication")
                .setDeviceCredentialAllowed(true)
                .build()
    }

    private fun instanceOfBiometricPrompt(): androidx.biometric.BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(this)

        val callback = object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext, "$errorCode :: $errString",   Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed",   Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext, "Authentication was successful",   Toast.LENGTH_SHORT).show()
            }
        }

        return androidx.biometric.BiometricPrompt(this, executor, callback)
    }
}

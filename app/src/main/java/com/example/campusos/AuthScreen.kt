package com.example.campusos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AuthScreen(auth: FirebaseAuth, onMockLogin: (UserRole) -> Unit, onAuthSuccess: () -> Unit) {
    var isLogin by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(UserRole.STUDENT) }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)
    val firestore = FirebaseFirestore.getInstance()

    // Real Web Client ID from Firebase Console (if available)
    val webClientId = "17000272518-placeholder.apps.googleusercontent.com"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isLogin) "Welcome Back" else "Create Account",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (isLogin) "Sign in to continue" else "Join the CampusOS community",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (!isLogin) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            
            Text("Select Role", style = MaterialTheme.typography.labelLarge)
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                UserRole.entries.filter { it != UserRole.ADMIN }.forEach { role ->
                    FilterChip(
                        selected = selectedRole == role,
                        onClick = { selectedRole = role },
                        label = { Text(role.name.lowercase().replaceFirstChar { it.uppercase() }) }
                    )
                }
            }
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        )

        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                scope.launch {
                    loading = true
                    error = null
                    try {
                        if (isLogin) {
                            auth.signInWithEmailAndPassword(email, password).await()
                        } else {
                            val res = auth.createUserWithEmailAndPassword(email, password).await()
                            val user = User(id = res.user?.uid ?: "", name = name, email = email, role = selectedRole)
                            firestore.collection("users").document(user.id).set(user).await()
                        }
                        onAuthSuccess()
                    } catch (e: Exception) {
                        error = e.message
                    } finally {
                        loading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = !loading
        ) {
            if (loading) CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
            else Text(if (isLogin) "Sign In" else "Sign Up")
        }

        TextButton(
            onClick = { isLogin = !isLogin },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(if (isLogin) "Don't have an account? Sign Up" else "Already have an account? Sign In")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Button(
            onClick = {
                scope.launch {
                    loading = true
                    error = null
                    try {
                        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                            .setFilterByAuthorizedAccounts(false)
                            .setServerClientId(webClientId)
                            .setAutoSelectEnabled(true)
                            .build()

                        val request: GetCredentialRequest = GetCredentialRequest.Builder()
                            .addCredentialOption(googleIdOption)
                            .build()

                        val result = credentialManager.getCredential(
                            request = request,
                            context = context
                        )

                        val credential = result.credential
                        if (credential is GoogleIdTokenCredential) {
                            val googleIdToken = credential.idToken
                            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                            val res = auth.signInWithCredential(firebaseCredential).await()
                            
                            // Check if user profile exists, if not create one
                            val doc = firestore.collection("users").document(res.user?.uid ?: "").get().await()
                            if (!doc.exists()) {
                                val user = User(id = res.user?.uid ?: "", name = res.user?.displayName ?: "User", email = res.user?.email ?: "", role = UserRole.STUDENT)
                                firestore.collection("users").document(user.id).set(user).await()
                            }
                            onAuthSuccess()
                        }
                    } catch (e: Exception) {
                        error = "Google Sign-In failed: ${e.message}"
                        if (e.message?.contains("7") == true) { // Common code for missing configuration
                             error = "Google Sign-In failed. Please ensure your SHA-1 is added to Firebase and Web Client ID is correct."
                        }
                    } finally {
                        loading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            enabled = !loading
        ) {
            Text("Sign in with Google")
        }

        OutlinedButton(
            onClick = { onMockLogin(UserRole.STUDENT) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Continue as Student (Skip Firebase)")
        }

        OutlinedButton(
            onClick = { onMockLogin(UserRole.ADMIN) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Continue as Admin (Skip Firebase)")
        }
    }
}

package org.example.export.auth

/**
 * Authentication Manager for SDK
 */
class AuthManager {
    private var currentUser: User? = null
    private var authListener: AuthListener? = null

    /**
     * Login with email and password
     */
    suspend fun login(email: String, password: String): AuthResult {
        return try {
            // Simulate API call
            if (email.isNotEmpty() && password.length >= 6) {
                currentUser = User(
                    id = "user_${System.currentTimeMillis()}",
                    email = email,
                    name = email.substringBefore("@")
                )
                authListener?.onAuthStateChanged(currentUser)
                AuthResult.Success(currentUser!!)
            } else {
                AuthResult.Error("Invalid email or password")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Unknown error")
        }
    }

    /**
     * Logout current user
     */
    fun logout() {
        currentUser = null
        authListener?.onAuthStateChanged(null)
    }

    /**
     * Get current logged in user
     */
    fun getCurrentUser(): User? = currentUser

    /**
     * Check if user is logged in
     */
    fun isLoggedIn(): Boolean = currentUser != null

    /**
     * Set authentication listener
     */
    fun setAuthListener(listener: AuthListener) {
        this.authListener = listener
    }

    /**
     * Remove authentication listener
     */
    fun removeAuthListener() {
        this.authListener = null
    }
}

/**
 * User data class
 */
data class User(
    val id: String,
    val email: String,
    val name: String
)

/**
 * Authentication result sealed class
 */
sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

/**
 * Authentication state listener
 */
interface AuthListener {
    fun onAuthStateChanged(user: User?)
}

package com.github.reapermaga.library.common

import com.github.reapermaga.library.common.repository.LocalRepository
import com.github.reapermaga.library.common.repository.Repository
import org.junit.jupiter.api.Test

class RepositoryTest {
    @Test
    fun `Test local repository`() {
        val userRepository = LocalUserRepository()
        val user = User("1", "1@1")

        // Test persist and findById
        userRepository.persist(user)
        assert(userRepository.findById("1") == user)

        // Test save
        val updatedUser = user.copy(email = "new@1")
        userRepository.save(updatedUser)
        assert(userRepository.findById("1") == updatedUser)

        // Test findAll
        val allUsers = userRepository.findAll()
        assert(allUsers.size == 1 && allUsers.contains(updatedUser))

        // Test existsById
        assert(userRepository.existsById("1"))

        // Test count
        assert(userRepository.count() == 1L)

        // Test deleteById
        userRepository.deleteById("1")
        assert(userRepository.findById("1") == null)
        assert(userRepository.count() == 0L)

        // Test deleteAll
        userRepository.persist(user)
        userRepository.deleteAll()
        assert(userRepository.count() == 0L)
    }
}

interface UserRepository : Repository<User, String>

class LocalUserRepository :
    LocalRepository<User, String>(),
    UserRepository {
    override val idSelector: (User) -> String = { it.id }
}

data class User(
    val id: String,
    val email: String,
)

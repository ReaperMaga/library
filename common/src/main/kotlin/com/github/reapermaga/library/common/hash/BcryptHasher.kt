package com.github.reapermaga.library.common.hash

import at.favre.lib.crypto.bcrypt.BCrypt

class BcryptHasher(val cost: Int = 12) : Hasher {

    val hasher: BCrypt.Hasher = BCrypt.withDefaults()
    val verifyer: BCrypt.Verifyer = BCrypt.verifyer()

    override fun hash(text : String) : String {
        return hasher.hashToString(cost, text.toCharArray())
    }

    override fun verify(text : String, hashedText : String) : Boolean {
        return verifyer.verify(text.toCharArray(), hashedText).verified
    }
}
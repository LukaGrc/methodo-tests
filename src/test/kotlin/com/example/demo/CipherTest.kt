//package com.example.demo
//
//import io.kotest.core.spec.style.FunSpec
//import io.kotest.matchers.shouldBe
//import io.kotest.property.Arb
//import io.kotest.property.arbitrary.int
//import io.kotest.property.checkAll
//import io.kotest.property.exhaustive.exhaustive
//
//class CipherTest : FunSpec({
//
//    test("Test de Cipher avec A et 2 doit renvoyer C") {
//        // Arrange
//        val cipher = Cipher()
//        val char = "A"
//        val shift = 2
//
//        // Act
//        val res = cipher.encode(char, shift)
//
//        // Assert
//        res shouldBe "C"
//    }
//
//    test("Test de Cipher avec J et 12 doit renvoyer V") {
//        // Arrange
//        val cipher = Cipher()
//        val char = "J"
//        val shift = 12
//
//        // Act
//        val res = cipher.encode(char, shift)
//
//        // Assert
//        res shouldBe "V"
//    }
//
//    test("Test de Cipher avec X et 5 doit renvoyer C") {
//        // Arrange
//        val cipher = Cipher()
//        val char = "X"
//        val shift = 5
//
//        // Act
//        val res = cipher.encode(char, shift)
//
//        // Assert
//        res shouldBe "C"
//    }
//
//    test("Test de Cipher avec ABC et 2 doit renvoyer CDE") {
//        // Arrange
//        val cipher = Cipher()
//        val char = "ABC"
//        val shift = 2
//
//        // Act
//        val res = cipher.encode(char, shift)
//
//        // Assert
//        res shouldBe "CDE"
//    }
//
//    test("Seules les lettes majuscules sont autorisées") {
//        // Arrange
//        val cipher = Cipher()
//        val char = "a"
//        val shift = 2
//
//        // Act & Assert
//        try {
//            cipher.encode(char, shift)
//        } catch (e: IllegalArgumentException) {
//            e.message shouldBe "Only uppercase letters are allowed!"
//        }
//    }
//
//    test("Le shift doit etre superieur ou egale a 0") {
//        // Arrange
//        val cipher = Cipher()
//        val char = "A"
//        val shift = -2
//
//        // Act & Assert
//        try {
//            cipher.encode(char, shift)
//        } catch (e: IllegalArgumentException) {
//            e.message shouldBe "Shift must be positive!"
//        }
//    }
//
//    test("Si l'on teste avec un clé %26") {
//        checkAll(('A'..'Z').toList().exhaustive()) { char ->
//            checkAll(Arb.int(0..25)) { shift ->
//                // Arrange
//                val cipher = Cipher()
//                val char = char.toString()
//
//                // Act
//                val res1 = cipher.encode(char, shift % 26)
//                val res2 = cipher.encode(char, shift)
//
//                // Assert
//                res1 shouldBe res2
//            }
//        }
//    }
//
//    test("L'encodage est réversible") {
//        checkAll(('A'..'Z').toList().exhaustive()) { char ->
//            checkAll(Arb.int(0..25)) { shift ->
//                // Arrange
//                val cipher = Cipher()
//                val char = char.toString()
//
//                // Act
//                val res1 = cipher.encode(char, shift)
//                val res2 = cipher.encode(res1, 26-shift)
//
//                // Assert
//                res2 shouldBe char
//            }
//        }
//    }
//})
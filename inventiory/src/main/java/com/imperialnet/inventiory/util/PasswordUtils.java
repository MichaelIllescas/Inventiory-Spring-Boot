package com.imperialnet.inventiory.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    // Método para generar un hash de la contraseña
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] salt = getSalt();
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword) + ":" + Base64.getEncoder().encodeToString(salt);
    }

    // Método para obtener una sal aleatoria
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    // Método para comparar contraseñas
    public static boolean verifyPassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException {
        if (storedPassword == null || !storedPassword.contains(":")) {
            return false; // Formato de contraseña no válido
        }
        
        String[] parts = storedPassword.split(":");
        if (parts.length < 2) {
            return false; // Formato de contraseña no válido
        }
        
        byte[] hash = Base64.getDecoder().decode(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedOriginalPassword = md.digest(originalPassword.getBytes());

        return MessageDigest.isEqual(hash, hashedOriginalPassword);
    }
    
    
    
     /*public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println(encodedPassword)  };*/
  
     
 
}

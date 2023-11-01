package com.example.websocketchat.security;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ChatModelEncryptionService {
    private SecretKey secretKey;

    public ChatModelEncryptionService() {
        this.secretKey = loadSecretKey();
        if (this.secretKey == null) {
            this.secretKey = generateSecretKey();
            saveSecretKey(this.secretKey);
        }
    }

    private SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // Вы можете выбрать другой размер ключа
            return keyGen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveSecretKey(SecretKey secretKey) {
        try {
            byte[] keyBytes = secretKey.getEncoded();
            String keyBase64 = Base64.getEncoder().encodeToString(keyBytes);
            Files.write(Paths.get("/var/projects/chat/secret_key.txt"), keyBase64.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SecretKey loadSecretKey() {
        try {
            Path keyFile = Paths.get("/var/projects/chat/secret_key.txt");
            if (Files.exists(keyFile)) {
                byte[] keyBytes = Base64.getDecoder().decode(Files.readAllBytes(keyFile));
                return new SecretKeySpec(keyBytes, "AES");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encryptContent(String content) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(content.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptContent(String encryptedContent) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedContent);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

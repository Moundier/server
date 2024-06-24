package com.example.demo.helpers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;

@Service
public class Cipherify {
  
  private Path path;
  private PublicKey publicKey;
  private PrivateKey privateKey;

  public Cipherify() {
    this.path = Paths.get("../pix-pass-server").toAbsolutePath();
    this.attemptCipherModule();
  }

  @SneakyThrows
  @PostConstruct
  private void attemptCipherModule() {
    
    Path keyPublic = path.resolve("public.key");
    Path keyPrivate = path.resolve("private.key");

    if (Files.notExists(keyPublic) && Files.notExists(keyPrivate)) {
      this.keypairNotFound(keyPublic, keyPrivate);
      System.out.println("CREATE KEY-PAIR");
      return;
    }

    System.out.println("FOUND KEY-PAIR");
    this.keypairExisting(keyPublic, keyPrivate);
  }

  @SneakyThrows
  private void keypairExisting(Path privateKeyPath, Path publicKeyPath) {
    KeyFactory kf = KeyFactory.getInstance("RSA");
    byte[] publicKeyBytes = Files.readAllBytes(publicKeyPath);
    byte[] privateKeyBytes = Files.readAllBytes(privateKeyPath);

    X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicKeyBytes);
    PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateKeyBytes);

    this.publicKey = kf.generatePublic(publicSpec);
    this.privateKey = kf.generatePrivate(privateSpec);
  }

  @SneakyThrows
  private void keypairNotFound(Path privateKeyPath, Path publicKeyPath) {
    Files.createDirectories(this.path);

    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);

    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    this.publicKey = keyPair.getPublic();
    this.privateKey = keyPair.getPrivate();

    Files.write(privateKeyPath, this.privateKey.getEncoded());
    Files.write(publicKeyPath, this.publicKey.getEncoded());
  }

  public String encrypt(String password) {
    try {
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Encryption failed: Algorithm not found", e);
    } catch (NoSuchPaddingException e) {
      throw new RuntimeException("Encryption failed: Padding not found", e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException("Encryption failed: Invalid key", e);
    } catch (IllegalBlockSizeException e) {
      throw new RuntimeException("Encryption failed: Illegal block size", e);
    } catch (BadPaddingException e) {
      throw new RuntimeException("Encryption failed: Bad padding", e);
    }
  }

  public String decrypt(String encryptedPassword) {
    try {
      byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
      return new String(decryptedBytes, StandardCharsets.UTF_8);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Decryption failed: Algorithm not found", e);
    } catch (NoSuchPaddingException e) {
      throw new RuntimeException("Decryption failed: Padding not found", e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException("Decryption failed: Invalid key", e);
    } catch (IllegalBlockSizeException e) {
      throw new RuntimeException("Decryption failed: Illegal block size", e);
    } catch (BadPaddingException e) {
      throw new RuntimeException("Decryption failed: Bad padding", e);
    }
  }
  
}

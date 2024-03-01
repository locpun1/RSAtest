package rsa;

import java.security.*;
import java.util.Scanner;
import javax.crypto.Cipher;

public class RSAMessageEncryptionExample {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String message;
            while (true) {
                System.out.println("Nhập thông điệp(nhập 'exit' để thoát): ");
                message = scanner.nextLine();

                //kiểm tra điều kiện thoát vòng lặp
                if ("exit".equalsIgnoreCase(message)) {
                    System.out.println("Dừng nhắn tin:");
                    break;
                }

                // Tạo cặp khóa cho Alice và Bob
                KeyPair keyPairAlice = generateRSAKeyPair();
                KeyPair keyPairBob = generateRSAKeyPair();

                // Dữ liệu cần truyền tải

                // Alice mã hóa thông điệp bằng khóa công khai của Bob
                byte[] encryptedData = encryptMessage(message, keyPairBob.getPublic());
                System.out.println("Tin nhắn gửi đã được mã hóa: "+encryptedData);
                // Bob giải mã thông điệp bằng khóa riêng của mình
                String decryptedMessage = decryptMessage(encryptedData, keyPairBob.getPrivate());
                System.out.println("Người nhận giải mã thông điệp: "+decryptedMessage);
                // In kết quả

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm tạo cặp khóa RSA
    private static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Độ dài khóa 2048 bit
        return keyPairGenerator.generateKeyPair();
    }

    // Hàm mã hóa thông điệp sử dụng RSA
    private static byte[] encryptMessage(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

    // Hàm giải mã thông điệp sử dụng RSA
    private static String decryptMessage(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return new String(decryptedBytes);
    }
}

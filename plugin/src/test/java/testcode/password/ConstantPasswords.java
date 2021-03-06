package testcode.password;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.PasswordAuthentication;
import java.security.KeyRep;
import java.security.KeyStore;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAMultiPrimePrivateCrtKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.DHPrivateKeySpec;
import javax.crypto.spec.DHPublicKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.KeyManagerFactory;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.kerberos.KerberosKey;
import javax.security.auth.kerberos.KerberosTicket;
import sun.security.provider.DSAPublicKeyImpl;

public class ConstantPasswords {

    private static String PWD1 = "secret4";
    private static char[] PWD2 = {'s', 'e', 'c', 'r', 'e', 't', '5'};
    private char[] PWD3 = {'s', 'e', 'c', 'r', 'e', 't', '5'};
    private static BigInteger big = new BigInteger("1000000");
    private static final byte[] PUBLIC_KEY = new byte[]{1, 2, 3, 4, 5, 6, 7};

    public void bad1() throws Exception {
        char[] passphrase = "secret1".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("keystore"), passphrase);
    }

    public static void bad2() throws Exception {
        final String passphrase = "secret2";
        System.out.println("secret2");
        KeyStore ks = KeyStore.getInstance("JKS");
        FileInputStream fs = new FileInputStream("keystore");
        ks.load(fs, passphrase.toCharArray());
    }

    public void bad3() throws Exception {
        char[] passphrase = {'s', 'e', 'c', 'r', 'e', 't', '3'};
        KeyStore.getInstance("JKS").load(new FileInputStream("keystore"), passphrase);
    }

    public void bad4() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("keystore"), PWD1.toCharArray());
    }

    public static void bad5a() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("keystore"), PWD2);
    }

    public void bad5b() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("keystore"), PWD3);
    }

    public void bad6() throws Exception {
        String pwdStr = "secret6";
        char[] pwd1 = pwdStr.toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        char[] pwd2 = pwd1;
        ks.load(new FileInputStream("keystore"), pwd2);
    }

    public void bad7() throws Exception {
        byte[] bytes = new byte[2];
        char[] pwd = "secret7".toCharArray();
        new PBEKeySpec(pwd);
        new PBEKeySpec(pwd, bytes, 1);
        new PBEKeySpec(pwd, bytes, 1, 1);
        PasswordAuthentication auth = new PasswordAuthentication("user", pwd);
        PasswordCallback callback = new PasswordCallback("str", true);
        callback.setPassword(pwd);
        KeyStore.PasswordProtection protection = new KeyStore.PasswordProtection(pwd);
        KerberosKey key = new KerberosKey(null, pwd, "alg");
        KeyManagerFactory.getInstance("").init(null, pwd);
    }

    public void bad8a() throws Exception {
        new DESKeySpec(null); // should not be reported
        byte[] key = {1, 2, 3, 4, 5, 6, 7, 8};
        DESKeySpec spec = new DESKeySpec(key);
        KeySpec spec2 = new DESedeKeySpec(key);
        KerberosKey kerberosKey = new KerberosKey(null, key, 0, 0);
        System.out.println(spec.getKey()[0] + kerberosKey.getKeyType());
        new SecretKeySpec(key, "alg");
        new SecretKeySpec(key, 0, 0, "alg");
        new X509EncodedKeySpec(key);
        new PKCS8EncodedKeySpec(key);
        new KeyRep(null, "alg", "format", key);
        new KerberosTicket(null, null, null, key, 0, null, null, null, null, null, null);
        new DSAPublicKeyImpl(key);
    }

    public void bad8b() {
        byte[] key = "secret8".getBytes();
        System.out.println("something");
        new SecretKeySpec(key, "alg");
    }

    public void bad9() throws SQLException {
        String pass = "secret9";
        Connection connection = DriverManager.getConnection("url", "user", PWD1);
        System.out.println(connection.getSchema());
        connection = DriverManager.getConnection("url", "user", pass);
        System.out.println(connection.getSchema());
    }

    public void bad10() throws Exception {
        BigInteger bigInteger = new BigInteger("12345", 5);
        new DSAPrivateKeySpec(bigInteger, null, null, null);
        new DSAPublicKeySpec(bigInteger, null, null, null);
        new DHPrivateKeySpec(bigInteger, null, null);
        new DHPublicKeySpec(bigInteger, null, null);
        new ECPrivateKeySpec(bigInteger, null);
        new RSAPrivateKeySpec(bigInteger, null);
        new RSAMultiPrimePrivateCrtKeySpec(bigInteger, null, null, null, null, null, null, null, null);
        new RSAPrivateCrtKeySpec(bigInteger, null, null, null, null, null, null, null);
        new RSAPublicKeySpec(bigInteger, null);
        new DSAPublicKeyImpl(bigInteger, null, null, null);
    }

    public void bad11() {
        new DSAPrivateKeySpec(null, null, null, null); // should not be reported
        System.out.println();
        new DSAPrivateKeySpec(big, null, null, null);
    }

    public void bad12() {
        byte[] key = "secret8".getBytes();
        BigInteger bigInteger = new BigInteger(key);
        new DSAPrivateKeySpec(bigInteger, null, null, null);
    }

    public void good1() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("keystore"), getPassword());
    }

    public void good2() throws Exception {
        String pwd = "uiiii".substring(3) + "oo";
        char[] pwdArray = pwd.toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("keystore"), pwdArray);
    }

    private static char[] getPassword() {
        char[] password = new char[3];
        // some operations to simulate non-constant password
        password[0] = 'x';
        password[1] = 10;
        password[2] = ("o" + "z").charAt(1);
        return password;
    }
}

package com.oguzhan.karacorlu.X509CertificateGenerator;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;

@SpringBootApplication
public class X509CertificateGeneratorApplication {

    public static void main(String[] args) throws Exception {
        // Generate key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Set basic information for the certificate
        String subjectDN = "CN=Common Name, O=Organization, OU=Organizational Unit, L=Ankara, ST=Ankara, C=TR";
        String issuerDN = subjectDN; // Since it's a self-signed certificate
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);
        Date endDate = new Date(now + 100L * 365 * 24 * 60 * 60 * 1000); // Validity period is 100 years

        // Generate X.509 certificate using the keys and basic information for the certificate
        X509Certificate cert = CertificateUtils.generateCertificate(publicKey, privateKey, subjectDN, issuerDN, startDate, endDate);

        // Write the certificate to a file
        FileOutputStream fos = new FileOutputStream("okaracorlu.crt");
        fos.write(cert.getEncoded());
        fos.close();

    }
}

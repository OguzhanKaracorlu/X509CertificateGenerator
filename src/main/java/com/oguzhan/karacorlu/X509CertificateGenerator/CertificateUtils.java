package com.oguzhan.karacorlu.X509CertificateGenerator;

import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;

public class CertificateUtils {

    public static X509Certificate generateCertificate(PublicKey publicKey, PrivateKey privateKey, String subjectDN, String issuerDN, Date startDate, Date endDate) throws Exception {
        // Certificate generation process
        X509Certificate cert = null;
        X500Principal subject = new X500Principal(subjectDN);
        X500Principal issuer = new X500Principal(issuerDN);

        // Certificate generator
        java.security.cert.Certificate certGen = java.security.cert.CertificateFactory.getInstance("X.509")
                .generateCertificate(new java.io.ByteArrayInputStream(generateCert(publicKey, privateKey, subject, issuer, startDate, endDate)));

        // Cast certificate
        if (certGen instanceof X509Certificate) {
            cert = (X509Certificate) certGen;
        }

        return cert;
    }

    public static byte[] generateCert(PublicKey publicKey, PrivateKey privateKey, X500Principal subject, X500Principal issuer, Date startDate, Date endDate) throws Exception {
        // Certificate generation process
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setSubjectDN(subject);
        certGen.setIssuerDN(issuer);
        certGen.setPublicKey(publicKey);
        certGen.setNotBefore(startDate);
        certGen.setNotAfter(endDate);
        certGen.setSignatureAlgorithm("SHA256withRSA");

        // Signing the certificate with the private key
        certGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));
        certGen.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        certGen.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));

        X509Certificate cert = certGen.generate(privateKey, secureRandom);
        return cert.getEncoded();
    }

}

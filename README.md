# X.509 Certificate Generator

This project is a simple Java application for generating X.509 certificates. It provides a convenient way to create self-signed certificates for testing and development purposes.

## Requirements
Java Development Kit (JDK) 8 or higher

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.


## How to Use

To generate a certificate, you can use the provided `CertificateUtils` class. Here's a basic example:

```java
// Generate key pair
KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
keyPairGenerator.initialize(2048);
KeyPair keyPair = keyPairGenerator.generateKeyPair();
PublicKey publicKey = keyPair.getPublic();
PrivateKey privateKey = keyPair.getPrivate();

// Set subject and issuer information
String subjectDN = "CN=Example, O=Organization, OU=Unit, L=City, ST=State, C=Country";
String issuerDN = subjectDN; // Since it's a self-signed certificate

// Set validity period
long now = System.currentTimeMillis();
Date startDate = new Date(now);
Date endDate = new Date(now + 100L * 365 * 24 * 60 * 60 * 1000); // Validity period is 100 years

// Generate X.509 certificate
X509Certificate cert = CertificateUtils.generateCertificate(publicKey, privateKey, subjectDN, issuerDN, startDate, endDate);

// Write the certificate to a file
FileOutputStream fos = new FileOutputStream("example.crt");
fos.write(cert.getEncoded());
fos.close();


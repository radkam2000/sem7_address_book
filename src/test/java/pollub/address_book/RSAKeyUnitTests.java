package pollub.address_book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pollub.address_book.utils.KeyGeneratorUtility;
import pollub.address_book.utils.RSAKeyProperties;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RSAKeyUnitTests {
    @Autowired
    private RSAKeyProperties rsaKeyProperties;

    @Test
    public void testGenerateRsaKey() {
        KeyPair keyPair = KeyGeneratorUtility.generateRsaKey();
        assertNotNull(keyPair, "Generated key pair should not be null");
    }

    @Test
    public void testSetPublicKey() {
        RSAPublicKey mockPublicKey = Mockito.mock(RSAPublicKey.class);
        rsaKeyProperties.setPublicKey(mockPublicKey);
        assertEquals(mockPublicKey, rsaKeyProperties.getPublicKey());
    }

    @Test
    public void testSetPrivateKey() {
        RSAPrivateKey mockPrivateKey = Mockito.mock(RSAPrivateKey.class);
        rsaKeyProperties.setPrivateKey(mockPrivateKey);
        assertEquals(mockPrivateKey, rsaKeyProperties.getPrivateKey());
    }

}

package br.com.gabrieltonhatti;

import br.com.gabrieltonhatti.entidades.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssertTest {

	@Test
	public void test() {
		Assertions.assertTrue(true);
		Assertions.assertFalse(false);

		Assertions.assertEquals(1, 1);
		Assertions.assertEquals(0.51234, 0.512, 0.001);
		Assertions.assertEquals(Math.PI, 3.14, 0.01);

		int i = 5;
		Integer i2 = 5;
		Assertions.assertEquals(Integer.valueOf(i), i2);
		Assertions.assertEquals(i, i2.intValue());

		Assertions.assertEquals("bola", "bola");
		Assertions.assertNotEquals("bola", "casa");
		Assertions.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assertions.assertTrue("bola".startsWith("bo"));

		Usuario u1 = new Usuario("Usuário 1");
		Usuario u2 = new Usuario("Usuário 1");
		Usuario u3 = null;

		// Precisa ter o Equals e Hashcode implementado
		Assertions.assertEquals(u1, u2);

		// Verifica em nível de instância
		Assertions.assertSame(u2, u2);
		Assertions.assertNotSame(u1, u2);

		Assertions.assertNull(u3);
		Assertions.assertNotNull(u2);
	}

}

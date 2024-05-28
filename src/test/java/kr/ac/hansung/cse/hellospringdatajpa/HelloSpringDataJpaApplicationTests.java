package kr.ac.hansung.cse.hellospringdatajpa;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 통합테스트 : spring container 를 만듬. 즉 어플리케이션이 있는 패키지를 스캔하여 서비스 컨트롤러 이런 빈들 등록
@Transactional // 테스트가 끝난 후 롤백
class HelloSpringDataJpaApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	private static final Logger logger = LoggerFactory.getLogger(HelloSpringDataJpaApplicationTests.class);

	@Test
	@DisplayName("Test1: findProductById")
	public void findProductById() {

		// Optional 객체:  null 값을 처리할 때 발생할 수 있는 NullPointerException을 방지
		// 값이 있으면 해당 값을 포함하고, 없으면	null
		Optional<Product> product = productRepository.findById(1L);

		assertTrue(product.isPresent(), "Product should be present");

		//lambda expression, parameters -> { statements; }, 익명 함수를 간결하게 작성할 수 있는 기능
		product.ifPresent(p -> {
			logger.info("Product found: {}", p);
		});

	}

	@Test
	@DisplayName("Test2: findAllProducts")
	public void findAllProducts() {

		List<Product> products = productRepository.findAll();
		assertNotNull(products);
		products.forEach(p -> logger.info("Product found: {}", p));

	}

	@Test
	@DisplayName("Test3: createProduct")
	public void createProduct() {

		Product product = new Product("OLED TV", "LG전자", "korea", 300.0);
		Product savedProduct = productRepository.save(product);

		Optional<Product> newProduct = productRepository.findById(savedProduct.getId());
		assertTrue(newProduct.isPresent(), "Product should be present");

		newProduct.ifPresent(p -> {
			logger.info("Product created: {}", p);
			assertEquals("OLED TV", p.getName());
		});
	}

}

package ru.caloricity.main;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;


@SpringBootTest
class CaloricityApplicationTests {
	ApplicationModules modules = ApplicationModules.of(CaloricityApplication.class);


	@Test
	void shouldBeCompliant() {
		modules.verify();
	}

	@Test
	void writeDocumentationSnippets() {
		new Documenter(modules)
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();
	}

}

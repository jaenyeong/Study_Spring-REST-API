package com.jaenyeong.restapi.index;

import com.jaenyeong.restapi.common.BaseTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//@Import(RestDocsConfiguration.class)
//@ActiveProfiles("test")
class IndexControllerTest extends BaseTest {

//	@Autowired
//	MockMvc mockMvc;

	@Test
	void index() throws Exception {
		this.mockMvc.perform(get("/api"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("_links.events").exists());
	}
}

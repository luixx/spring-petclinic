import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import static org.springframework.test.web.servlet.result.MockResultMatchers.*;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCitySearch() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = get("/api/owners?city=Berlin").param("page", "0");

        // Act
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        // Assert
    }
}
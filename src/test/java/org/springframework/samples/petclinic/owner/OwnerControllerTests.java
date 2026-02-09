package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.RequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Import status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(OwnerController.class)
class OwnerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerRepository owners;

    @Test
    void testFindOwnersByCity() throws Exception {
        // Setup: Add some owners with different cities
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setCity("New York");
        owners.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Smith");
        owner2.setCity("London");
        owners.save(owner2);

        Owner owner3 = new Owner();
        owner3.setFirstName("Peter");
        owner3.setLastName("Jones");
        owner3.setCity("New York");
        owners.save(owner3);

        // Action: Perform a GET request with a city parameter
        MockHttpServletRequestBuilder requestBuilder = get("/owners")
                .param("city", "New York");

        // Assertion: Verify that the response status is OK and the number of owners returned is correct
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attributeHasSize(2, "ownerList"));
    }

    @Test
    void testFindOwnersByCitySubstring() throws Exception {
        // Setup: Add some owners with different cities
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setCity("Berlin-Mitte");
        owners.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Smith");
        owner2.setCity("Berlin");
        owners.save(owner2);

        // Action: Perform a GET request with a city parameter
        MockHttpServletRequestBuilder requestBuilder = get("/owners")
                .param("city", "Berlin");

        // Assertion: Verify that the response status is OK and the number of owners returned is correct
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attributeHasSize(2, "ownerList"));
    }

    @Test
    void testFindOwnersByCityCaseInsensitive() throws Exception {
        // Setup: Add some owners with different cities
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setCity("london");
        owners.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Smith");
        owner2.setCity("London");
        owners.save(owner2);

        // Action: Perform a GET request with a city parameter
        MockHttpServletRequestBuilder requestBuilder = get("/owners")
                .param("city", "LONDON");

        // Assertion: Verify that the response status is OK and the number of owners returned is correct
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attributeHasSize(2, "ownerList"));
    }
}

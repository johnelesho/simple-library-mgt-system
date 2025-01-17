package tech.elsoft.maidcclibrarymanagementsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreateBookDTO;
import tech.elsoft.maidcclibrarymanagementsystem.controllers.BooksController;
import tech.elsoft.maidcclibrarymanagementsystem.security.SecurityConfig;

import java.time.Year;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksController.class)
public class BookControllerUnitTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void getAllBooks() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
    }

    @Test
    public void createEmployeeAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/books")
                        .content(asJsonString(new CreateBookDTO("Title One", Set.of("Test"), "Test", "2011792226", "John", true, true, Year.of(2024))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

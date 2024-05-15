package org.sinedmv.Cats.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.ApiMicroservice.Models.SignInDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class CatControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addCat_correct_withoutToken() throws Exception {
        CatDto catDto = new CatDto();
        catDto.setId(1000);
        catDto.setName("t1");
        catDto.setBirthdayDate(new Date());
        catDto.setBreed("t1");
        catDto.setColor("BLACK");
        catDto.setOwnerId(1000);

        String jsonRequest = objectMapper.writeValueAsString(catDto);

        mockMvc.perform(post("/cats/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addCat_correct() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User1");
        signInDto.setPassword("Password1");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        CatDto catDto = new CatDto();
        catDto.setId(1000);
        catDto.setName("t1");
        catDto.setBirthdayDate(new Date());
        catDto.setBreed("t1");
        catDto.setColor("BLACK");
        catDto.setOwnerId(1000);

        jsonRequest = objectMapper.writeValueAsString(catDto);

        mockMvc.perform(post("/cats/add")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    public void addCat_incorrect() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User1");
        signInDto.setPassword("Password1");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        CatDto catDto = new CatDto();
        catDto.setId(1);
        catDto.setName("t1");
        catDto.setBirthdayDate(new Date());
        catDto.setBreed("t1");

        jsonRequest = objectMapper.writeValueAsString(catDto);

        mockMvc.perform(post("/cats/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(jsonRequest))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getById_correct() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User8");
        signInDto.setPassword("Password8");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        mockMvc.perform(get("/cats/{id}", 1)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cat 1"));
    }

    @Test
    public void getById_incorrect() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User8");
        signInDto.setPassword("Password8");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        mockMvc.perform(get("/cats/{id}", 10000)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getCatsByBreed_correct() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User9");
        signInDto.setPassword("Password9");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();


        mockMvc.perform(get("/cats/bybreed/{breed}", "Bengal")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cat 10"))
                .andExpect(jsonPath("$[1].name").value("Cat 16"))
        ;
    }

    @Test
    public void getCatsByColor_correct() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User9");
        signInDto.setPassword("Password9");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        mockMvc.perform(get("/cats/bycolor/{color}", "PINK")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cat 5"))
                .andExpect(jsonPath("$[1].name").value("Cat 16"));
    }

    @Test
    public void getCatsByColor_incorrect() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User9");
        signInDto.setPassword("Password9");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        mockMvc.perform(get("/cats/bycolor/{color}", "adawdad")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }
}

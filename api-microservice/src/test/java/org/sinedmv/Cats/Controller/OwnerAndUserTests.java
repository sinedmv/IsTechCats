package org.sinedmv.Cats.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sinedmv.Cats.ApiMicroservice.Models.SignInDto;
import org.sinedmv.Cats.Entities.Dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class OwnerAndUserTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addUser_correct() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User10");
        signInDto.setPassword("Password10");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        UserDto userDto = new UserDto();
        userDto.setUsername("t1");
        userDto.setPassword("tttt");
        userDto.setName("t1");
        userDto.setBirthdayDate(new java.util.Date());

        jsonRequest = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/owners/add")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    public void addUser_incorrect() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User10");
        signInDto.setPassword("Password10");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        UserDto userDto = new UserDto();
        userDto.setUsername("t1");
        userDto.setPassword("tttt");

        jsonRequest = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/owners/add")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getOwnerById_correct() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User10");
        signInDto.setPassword("Password10");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        mockMvc.perform(get("/owners/{id}", 1)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name1"));
    }

    @Test
    public void getOwnerById_incorrect() throws Exception {
        SignInDto signInDto = new SignInDto();
        signInDto.setUsername("User10");
        signInDto.setPassword("Password10");

        String jsonRequest = objectMapper.writeValueAsString(signInDto);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("jwt").asText();

        mockMvc.perform(get("/owners/{id}", 100000)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isInternalServerError());
    }
}

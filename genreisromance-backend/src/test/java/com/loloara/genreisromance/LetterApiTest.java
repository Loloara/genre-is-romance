package com.loloara.genreisromance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.loloara.genreisromance.model.dto.UserDto;
import com.loloara.genreisromance.repository.LetterRepository;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LetterApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LetterRepository letterRepository;

    @Test
    public void getLettersForAdminTest() throws Exception {
        String jsonUser = asJsonString(
                UserDto.LoginRequest.builder()
                        .email("admin@gmail.com")
                        .password("admin1@3$")
                        .build());
        MvcResult result = mockMvc.perform(
                post("/api/auth/email")
                        .content(jsonUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
        String token = obj.getString("token");

        result = mockMvc.perform(
                get("/api/letter/process/0")
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("getLettersTest: " + result.getResponse().getContentAsString());
    }

    public static String asJsonString(final Object o) {
        try{
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .writeValueAsString(o);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
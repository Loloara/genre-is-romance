package com.loloara.genreisromance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.model.dto.UserDto;
import com.loloara.genreisromance.repository.AuthorityRepository;
import com.loloara.genreisromance.repository.UserRepository;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    public void registerAndGetUserTest() throws Exception {
        final String TEST_EMAIL = "test@gmail.com";

        String jsonUser = asJsonString(
                UserDto.Create.builder()
                        .email(TEST_EMAIL)
                        .password("test1@3$")
                        .userName("tuser")
                        .height(173)
                        .phone("01022345678")
                        .gender(Gender.MALE.jsonValue())
                        .birthDate(LocalDate.of(1994,3,16))
                        .build());

        MvcResult result = mockMvc.perform(
                post("/api/register")
                        .content(jsonUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("registerUserTest: " + result.getRequest().getContentAsString());

        MvcResult result2 = mockMvc.perform(
                get("/api/user/" + TEST_EMAIL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("getUserTest: " + result2.getResponse().getContentAsString());

        userRepository.findByEmailFetchAll(TEST_EMAIL).ifPresent(user -> {
            assertEquals(user.getAuthorities().size(), 1);
        });

        authorityRepository.findByNameFetchAll("ROLE_USER").ifPresent(authority -> {
            assertEquals(authority.getUsers().size(), 1);
        });

        userRepository.deleteByEmail(TEST_EMAIL);
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
package com.loloara.genreisromance;

import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.model.domain.UserAuthority;
import com.loloara.genreisromance.model.dto.UserDto;
import com.loloara.genreisromance.repository.AuthorityRepository;
import com.loloara.genreisromance.repository.UserRepository;
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

import java.time.LocalDate;

import static com.loloara.genreisromance.UtilForTest.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        final String TEST_EMAIL = "test01@gmail.com";

        String jsonUser = asJsonString(
                UserDto.Create.builder()
                        .email(TEST_EMAIL)
                        .password("test1@3$")
                        .userName("testUser")
                        .height(173)
                        .phone("01012345678")
                        .gender(Gender.MALE.jsonValue())
                        .birthDate(LocalDate.of(1994,4,10))
                        .build());

        MvcResult result = mockMvc.perform(
                post("/api/user")
                        .content(jsonUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("registerUserTest: " + result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(
                get("/api/user/" + TEST_EMAIL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("getUserTest: " + result2.getResponse().getContentAsString());

        userRepository.findByEmailFetchAll(TEST_EMAIL).ifPresent(user -> {
            for(UserAuthority u : user.getAuthorities()) {
                assertEquals(u.getAuthority().getName(), "ROLE_USER");
            }
        });

        authorityRepository.findByNameFetchAll("ROLE_USER").ifPresent(authority -> {
            boolean chk = false;
            for(UserAuthority u : authority.getUsers()) {
                if(u.getUser().getEmail().equals(TEST_EMAIL)) {
                    chk = true;
                    break;
                }
            }
            assertTrue(chk);
        });

        userRepository.deleteByEmail(TEST_EMAIL);
    }

    @Test
    public void LoginTest() throws Exception {
        final String TEST_EMAIL = "test02@gmail.com";

        String jsonUser = asJsonString(
                UserDto.LoginRequest.builder()
                .email(TEST_EMAIL)
                .password("test1@3$")
                .build());

        MvcResult result = mockMvc.perform(
                post("/api/auth/email")
                        .content(jsonUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("LoginTest: " + result.getResponse().getContentAsString());

        JSONObject obj = new JSONObject(result.getResponse().getContentAsString());
        String token = obj.getString("token");

        MvcResult result2 = mockMvc.perform(
                post("/api/auth/user")
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("CurrentUserTest: " + result2.getResponse().getContentAsString());
    }
}
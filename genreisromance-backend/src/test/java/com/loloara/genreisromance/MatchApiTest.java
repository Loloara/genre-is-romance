package com.loloara.genreisromance;

import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.common.util.DayTime;
import com.loloara.genreisromance.model.domain.MatchInfo;
import com.loloara.genreisromance.model.dto.MatchInfoDto;
import com.loloara.genreisromance.model.dto.TheDayDto;
import com.loloara.genreisromance.model.dto.UserDto;
import com.loloara.genreisromance.repository.MatchInfoRepository;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.loloara.genreisromance.UtilForTest.asJsonString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MatchApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MatchInfoRepository matchInfoRepository;

    @Test
    public void makeMatchTest() throws Exception {
        final Long TEST_MALE_ID = 3L;
        final Long TEST_FEMALE_ID = 91L;

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

        String jsonMatch = asJsonString(
                MatchInfoDto.Create.builder()
                        .userMaleId(TEST_MALE_ID)
                        .userFemaleId(TEST_FEMALE_ID)
                        .build());

        MvcResult result2 = mockMvc.perform(
                post("/api/match")
                        .header("Authorization", token)
                        .content(jsonMatch)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("makeMatchTest: " + result2.getResponse().getContentAsString());

        MatchInfo matchInfo = matchInfoRepository.findByUserIdOnProcess(TEST_MALE_ID)
                .orElseThrow(() -> new ApiException("Not Found Match", HttpStatus.NOT_FOUND));

        assertEquals(matchInfo.getUserMaleId().getId(), TEST_MALE_ID);
        assertEquals(matchInfo.getUserFemaleId().getId(), TEST_FEMALE_ID);

        jsonUser = asJsonString(
                UserDto.LoginRequest.builder()
                        .email("test02@gmail.com")
                        .password("test1@3$")
                        .build());
        result = mockMvc.perform(
                post("/api/auth/email")
                        .content(jsonUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        obj = new JSONObject(result.getResponse().getContentAsString());
        token = obj.getString("token");

        List<String> movies = Arrays.asList("movie01", "movie02", "movie03");
        List<String> places = Arrays.asList("place01", "place02", "place03");
        String jsonMovies = asJsonString(MatchInfoDto.AddMatchMovies.builder().movieIds(movies).build());
        String jsonPlaces = asJsonString(MatchInfoDto.AddMatchPlaces.builder().placeIds(places).build());
        mockMvc.perform(
                post("/api/match/movie")
                        .header("Authorization", token)
                        .content(jsonMovies)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(
                post("/api/match/place")
                        .header("Authorization", token)
                        .content(jsonPlaces)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String jsonTheDay = asJsonString(TheDayDto.Create.builder().dayDate(LocalDate.now().plusWeeks(1)).dayTime(DayTime._6PM).build());
        MvcResult result1 = mockMvc.perform(
                post("/api/theday")
                        .header("Authorization", token)
                        .content(jsonTheDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        obj = new JSONObject(result1.getResponse().getContentAsString());
        Long theDayId = obj.getLong("id");
        String jsonTheDays = asJsonString(MatchInfoDto.AddMatchTheDays.builder().thedayIds(Collections.singletonList(theDayId)).build());
        mockMvc.perform(
                post("/api/match/theday")
                        .header("Authorization", token)
                        .content(jsonTheDays)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        matchInfoRepository.deleteById(matchInfo.getId());
    }
}

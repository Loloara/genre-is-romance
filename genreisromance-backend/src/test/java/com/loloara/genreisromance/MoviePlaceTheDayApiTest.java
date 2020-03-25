package com.loloara.genreisromance;

import com.loloara.genreisromance.common.util.DayTime;
import com.loloara.genreisromance.model.domain.Movie;
import com.loloara.genreisromance.model.domain.Place;
import com.loloara.genreisromance.model.domain.TheDay;
import com.loloara.genreisromance.model.dto.*;
import com.loloara.genreisromance.repository.MovieRepository;
import com.loloara.genreisromance.repository.PlaceRepository;
import com.loloara.genreisromance.repository.TheDayRepository;
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
import java.util.ArrayList;
import java.util.List;

import static com.loloara.genreisromance.UtilForTest.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MoviePlaceTheDayApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    TheDayRepository theDayRepository;

    @Test
    public void addMoviesPlacesTheDaysTest() throws Exception {
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

        List<Movie> movies = new ArrayList<>();
        List<Place> places = new ArrayList<>();
        List<TheDay> theDays = new ArrayList<>();

        for(int i=1;i<4;i++) {
            String jsonMovie = asJsonString(MovieDto.Create.builder().movieTitle("test0"+i).build());
            String jsonPlace = asJsonString(PlaceDto.Create.builder().placeName("test0"+i).build());
            String jsonTheDay = asJsonString(TheDayDto.Create.builder().dayDate(LocalDate.now().plusWeeks(i*2)).dayTime(DayTime._6PM).build());

            mockMvc.perform(
                    post("/api/movie")
                            .header("Authorization", token)
                            .content(jsonMovie)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            mockMvc.perform(
                    post("/api/place")
                            .header("Authorization", token)
                            .content(jsonPlace)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            mockMvc.perform(
                    post("/api/theday")
                            .header("Authorization", token)
                            .content(jsonTheDay)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();
        }

        for(int i=1;i<4;i++) {
            movieRepository.deleteById("test0"+i);
            placeRepository.deleteById("test0"+i);
        }
        theDayRepository.deleteUnusedTheDay();
    }
}

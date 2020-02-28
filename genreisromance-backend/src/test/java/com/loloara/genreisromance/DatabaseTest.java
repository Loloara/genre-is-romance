package com.loloara.genreisromance;

import com.loloara.genreisromance.common.util.DayTime;
import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.model.*;
import com.loloara.genreisromance.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DatabaseTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    DataSourceProperties dataSourceProperties;

    @Autowired
    UserService userService;

    @Autowired
    LetterService letterService;

    @Autowired
    MatchInfoService matchInfoService;

    @Autowired
    MatchMovieService matchMovieService;

    @Autowired
    MatchPlaceService matchPlaceService;

    @Autowired
    MatchTheDayService matchTheDayService;

    @Autowired
    MovieService movieService;

    @Autowired
    PlaceService placeService;

    @Autowired
    TheDayService theDayService;

    @Test
    public void dataSourcePropertiesTest() {
        try(Connection con = dataSource.getConnection()) {
            System.out.println("Database Connected: " + con);
        }catch (Exception e) {
            e.printStackTrace();
        }

        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/genreisromance?serverTimezone=Asia/Seoul&userUnicode=true&characterEncoding=utf-8";
        String username = "root";

        System.out.println("DataSource.toString(): " + dataSource.toString());
        assertEquals(driverClassName, dataSourceProperties.getDriverClassName());
        assertEquals(jdbcUrl, dataSourceProperties.getUrl());
        assertEquals(username, dataSourceProperties.getUsername());
    }

    @Test
    public void userLetterSaveTest() {
        User user = userService.save(
                User.builder()
                        .userName("Lucas")
                        .birthDate(LocalDate.of(1994,4,10))
                        .email("test@gmail.com")
                        .gender(Gender.MALE)
                        .height(173)
                        .phone("01012345678")
                        .build()
        );

        Letter letter = letterService.save(
                Letter.builder()
                        .user_id(user)
                        .Q1("Q1 TEST")
                        .Q2("Q2 TEST")
                        .Q3("Q3 TEST")
                        .imagePath("img_path_min_15")
                        .build()
        );

        System.out.println("===================USER SAVE TEST===================");
        user = userService.findByUserId(user.getId());
        System.out.println("user : " + user.toString());

        System.out.println("===================LETTER SAVE TEST===================");
        letter = letterService.findById(letter.getId());
        System.out.println("letter : " + letter.toString());

        boolean isSameUserId = letter.getUser_id().equals(user);
        assertTrue(isSameUserId);

        letterService.delete(letter);

        assertFalse(userService.existsById(user.getId()));
        assertFalse(letterService.existsById(letter.getId()));
    }

    @Test
    public void userMatchTest() {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .userName("userM")
                .birthDate(LocalDate.of(1994,4,10))
                .email("test01@gmail.com")
                .gender(Gender.MALE)
                .height(173)
                .phone("01012345678")
                .build());
        users.add(User.builder()
                .userName("userF")
                .phone("01043218765")
                .height(166)
                .gender(Gender.FEMALE)
                .email("test02@gmail.com")
                .birthDate(LocalDate.of(1994,3,16))
                .build());
        userService.saveAll(users);


        List<Letter> letters = new ArrayList<>();
        letters.add(Letter.builder()
                .user_id(users.get(users.size()-2))
                .imagePath("img_path_min_15")
                .Q1("Q1 for Male")
                .Q2("Q2 for Male")
                .Q3("Q3 for Male")
                .build());
        letters.add(Letter.builder()
                .user_id(users.get(users.size()-1))
                .imagePath("img_path_min_15")
                .Q1("Q1 for Female")
                .Q2("Q2 for Female")
                .Q3("Q3 for Female")
                .build());
        letterService.saveAll(letters);

        User userM = userService.findByEmail("test01@gmail.com");
        User userW = userService.findByEmail("test02@gmail.com");
        MatchInfo matchInfo = matchInfoService.save(
                MatchInfo.builder()
                        .userMaleId(userM)
                        .userFemaleId(userW)
                        .build()
        );

        for(int i=1;i<=3;i++){
            Movie movie = movieService.save(
                    Movie.builder()
                            .movieTitle("movie_0" + i)
                            .build());
            MatchMovie matchMovie = matchMovieService.save(
                    MatchMovie.builder()
                            .movie(movie)
                            .matchInfo(matchInfo)
                            .build()
            );

            Place place = placeService.save(
                    Place.builder()
                            .placeName("place_0" + i)
                            .build());
            MatchPlace matchPlace = matchPlaceService.save(
                    MatchPlace.builder()
                            .place(place)
                            .matchInfo(matchInfo)
                            .build()
            );

            TheDay theDay = theDayService.save(
                    TheDay.builder()
                            .dayDate(LocalDate.of(2020, 3, 16+i-1))
                            .dayTime(DayTime._7PM)
                            .build()
            );
            MatchTheDay matchTheDay = matchTheDayService.save(
                    MatchTheDay.builder()
                            .theDay(theDay)
                            .matchInfo(matchInfo)
                            .build()
            );
        }

        matchInfo = matchInfoService.findByIdFecthAll(matchInfo.getId());

        assertEquals(matchInfo.getUserMaleId().getUserName(), userM.getUserName());
        assertEquals(matchInfo.getUserFemaleId().getUserName(), userW.getUserName());

        for(MatchTheDay mt : matchInfo.getThe_days()){
            System.out.println("logging_theDays_of_matchInfo : "+mt.getId()+" : " + mt.getTheDay().getId()+"_"+mt.getTheDay().getDayDate());
            TheDay theDay = theDayService.findByIdFetchAll(mt.getTheDay().getId());
            assertEquals(mt.getTheDay().getId(), theDay.getId());
            for(MatchTheDay mt2 : theDay.getMatchTheDays()) {
                System.out.println("logging_theDay : " + theDay.getId()+ " : " + mt2.getId());
                assertEquals(mt.getId(), mt2.getId());
            }
        }

        for(MatchPlace mp : matchInfo.getPlaces()){
            System.out.println("logging_places_of_matchInfo : "+mp.getId()+" : " + mp.getPlace().getId()+"_"+mp.getPlace().getPlaceName());
            Place place = placeService.findByIdFetchAll(mp.getPlace().getId());
            assertEquals(mp.getPlace().getId(), place.getId());
            for(MatchPlace mp2 : place.getMatchPlaces()){
                System.out.println("logging_place : " + place.getId()+ " : " + mp2.getId());
                assertEquals(mp.getId(), mp2.getId());
            }
        }

        for(MatchMovie mv : matchInfo.getMovies()){
            System.out.println("logging_movies_of_matchInfo : "+mv.getId()+" : " + mv.getMovie().getId()+"_"+mv.getMovie().getMovieTitle());
            Movie movie = movieService.findByIdFetchAll(mv.getMovie().getId());
            assertEquals(mv.getMovie().getId(), movie.getId());
            for(MatchMovie mv2 : movie.getMatchMovies()){
                System.out.println("logging_movie : " + movie.getId()+" : " + mv2.getId());
                assertEquals(mv.getId(), mv2.getId());
            }
        }

        assertEquals(matchInfo.getThe_days().size(), 3);
        assertEquals(matchInfo.getMovies().size(), 3);
        assertEquals(matchInfo.getPlaces().size(), 3);


        /*
            Deleting Test
        */

        matchInfoService.delete(matchInfo);

        List<TheDay> theDays = theDayService.findFetchAll();
        System.out.println("TheDays_size: " + theDays.size());
        for(TheDay theDay : theDays) {
            System.out.println("TheDay_id" + theDay.getId() + " : " + theDay.getMatchTheDays().size());
            theDayService.delete(theDay);
        }

        List<Place> places = placeService.findFetchAll();
        System.out.println("Place_size: " + places.size());
        for(Place place : places) {
            System.out.println("Place_id" + place.getId() + " : " + place.getMatchPlaces().size());
            placeService.delete(place);
        }

        List<Movie> movies = movieService.findFetchAll();
        System.out.println("movies_size: " + movies.size());
        for(Movie movie : movies) {
            System.out.println("Movie_id" + movie.getId() + " : " + movie.getMatchMovies().size());
            movieService.delete(movie);
        }

        letterService.delete(letters.get(0));
        letterService.delete(letters.get(1));
    }
}

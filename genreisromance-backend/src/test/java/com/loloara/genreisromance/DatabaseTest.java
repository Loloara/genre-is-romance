package com.loloara.genreisromance;

import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.common.util.AuthProvider;
import com.loloara.genreisromance.common.util.AuthoritiesConstant;
import com.loloara.genreisromance.common.util.DayTime;
import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.model.domain.*;
import com.loloara.genreisromance.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserAuthorityRepository userAuthorityRepository;

    @Autowired
    LetterRepository letterRepository;

    @Autowired
    MatchInfoRepository matchInfoRepository;

    @Autowired
    MatchMovieRepository matchMovieRepository;

    @Autowired
    MatchPlaceRepository matchPlaceRepository;

    @Autowired
    MatchTheDayRepository matchTheDayRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    TheDayRepository theDayRepository;

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
        User user = userRepository.save(
                User.builder()
                        .userName("test99")
                        .birthDate(LocalDate.of(1994,4,10))
                        .email("test99@gmail.com")
                        .gender(Gender.MALE)
                        .height(173)
                        .phone("01043215678")
                        .provider(AuthProvider.LOCAL)
                        .password(passwordEncoder.encode("test1@3$"))
                        .build()
        );

        User finalUser = user;
        Authority auth = authorityRepository.findById(AuthoritiesConstant.USER).orElseThrow(() -> new ApiException("no auth"));
        UserAuthority uAuth = userAuthorityRepository.save(UserAuthority.builder().user(finalUser).authority(auth).build());

        Letter letter = letterRepository.save(
                Letter.builder()
                        .user_id(user)
                        .q1("Q1 TEST")
                        .q2("Q2 TEST")
                        .q3("Q3 TEST")
                        .imagePath("img_path_min_15")
                        .build()
        );

        System.out.println("===================USER SAVE TEST===================");
        user = userRepository.findById(user.getId()).orElseThrow(() -> new ApiException("user not found"));
        System.out.println("user : " + user.toString());

        System.out.println("===================LETTER SAVE TEST===================");
        letter = letterRepository.findById(letter.getId()).orElseThrow(() -> new ApiException("letter not found"));
        System.out.println("letter : " + letter.toString());

        Long userId = user.getId();
        Long letterId = letter.getId();

        assertEquals(letter.getUser_id(), user);

        deleteForUserLetterSaveTest(letterId);

        assertFalse(letterRepository.existsById(userId));
        assertFalse(letterRepository.existsById(letterId));
    }

    @Transactional
    public void deleteForUserLetterSaveTest(Long letterId) {
        letterRepository.deleteById(letterId);
    }

    @Test
    public void userMatchTest() {
        final String TEST_EMAIL_MALE = "test11@gmail.com";
        final String TEST_EMAIL_FEMALE = "test12@gmail.com";
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .userName("userM")
                .birthDate(LocalDate.of(1994,4,10))
                .email(TEST_EMAIL_MALE)
                .gender(Gender.MALE)
                .height(173)
                .phone("01023232323")
                .provider(AuthProvider.LOCAL)
                .password(passwordEncoder.encode("qwer1@3$"))
                .build());
        users.add(User.builder()
                .userName("userF")
                .phone("01032323232")
                .height(166)
                .gender(Gender.FEMALE)
                .email(TEST_EMAIL_FEMALE)
                .birthDate(LocalDate.of(1994,3,16))
                .provider(AuthProvider.LOCAL)
                .password(passwordEncoder.encode("qwer1@3$"))
                .build());
        userRepository.saveAll(users);

        List<Letter> letters = new ArrayList<>();
        letters.add(Letter.builder()
                .user_id(users.get(users.size()-2))
                .imagePath("img_path_min_15")
                .q1("Q1 for Male")
                .q2("Q2 for Male")
                .q3("Q3 for Male")
                .build());
        letters.add(Letter.builder()
                .user_id(users.get(users.size()-1))
                .imagePath("img_path_min_15")
                .q1("Q1 for Female")
                .q2("Q2 for Female")
                .q3("Q3 for Female")
                .build());
        letterRepository.saveAll(letters);

        User userM = userRepository.findByEmail(TEST_EMAIL_MALE).orElseThrow(() -> new ApiException("Not Found"));
        User userW = userRepository.findByEmail(TEST_EMAIL_FEMALE).orElseThrow(() -> new ApiException("Not Found"));
        MatchInfo matchInfo = matchInfoRepository.save(
                MatchInfo.builder()
                        .userMaleId(userM)
                        .userFemaleId(userW)
                        .build()
        );

        for(int i=1;i<=3;i++){
            Movie movie = movieRepository.save(
                    Movie.builder()
                            .movieTitle("movie_0" + i)
                            .build());
            MatchMovie matchMovie = matchMovieRepository.save(
                    MatchMovie.builder()
                            .movie(movie)
                            .matchInfo(matchInfo)
                            .build()
            );

            Place place = placeRepository.save(
                    Place.builder()
                            .placeName("place_0" + i)
                            .build());
            MatchPlace matchPlace = matchPlaceRepository.save(
                    MatchPlace.builder()
                            .place(place)
                            .matchInfo(matchInfo)
                            .build()
            );

            TheDay theDay = theDayRepository.save(
                    TheDay.builder()
                            .dayDate(LocalDate.of(2020, 3, 16+i-1))
                            .dayTime(DayTime._7PM)
                            .build()
            );
            MatchTheDay matchTheDay = matchTheDayRepository.save(
                    MatchTheDay.builder()
                            .theDay(theDay)
                            .matchInfo(matchInfo)
                            .build()
            );
        }

        matchInfo = matchInfoRepository.findByIdFetchAll(matchInfo.getId()).orElseThrow(() -> new ApiException("Not Found"));

        assertEquals(matchInfo.getUserMaleId().getUserName(), userM.getUserName());
        assertEquals(matchInfo.getUserFemaleId().getUserName(), userW.getUserName());

        for(MatchTheDay mt : matchInfo.getThe_days()){
            System.out.println("logging_theDays_of_matchInfo : "+mt.getId()+" : " + mt.getTheDay().getId()+"_"+mt.getTheDay().getDayDate());
            TheDay theDay = theDayRepository.findByIdFetchAll(mt.getTheDay().getId()).orElseThrow(() -> new ApiException("Not Found"));
            assertEquals(mt.getTheDay().getId(), theDay.getId());
            for(MatchTheDay mt2 : theDay.getMatchTheDays()) {
                System.out.println("logging_theDay : " + theDay.getId()+ " : " + mt2.getId());
                assertEquals(mt.getId(), mt2.getId());
            }
        }

        for(MatchPlace mp : matchInfo.getPlaces()){
            System.out.println("logging_places_of_matchInfo : "+mp.getId()+" : " + mp.getPlace().getId()+"_"+mp.getPlace().getPlaceName());
            Place place = placeRepository.findByIdFetchAll(mp.getPlace().getId()).orElseThrow(() -> new ApiException("Not Found"));
            assertEquals(mp.getPlace().getId(), place.getId());
            for(MatchPlace mp2 : place.getMatchPlaces()){
                System.out.println("logging_place : " + place.getId()+ " : " + mp2.getId());
                assertEquals(mp.getId(), mp2.getId());
            }
        }

        for(MatchMovie mv : matchInfo.getMovies()){
            System.out.println("logging_movies_of_matchInfo : "+mv.getId()+" : " + mv.getMovie().getId()+"_"+mv.getMovie().getMovieTitle());
            Movie movie = movieRepository.findByIdFetchAll(mv.getMovie().getId()).orElseThrow(() -> new ApiException("Not Found"));
            assertEquals(mv.getMovie().getId(), movie.getId());
            for(MatchMovie mv2 : movie.getMatchMovies()){
                System.out.println("logging_movie : " + movie.getId()+" : " + mv2.getId());
                assertEquals(mv.getId(), mv2.getId());
            }
        }

        assertEquals(matchInfo.getThe_days().size(), 3);
        assertEquals(matchInfo.getMovies().size(), 3);
        assertEquals(matchInfo.getPlaces().size(), 3);



        //    Deleting Test


        matchInfoRepository.delete(matchInfo);

        List<TheDay> theDays = theDayRepository.findFetchAll();
        System.out.println("TheDays_size: " + theDays.size());
        for(TheDay theDay : theDays) {
            System.out.println("TheDay_id: " + theDay.getId() + " // have " + theDay.getMatchTheDays().size() + " MatchTheDays");
            theDayRepository.delete(theDay);
        }

        List<Place> places = placeRepository.findFetchAll();
        System.out.println("Place_size: " + places.size());
        for(Place place : places) {
            System.out.println("Place_id: " + place.getId() + " // have " + place.getMatchPlaces().size() + " MatchPlaces");
            placeRepository.delete(place);
        }

        List<Movie> movies = movieRepository.findFetchAll();
        System.out.println("movies_size: " + movies.size());
        for(Movie movie : movies) {
            System.out.println("Movie_id: " + movie.getId() + " // have " + movie.getMatchMovies().size() + " MatchMovies");
            movieRepository.delete(movie);
        }

        letterRepository.delete(letters.get(0));
        letterRepository.delete(letters.get(1));
    }
}

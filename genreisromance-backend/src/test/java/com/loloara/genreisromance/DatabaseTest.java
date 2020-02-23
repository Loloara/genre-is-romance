package com.loloara.genreisromance;

import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.model.Letter;
import com.loloara.genreisromance.model.User;
import com.loloara.genreisromance.service.LetterService;
import com.loloara.genreisromance.service.UserService;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        userService.save(
                User.builder()
                        .userName("Lucas")
                        .age(27)
                        .birthDate(LocalDate.of(1994,4,10))
                        .email("test@gmail.com")
                        .gender(Gender.MALE)
                        .height(173)
                        .phone("01012345678")
                        .build()
        );
        List<User> users = userService.findAll();

        letterService.save(
                Letter.builder()
                        .user_id(users.get(0))
                        .Q1("Q1 TEST")
                        .Q2("Q2 TEST")
                        .Q3("Q3 TEST")
                        .imagePath("img_path_min_15")
                        .build()
        );
        List<Letter> letters = letterService.findAll();

        System.out.println("===================USER SAVE TEST===================");
        User user = users.get(users.size()-1);
        System.out.println("user : " + user.toString());

        System.out.println("===================LETTER SAVE TEST===================");
        Letter letter = letterService.findByIdEager(letters.get(letters.size()-1).getId());
        System.out.println("letter : " + letter.toString());

        assertEquals(users.size(), 1);
        assertEquals(letters.size(), 1);

        letterService.delete(letter);

        users = userService.findAll();
        letters = letterService.findAll();
        assertEquals(users.size(), 0);
        assertEquals(letters.size(), 0);
    }
}

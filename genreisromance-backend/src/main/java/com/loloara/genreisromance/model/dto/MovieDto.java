package com.loloara.genreisromance.model.dto;

import com.loloara.genreisromance.model.domain.MatchInfo;
import com.loloara.genreisromance.model.domain.MatchMovie;
import com.loloara.genreisromance.model.domain.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

public class MovieDto {

    @Getter @Builder
    public static class Create {

        @NotNull(message = "영화 제목은 필수 입력 값입니다.")
        private String movieTitle;
    }

    @Getter @Builder
    public static class Update {

        private String movieTitle;
    }

    @Getter @Setter
    @NoArgsConstructor(access = PROTECTED)
    public static class MovieInfo {

        private String movieTitle;
        private Set<MatchInfo> matchInfos;

        public MovieInfo(Movie movie) {
            movieTitle = movie.getMovieTitle();
            setMatchInfos(movie.getMatchMovies());
        }

        public void setMatchInfos(Set<MatchMovie> matchInfos) {
            this.matchInfos = matchInfos.stream().map(MatchMovie::getMatchInfo).collect(Collectors.toSet());
        }
    }

    @Getter @Setter
    public static class MovieInfos {
        private List<MovieDto.MovieInfo> movieInfos;

        public MovieInfos(List<Movie> movies) {
            movieInfos = new ArrayList<>();
            for(Movie m : movies) {
                movieInfos.add(new MovieDto.MovieInfo(m));
            }
        }
    }
}

package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import javax.swing.plaf.nimbus.State;
import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Film> get(Integer id) {
        log.info("Поиск фильма по id={}", id);
        String sql = "SELECT F.*,\n" +
                "       M.NAME AS MPA_NAME,\n" +
                "       group_concat(concat(G.ID, ':', G.NAME) separator ',') AS GENRE\n" +
                "FROM FILMS F\n" +
                "LEFT JOIN FILM_GENRE FG ON F.ID = FG.FILM_ID\n" +
                "LEFT JOIN MPA M ON F.MPA = M.ID\n" +
                "LEFT JOIN GENRE G ON FG.GENRE_ID = G.ID\n" +
                "WHERE F.ID = ?\n" +
                "GROUP BY F.ID";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::filmBuilder, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT F.*,\n" +
                "       M.NAME AS MPA_NAME,\n" +
                "       group_concat(concat(G.ID, ':', G.NAME) separator ',') AS GENRE\n" +
                "FROM FILMS F\n" +
                "LEFT JOIN FILM_GENRE FG ON F.ID = FG.FILM_ID\n" +
                "LEFT JOIN MPA M ON F.MPA = M.ID\n" +
                "LEFT JOIN GENRE G ON FG.GENRE_ID = G.ID\n" +
                "GROUP BY F.ID";
        log.info("Получили все фильмы из базы данных");
        return jdbcTemplate.query(sql, this::filmBuilder);
    }




    @Override
    public void increaseRating(Integer filmId,Integer userId) {
        String sql = "UPDATE FILMS SET RATE=RATE+1 WHERE id = ?";
        jdbcTemplate.update(sql, filmId);
        String insertLikeAndUser = "INSERT INTO LIKE_TO_FILM (USER_ID, FILM_ID) VALUES(?,?)";
        jdbcTemplate.update(insertLikeAndUser,filmId,userId);
    }


    @Override
    public void decreaseRating(Integer filmId,Integer userId) {
        String sql = "UPDATE FILMS SET RATE=RATE-1 WHERE id = ?";
        jdbcTemplate.update(sql, filmId);
        String insertLikeAndUser = "DELETE FROM LIKE_TO_FILM WHERE USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(insertLikeAndUser,filmId,userId);
    }

    @Override
    public Film add(Film film) {
        String sql = "INSERT INTO FILMS (NAME, MPA, RATE, DESCRIPTION, RELEASE_DATE, DURATION) VALUES (?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder gkh = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setInt(2, film.getMpa().getId());
            ps.setInt(3, film.getRate());
            ps.setString(4, film.getDescription());
            ps.setDate(5, Date.valueOf(film.getReleaseDate()));
            ps.setInt(6, film.getDuration());
            return ps;
        }, gkh);
        Integer filmId = Objects.requireNonNull(gkh.getKey()).intValue();
        if (film.getGenres() != null) {
            String sqlInsGenre = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";

            batchInsert(film.getGenres(), sqlInsGenre,filmId);
        }
        film.setId(filmId);
        return film;
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update(
                "UPDATE FILMS SET NAME=?, MPA=?, RATE=?, DESCRIPTION=?, RELEASE_DATE=?, DURATION=? WHERE ID=?",
                film.getName(),
                film.getMpa().getId(),
                film.getRate(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId()

        );
        log.info("База была обновлена, добавлен 1 фильм");
        String sqlDelGenre = "DELETE FROM FILM_GENRE WHERE FILM_ID=?";
        jdbcTemplate.update(sqlDelGenre, film.getId());
        if (film.getGenres() != null && film.getGenres().size() > 0) {
            String sqlInsGenre = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";

            batchInsert(film.getGenres(), sqlInsGenre, film.getId());
        }
        return this.get(film.getId()).orElseThrow();
    }


    @Override
    public boolean containsFilm(Integer id) {
        String sql = "SELECT F.*, M.NAME AS MPA_NAME FROM FILMS F LEFT JOIN MPA M ON F.MPA = M.ID WHERE F.id = ?";
        return !jdbcTemplate.query(sql, this::filmBuilder, id).isEmpty();
    }


    public boolean containsLikeUserFilm(Integer filmId, Integer userId) {
        String sql = "SELECT * FROM LIKE_TO_FILM WHERE FILM_ID = ? AND USER_ID = ?";
        return !jdbcTemplate.query(sql,filmId,userId).isEmpty;
    }


    private Film filmBuilder(ResultSet rs, int row) throws SQLException {
        List<Genre> genreList;
        try {
            String genres = rs.getString("GENRE");
            genreList = Arrays.stream(genres.split(","))
                    .map(genreStr -> {
                        String[] genreArr = genreStr.split(":");
                        return Genre.builder()
                                .id(Integer.valueOf(genreArr[0]))
                                .name(genreArr[1])
                                .build();
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            genreList = Collections.emptyList();
        }
        Mpa mpa = Mpa.builder()
                .id(rs.getInt("MPA"))
                .name(rs.getString("MPA_NAME"))
                .build();
        return Film.builder()
                .id(rs.getInt("ID"))
                .name(rs.getString("NAME"))
                .genres(genreList)
                .mpa(mpa)
                .rate(rs.getInt("RATE"))
                .description(rs.getString("DESCRIPTION"))
                .releaseDate(rs.getDate("RELEASE_DATE").toLocalDate())
                .duration(rs.getInt("DURATION"))
                .build();
    }

    private void batchInsert(List<Genre> genreList, String sqlGenre, Integer filmId) {
        try {

            jdbcTemplate.batchUpdate(sqlGenre,
                    new BatchPreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, filmId);
                            ps.setInt(2, genreList.get(i).getId());
                        }

                        @Override
                        public int getBatchSize() {
                            return genreList.size();
                        }
                    }

            );
        }catch (Exception e) {
            log.info(e.getMessage());
        }
    }
        

}
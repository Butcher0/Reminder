package com.zml.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.zml.entity.Movie;

public class MovieDao {

	private static JdbcTemplate jdbcTemplate = JDBCHelper.createMysqlTemplate();

	public void saveMovice(Movie movie) {
		String sql = "INSERT INTO `movie` (`id`, `name`,`score`,`releaseTime`, `duration`, `actors`, `years`,`director`, `type`, `area`, `summary`,`cover` ) VALUES (null, ?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println(sql);
		jdbcTemplate.update(sql, movie.getName(), movie.getScore(), movie.getReleaseTime(), movie.getDuration(), movie.getActors(),
				movie.getYears(), movie.getDirector(), movie.getType(), movie.getArea(), movie.getSummary(),
				movie.getCover());
	}
	
	public int findMovieByName(String name) {
		String sql ="SELECT COUNT(*) FROM `movie` WHERE name = ?";
		return jdbcTemplate.queryForObject(sql.toString(), new Object[]{name}, Integer.class);
	}
	
	public List<Movie> findUpcomingMovies() {
		String sql = "SELECT * FROM `movie` WHERE  DATEDIFF(`releaseTime`, NOW()) = 12";
		return (List<Movie>) jdbcTemplate.query(sql, new RowMapper<Movie>(){

			@Override
			public Movie mapRow(ResultSet rs, int rowNumber) throws SQLException {
				Movie m = new Movie();
				m.setId(rs.getLong("id"));
				m.setName(rs.getString("name"));
				m.setReleaseTime(rs.getDate("releaseTime"));
				m.setScore(rs.getString("score"));
				m.setActors(rs.getString("actors"));
				m.setArea(rs.getString("area"));
				m.setDuration(rs.getString("duration"));
				m.setType(rs.getString("type"));
				m.setArea(rs.getString("area"));
				m.setYears(rs.getString("years"));
				m.setDirector(rs.getString("director"));
				m.setCover(rs.getString("cover"));
				return m; 
			}});
	}
}

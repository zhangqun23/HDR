package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the movie_info_sort database table.
 * 
 */
@Entity
@Table(name="movie_info_sort")
@NamedQuery(name="MovieInfoSort.findAll", query="SELECT m FROM MovieInfoSort m")
public class MovieInfoSort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private int display;

	@Column(name="movie_info_id", nullable=false)
	private int movieInfoId;

	@Column(name="movie_sort_id", nullable=false, length=45)
	private String movieSortId;

	public MovieInfoSort() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDisplay() {
		return this.display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public int getMovieInfoId() {
		return this.movieInfoId;
	}

	public void setMovieInfoId(int movieInfoId) {
		this.movieInfoId = movieInfoId;
	}

	public String getMovieSortId() {
		return this.movieSortId;
	}

	public void setMovieSortId(String movieSortId) {
		this.movieSortId = movieSortId;
	}

}
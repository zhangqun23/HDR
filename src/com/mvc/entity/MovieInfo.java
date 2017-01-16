package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the movie_info database table.
 * 
 */
@Entity
@Table(name="movie_info")
@NamedQuery(name="MovieInfo.findAll", query="SELECT m FROM MovieInfo m")
public class MovieInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int movie_id;

	@Column(nullable=false, length=50)
	private String director;

	private int display;

	@Column(nullable=false)
	private int fileExists;

	@Column(nullable=false, length=100)
	private String movie_actor;

	@Column(length=50)
	private String movie_class;

	@Column(length=30)
	private String movie_country;

	@Lob
	private String movie_description;

	@Column(nullable=false, length=100)
	private String movie_file;

	@Column(nullable=false)
	private int movie_freeOrNot;

	@Column(nullable=false, length=20)
	private String movie_language;

	@Column(nullable=false, length=50)
	private String movie_name;

	@Column(nullable=false, length=100)
	private String movie_path;

	@Column(nullable=false, length=100)
	private String movie_picturePath;

	@Column(length=30)
	private String movie_release;

	@Column(length=100)
	private String movie_Screenwriter;

	@Column(nullable=false, length=30)
	private String movie_time;

	@Column(nullable=false)
	private int movieSort;

	@Column(nullable=false, length=24)
	private String mt_id;

	@Column(length=30)
	private String reserve1;

	@Column(length=30)
	private String reserve2;

	@Column(length=30)
	private String reserve3;

	public MovieInfo() {
	}

	public int getMovie_id() {
		return this.movie_id;
	}

	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getDisplay() {
		return this.display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public int getFileExists() {
		return this.fileExists;
	}

	public void setFileExists(int fileExists) {
		this.fileExists = fileExists;
	}

	public String getMovie_actor() {
		return this.movie_actor;
	}

	public void setMovie_actor(String movie_actor) {
		this.movie_actor = movie_actor;
	}

	public String getMovie_class() {
		return this.movie_class;
	}

	public void setMovie_class(String movie_class) {
		this.movie_class = movie_class;
	}

	public String getMovie_country() {
		return this.movie_country;
	}

	public void setMovie_country(String movie_country) {
		this.movie_country = movie_country;
	}

	public String getMovie_description() {
		return this.movie_description;
	}

	public void setMovie_description(String movie_description) {
		this.movie_description = movie_description;
	}

	public String getMovie_file() {
		return this.movie_file;
	}

	public void setMovie_file(String movie_file) {
		this.movie_file = movie_file;
	}

	public int getMovie_freeOrNot() {
		return this.movie_freeOrNot;
	}

	public void setMovie_freeOrNot(int movie_freeOrNot) {
		this.movie_freeOrNot = movie_freeOrNot;
	}

	public String getMovie_language() {
		return this.movie_language;
	}

	public void setMovie_language(String movie_language) {
		this.movie_language = movie_language;
	}

	public String getMovie_name() {
		return this.movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public String getMovie_path() {
		return this.movie_path;
	}

	public void setMovie_path(String movie_path) {
		this.movie_path = movie_path;
	}

	public String getMovie_picturePath() {
		return this.movie_picturePath;
	}

	public void setMovie_picturePath(String movie_picturePath) {
		this.movie_picturePath = movie_picturePath;
	}

	public String getMovie_release() {
		return this.movie_release;
	}

	public void setMovie_release(String movie_release) {
		this.movie_release = movie_release;
	}

	public String getMovie_Screenwriter() {
		return this.movie_Screenwriter;
	}

	public void setMovie_Screenwriter(String movie_Screenwriter) {
		this.movie_Screenwriter = movie_Screenwriter;
	}

	public String getMovie_time() {
		return this.movie_time;
	}

	public void setMovie_time(String movie_time) {
		this.movie_time = movie_time;
	}

	public int getMovieSort() {
		return this.movieSort;
	}

	public void setMovieSort(int movieSort) {
		this.movieSort = movieSort;
	}

	public String getMt_id() {
		return this.mt_id;
	}

	public void setMt_id(String mt_id) {
		this.mt_id = mt_id;
	}

	public String getReserve1() {
		return this.reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return this.reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return this.reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

}
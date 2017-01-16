package com.mvc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the movie_vod database table.
 * 
 */
@Entity
@Table(name="movie_vod")
@NamedQuery(name="MovieVod.findAll", query="SELECT m FROM MovieVod m")
public class MovieVod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private int movieId;

	@Column(nullable=false, length=45)
	private String roomNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date vodTime;

	public MovieVod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMovieId() {
		return this.movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public Date getVodTime() {
		return this.vodTime;
	}

	public void setVodTime(Date vodTime) {
		this.vodTime = vodTime;
	}

}
package com.eya.getstarted.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "speakers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"HibernateLazyInitializer", "handler", "ByteBuddyInterceptor"})
public class Speaker {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long speaker_id;
	private String first_name;
	private String last_name;
	private String title;
	private String company;
	private String speaker_bio;
	@Lob
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private byte[] speaker_photo;
	@ManyToMany(mappedBy = "speakers")
	@JsonIgnore
	private List<Session> sessions;


}

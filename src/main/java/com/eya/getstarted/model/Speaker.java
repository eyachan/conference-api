package com.eya.getstarted.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity(name = "speakers")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

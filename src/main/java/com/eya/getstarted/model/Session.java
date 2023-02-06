package com.eya.getstarted.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"HibernateLazyInitializer","handler"})
public class Session {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long session_id;
	private String session_name;
	private String session_description;
	private Integer session_length;
	@ManyToMany
	@JoinTable(
			name = "session_speakers",
			joinColumns = @JoinColumn(name = "session_id"),
			inverseJoinColumns = @JoinColumn(name = "speaker_id")
	)
	//@JsonIgnore
	private List<Speaker> speakers;

}

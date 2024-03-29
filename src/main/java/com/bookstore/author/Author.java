package com.bookstore.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author implements Serializable {
    private String name;
    private String surname;
    private String country;
    private LocalDate dateOfBirth;
    private String shortBio;
}

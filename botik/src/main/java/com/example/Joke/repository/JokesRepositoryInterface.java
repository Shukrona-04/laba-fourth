package com.example.Joke.repository;

import com.example.Joke.model.DBJokes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.Joke.model.DBJokes;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Joke.model.DBJokes;

import java.util.List;

@Repository
public interface JokesRepositoryInterface extends JpaRepository<DBJokes, Long> {
    List<DBJokes> findFirst5ByOrderByIdAsc();

    @Query(value = "SELECT * FROM jokes ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    DBJokes getRandomJoke();
}

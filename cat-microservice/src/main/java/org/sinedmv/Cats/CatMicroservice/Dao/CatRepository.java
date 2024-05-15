package org.sinedmv.Cats.CatMicroservice.Dao;

import org.sinedmv.Cats.Entities.Enums.Color;
import org.sinedmv.Cats.Entities.Models.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("catRepository")
public interface CatRepository extends JpaRepository<Cat, Integer> {
    Optional<Cat> findCatById(Integer id);
    List<Cat> findAllByColor(Color color);
    List<Cat> findAllByBreed(String breed);
    List<Cat> findAllByName(String name);
}

package org.sinedmv.Cats.OwnerMicroservice.Dao;

import org.sinedmv.Cats.Entities.Models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ownerRepository")
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    Optional<Owner> findOwnerById(Integer id);
    List<Owner> findAllByName(String name);
}

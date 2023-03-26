package ru.practicum.main.categorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {

    @Query(value = "select * " +
            "from categories as c " +
            "ORDER BY c.id ASC " +
            "LIMIT ?2 OFFSET ?1",
            nativeQuery = true)
    List<Categorie> getCategories(int from, int size);


    @Query(value = "select * " +
            "from categories as c " +
            "WHERE c.name=?1 ",
            nativeQuery = true)
    List<Categorie> getName(String name);
}

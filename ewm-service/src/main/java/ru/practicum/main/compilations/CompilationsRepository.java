package ru.practicum.main.compilations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompilationsRepository extends JpaRepository<Compilation, Integer> {
    @Query(value = "select * " +
            "from compilations as c " +
            "WHERE c.pinned = ?3 " +
            "LIMIT ?2 OFFSET ?1 ",
            nativeQuery = true)
    List<Compilation> getEventForCompilation(int from, int size, Boolean pinned);

    @Query(value = "select * " +
            "from compilations as c " +
            "LIMIT ?2 OFFSET ?1 ",
            nativeQuery = true)
    List<Compilation> getEventForCompilationAll(int from, int size, Boolean pinned);
}

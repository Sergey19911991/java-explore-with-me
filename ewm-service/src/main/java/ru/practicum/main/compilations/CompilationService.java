package ru.practicum.main.compilations;

import java.util.List;

public interface CompilationService {

    Compilation creatCompilation(DtoCompilation dtoCompilation);

    Compilation getCompilation(int id);

    List<Compilation> getCompilations(int from, int size, Boolean pinned);

    void deletCompilation(int id);

    Compilation updateCompilation(int compId, DtoCompilation dtoCompilation);
}

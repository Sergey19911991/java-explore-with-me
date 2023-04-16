package ru.practicum.main.compilations;

import ru.practicum.main.compilations.dto.DtoCompilation;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    DtoCompilation creatCompilation(NewCompilationDto newCompilationDto);

    DtoCompilation getCompilation(int id);

    List<DtoCompilation> getCompilations(int from, int size, Boolean pinned);

    void deletCompilation(int id);

    DtoCompilation updateCompilation(int compId, UpdateCompilationRequest updateCompilationRequest);
}

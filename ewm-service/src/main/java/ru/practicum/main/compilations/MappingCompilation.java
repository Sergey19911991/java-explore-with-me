package ru.practicum.main.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.compilations.dto.DtoCompilation;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class MappingCompilation {


    public Compilation compilationNewCompilationDto(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        if (newCompilationDto.getPinned() != null) {
            compilation.setPinned(newCompilationDto.getPinned());
        }
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;

    }

    public Compilation updateCompilationRequest(UpdateCompilationRequest updateCompilationRequest, Compilation compilation) {
        if (updateCompilationRequest.getTitle() != null) {
            if (!updateCompilationRequest.getTitle().isBlank()) {
                compilation.setTitle(updateCompilationRequest.getTitle());
            }
        }
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(compilation.getPinned());
        }
        return compilation;
    }

    public DtoCompilation dtoCompilation(Compilation compilation) {
        DtoCompilation dtoCompilation = new DtoCompilation();
        dtoCompilation.setId(compilation.getId());
        dtoCompilation.setPinned(compilation.getPinned());
        dtoCompilation.setTitle(compilation.getTitle());
        dtoCompilation.setEvents(compilation.getEvents());
        return dtoCompilation;
    }

}


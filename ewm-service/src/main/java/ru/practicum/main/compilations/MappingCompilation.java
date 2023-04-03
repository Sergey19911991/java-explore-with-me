package ru.practicum.main.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.compilations.dto.DtoCompilation;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;
import ru.practicum.main.events.EventsRepository;
import ru.practicum.main.exception.RequestException;

@Slf4j
@RequiredArgsConstructor
@Service
public class MappingCompilation {
    private final EventsRepository eventsRepository;


    public Compilation compilationNewCompilationDto(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        if (newCompilationDto.getPinned() != null) {
            compilation.setPinned(newCompilationDto.getPinned());
        }
        compilation.setTitle(newCompilationDto.getTitle());
        if (newCompilationDto.getEvents() != null) {
            compilation.setEvents(eventsRepository.getEventForCompilation(newCompilationDto.getEvents()));
            return compilation;
        } else {
            log.error("Неправильное тело запроса!");
            throw new RequestException("Неправильное тело запроса!");
        }
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


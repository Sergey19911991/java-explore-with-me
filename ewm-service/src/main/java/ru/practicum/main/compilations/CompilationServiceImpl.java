package ru.practicum.main.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.compilations.dto.DtoCompilation;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;
import ru.practicum.main.events.EventsRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.RequestException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationsRepository compilationsRepository;

    private final EventsRepository eventsRepository;

    private final MappingCompilation mappingCompilation;

    @Override
    public DtoCompilation creatCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = mappingCompilation.compilationNewCompilationDto(newCompilationDto);
        if (newCompilationDto.getEvents() != null) {
            compilation.setEvents(eventsRepository.getEventForCompilation(newCompilationDto.getEvents()));
           // return compilation;
        } else {
            log.error("Неправильное тело запроса!");
            throw new RequestException("Неправильное тело запроса!");
        }
        log.info("Создана подборка событий");
        compilationsRepository.save(compilation);
        return mappingCompilation.dtoCompilation(compilation);
    }

    @Override
    public DtoCompilation getCompilation(int id) {
        log.info("Информация о подборке событий с id = {}", id);
        return mappingCompilation.dtoCompilation(compilationsRepository.findById(id).get());
    }

    @Override
    public List<DtoCompilation> getCompilations(int from, int size, Boolean pinned) {
        if (pinned == null) {
            List<Compilation> compilations = compilationsRepository.getEventForCompilationAll(from, size);
            List<DtoCompilation> dtoCompilations = new ArrayList<>();
            for (Compilation compilation : compilations) {
                dtoCompilations.add(mappingCompilation.dtoCompilation(compilation));
            }
            log.info("Информация о подборках событий, не закрепленных на главной странице");
            return dtoCompilations;
        } else {
            List<Compilation> compilations = compilationsRepository.getEventForCompilation(from, size, pinned);
            List<DtoCompilation> dtoCompilations = new ArrayList<>();
            for (Compilation compilation : compilations) {
                dtoCompilations.add(mappingCompilation.dtoCompilation(compilation));
            }
            log.info("Информация о подборках событий, закрепленных на главной странице");
            return dtoCompilations;
        }
    }

    public void deletCompilation(int id) {
        log.info("Удалена подборка событий с id = {}", id);
        compilationsRepository.deleteById(id);
    }

    public DtoCompilation updateCompilation(int compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationsRepository.findById(compId).orElseThrow(() -> new NotFoundException("Подборка событий не найдена!"));
        compilation.setEvents(eventsRepository.getEventForCompilation(updateCompilationRequest.getEvents()));
        log.info("Перезаписана подборка событий с id = {}", compId);
        compilation = mappingCompilation.updateCompilationRequest(updateCompilationRequest, compilation);
        compilationsRepository.save(compilation);
        return mappingCompilation.dtoCompilation(compilation);
    }
}

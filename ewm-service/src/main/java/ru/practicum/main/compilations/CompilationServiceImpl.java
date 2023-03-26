package ru.practicum.main.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.events.EventsRepository;
import ru.practicum.main.exception.RequestException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationsRepository compilationsRepository;

    private final EventsRepository eventsRepository;

    @Override
    public Compilation creatCompilation(DtoCompilation dtoCompilation) {
        Compilation compilation = new Compilation();
        compilation.setPinned(dtoCompilation.getPinned());
        if (dtoCompilation.getTitle() != null) {
            compilation.setTitle(dtoCompilation.getTitle());
        } else {
            throw new RequestException("Неправильное тело запроса!");
        }
        compilation.setEvents(eventsRepository.getEventForCompilation(dtoCompilation.getEvents()));
        return compilationsRepository.save(compilation);
    }

    @Override
    public Compilation getCompilation(int id) {
        return compilationsRepository.findById(id).get();
    }

    @Override
    public List<Compilation> getCompilations(int from, int size, Boolean pinned) {
        if (pinned == null) {
            return compilationsRepository.getEventForCompilationAll(from, size, pinned);
        } else {
            return compilationsRepository.getEventForCompilation(from, size, pinned);
        }
    }

    public void deletCompilation(int id) {
        compilationsRepository.deleteById(id);
    }

    public Compilation updateCompilation(int compId, DtoCompilation dtoCompilation) {
        Compilation compilation = compilationsRepository.findById(compId).get();
        compilation.setEvents(eventsRepository.getEventForCompilation(dtoCompilation.getEvents()));
        compilation.setTitle(dtoCompilation.getTitle());
        compilation.setPinned(compilation.getPinned());
        return compilationsRepository.save(compilation);
    }
}

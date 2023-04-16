package hit;

import client.BaseClient;
import ru.practicum.dto.DtoInletHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@Service
public class HitClient extends BaseClient {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public HitClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> createHit(DtoInletHit hit) {
        return post("/hit", hit);
    }

    public ResponseEntity<Object> getHits(String start, String end, String[] uri, boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uri", uri,
                "unique", unique
        );
        return get("/stats?start={start}&end={end}&uri={{uri}}&unique={unique}", parameters);
    }

    public DtoInletHit mappingHitDtoClient(HttpServletRequest request, String app) {
        DtoInletHit hit = new DtoInletHit();
        hit.setTimestamp(LocalDateTime.now().format(formatter));
        hit.setIp(request.getRemoteAddr());
        hit.setUri(request.getRequestURI());
        hit.setApp(app);
        return hit;
    }

}

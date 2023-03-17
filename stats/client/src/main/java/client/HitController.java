package client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class HitController {

    private final HitClient hitClient;

    @PostMapping(value = "/hit")
    public ResponseEntity<Object> creatHit(@RequestBody Hit hit) {
        return hitClient.createHit(hit);
    }
}




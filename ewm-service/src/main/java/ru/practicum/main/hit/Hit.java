package ru.practicum.main.hit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hit {
    private String ip;
    private String uri;
    private String app;
    private String timestamp;
}

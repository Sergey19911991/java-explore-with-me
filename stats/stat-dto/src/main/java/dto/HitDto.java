package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HitDto {
    private String app;
    private String uri;
    private int hits;
}

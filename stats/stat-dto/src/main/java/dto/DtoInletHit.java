package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoInletHit {
    private String app;
    private String uri;
    private int hits;
    private String ip;
    private String timestamp;
}

package client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hit {
    private int id;
    private String ip;
    private String uri;
    private String app;
    private String timestamp;
}

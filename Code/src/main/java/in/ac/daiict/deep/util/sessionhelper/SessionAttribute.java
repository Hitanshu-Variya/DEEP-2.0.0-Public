package in.ac.daiict.deep.util.sessionhelper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class SessionAttribute<T> {
    private T value;
    private LocalDateTime expiresAt;

    public SessionAttribute(T value, Duration duration) {
        this.value = value;
        this.expiresAt = LocalDateTime.now().plus(duration);
    }

    public boolean isExpired(){
        return expiresAt.isBefore(LocalDateTime.now());
    }
}

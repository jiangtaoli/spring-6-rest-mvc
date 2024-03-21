package guru.springframework.spring6restmvc.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

//@Data
@Builder
@Data
public class Customer {
    private UUID id;
    private Integer version;

    private String customerName;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

}

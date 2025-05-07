package co.com.nequi.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class TechnicalMessage {
    private String technicalMessage;
    private int code;

}

package ro.sisc.ttj.inttjence.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelAnswerChoiceDto {
    private MessageDto message;
}

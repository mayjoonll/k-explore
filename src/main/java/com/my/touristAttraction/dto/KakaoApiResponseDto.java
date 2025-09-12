package com.my.touristAttraction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data

public class KakaoApiResponseDto {
    @JsonProperty("documents")
    private List<DocumentDto> documentlist;
    @JsonProperty("meta")
    private MetaDto metaDto;


}

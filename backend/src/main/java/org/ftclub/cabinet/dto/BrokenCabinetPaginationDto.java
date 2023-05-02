package org.ftclub.cabinet.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrokenCabinetPaginationDto {

    private final List<BrokenCabinetDto> result;
    private final Integer totalLength;
}

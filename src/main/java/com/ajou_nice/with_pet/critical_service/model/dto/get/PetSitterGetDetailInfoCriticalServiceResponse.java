package com.ajou_nice.with_pet.critical_service.model.dto.get;

import com.ajou_nice.with_pet.critical_service.model.entity.PetSitterCriticalService;
import com.ajou_nice.with_pet.withpet_service.model.entity.PetSitterWithPetService;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class PetSitterGetDetailInfoCriticalServiceResponse {
    private Long petSitterCriticalServiceId;
    private Long criticalServiceId;
    private String criticalServiceName;
    private String criticalServiceIntroduction;
    private String criticalServiceImg;
    private int petSitterCriticalServicePrice;

    public static List<PetSitterGetDetailInfoCriticalServiceResponse> toList(List<PetSitterCriticalService> petSitterServiceList){
        return petSitterServiceList.stream().map(petSitterCriticalService -> PetSitterGetDetailInfoCriticalServiceResponse.builder()
                .petSitterCriticalServiceId(petSitterCriticalService.getId())
                .criticalServiceId(petSitterCriticalService.getCriticalService().getId())
                .criticalServiceName(petSitterCriticalService.getCriticalService().getServiceName())
                .criticalServiceIntroduction(petSitterCriticalService.getCriticalService().getIntroduction())
                .criticalServiceImg(petSitterCriticalService.getCriticalService().getServiceImg())
                .petSitterCriticalServicePrice(petSitterCriticalService.getPrice())
                .build()).collect(Collectors.toList());
    }
}

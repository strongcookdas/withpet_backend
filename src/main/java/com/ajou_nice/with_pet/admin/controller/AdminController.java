package com.ajou_nice.with_pet.admin.controller;


import com.ajou_nice.with_pet.admin.model.dto.*;
import com.ajou_nice.with_pet.admin.service.AdminService;
import com.ajou_nice.with_pet.applicant.model.dto.PetSitterApplicationResponse;
import com.ajou_nice.with_pet.critical_service.model.dto.CriticalServiceResponse;
import com.ajou_nice.with_pet.domain.dto.Response;
import com.ajou_nice.with_pet.applicant.model.dto.ApplicantBasicInfoResponse;
import com.ajou_nice.with_pet.domain.dto.petsitter.PetSitterBasicResponse;
import com.ajou_nice.with_pet.withpet_service.model.dto.WithPetServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/admins")
@Api(tags = "Administrator API")
public class AdminController {
    private final AdminService adminService;
    @PatchMapping("/accept-applicants/{userId}")
    @ApiOperation(value = "관리자의 펫시터 지원자 수락")
    public Response<PetSitterBasicResponse> acceptApplicant(@ApiIgnore Authentication authentication, @PathVariable("userId") Long userId) {

        PetSitterBasicResponse adminAcceptApplicantResponse = adminService.acceptApplicant(authentication.getName(), userId);

        return Response.success(adminAcceptApplicantResponse);
    }

    @PatchMapping("/refuse-applicant/{userId}")
	@ApiOperation(value = "관리자의 펫시터 지원자 거절")
	public Response<AdminApplicantResponse> refuseApplicant(@ApiIgnore Authentication authentication,  @PathVariable("userId") Long userId){

		AdminApplicantResponse adminApplicantResponse = adminService.refuseApplicant(authentication.getName(), userId);

		return Response.success(adminApplicantResponse);
	}

//	@GetMapping("/api/v1/admin/show-petsitters")
//	@ApiOperation(value = "관리자의 펫시터 리스트 조회")
//	public Response<List<PetSitterBasicResponse>> showPetSitters(@ApiIgnore Authentication authentication){
//		log.info(authentication.getName());
//		List<PetSitterBasicResponse> petSitterBasicResponses = adminService.showPetSitters(
//				authentication.getName());
//		return Response.success(petSitterBasicResponses);
//	}

    @GetMapping("/applicants")
    @ApiOperation(value = "펫시터 지원자 리스트 전체 확인")
    public Response<List<ApplicantBasicInfoResponse>> showApplicants(@ApiIgnore Authentication authentication) {
        List<ApplicantBasicInfoResponse> applicantList = adminService.showApplicants(
                authentication.getName());
        return Response.success(applicantList);
    }

    @GetMapping("applicants/{userId}")
    @ApiOperation(value = "펫시터 지원자 정보 상세 확인")
    public Response<PetSitterApplicationResponse> getApplicant(@ApiIgnore Authentication authentication, @PathVariable("userId") Long userId) {

        PetSitterApplicationResponse applicantCreateResponse = adminService.getApplicantDetailInfo(
                authentication.getName(), userId);

        return Response.success(applicantCreateResponse);
    }

    @PostMapping("/critical-service")
    @ApiOperation(value = "관리자의 필수 서비스 추가")
    public Response<CriticalServiceResponse> addCriticalService(@ApiIgnore Authentication authentication, @RequestBody @Valid
    AddCriticalServiceRequest addCriticalServiceRequest) {
        CriticalServiceResponse criticalServiceResponse = adminService.addCriticalService(
                authentication.getName(), addCriticalServiceRequest);
        return Response.success(criticalServiceResponse);
    }

    @PutMapping("/critical-service/{serviceId}")
    @ApiOperation(value = "관리자의 필수 서비스 수정")
    public Response<UpdateCriticalServiceResponse> updateCriticalService(@ApiIgnore Authentication authentication,@PathVariable("serviceId") Long serviceId, @RequestBody @Valid UpdateCriticalServiceRequest updateCriticalServiceRequest){
        UpdateCriticalServiceResponse updateCriticalServiceResponse = adminService.updateCriticalService(authentication.getName(), serviceId, updateCriticalServiceRequest);

        return Response.success(updateCriticalServiceResponse);
    }

    @PostMapping("/service")
    @ApiOperation(value = "관리자의 위드펫 서비스 추가")
    public Response<WithPetServiceResponse> addWithPetService(@ApiIgnore Authentication authentication, @RequestBody @Valid
    AddWithPetServiceRequest addWithPetServiceRequest) {
        WithPetServiceResponse withPetServiceResponse = adminService.addWithPetService(
                authentication.getName(), addWithPetServiceRequest);
        return Response.success(withPetServiceResponse);
    }

//	@PutMapping("/api/v1/admin/service")
//	@ApiOperation(value = "관리자의 위드펫 서비스 수정")
//	public Response<WithPetServiceResponse> updateWithPetService(@ApiIgnore Authentication authentication,@RequestBody @Valid
//			WithPetServiceModifyRequest withPetServiceModifyRequest){
//
//		log.info("=============== request update withPetService info : {} ==================",withPetServiceModifyRequest);
//
//		WithPetServiceResponse withPetServiceResponse = adminService.updateWithPetService(
//				authentication.getName(), withPetServiceModifyRequest);
//
//		log.info("=============== response update withPetService info : {} ==================",withPetServiceResponse);
//
//		return Response.success(withPetServiceResponse);
//	}
//
//	@PostMapping  ("/api/v1/admin/service")
//	@ApiOperation(value = "관리자의 위드펫 서비스 삭제")
//	public Response<List<WithPetServiceResponse>> deleteWithPetService(@ApiIgnore Authentication authentication,@RequestBody @Valid
//			WithPetServiceModifyRequest withPetServiceModifyRequest){
//
//		log.info("=============== request delete withPetService info : {} ==================",withPetServiceModifyRequest);
//
//		List<WithPetServiceResponse> withPetServiceResponses = adminService.deleteWithPetService(
//				authentication.getName(), withPetServiceModifyRequest);
//
//		log.info("=============== response deleted withPetService List : {} ==================",withPetServiceResponses);
//
//		return Response.success(withPetServiceResponses);
//	}
}

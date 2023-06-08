package com.ajou_nice.with_pet.review;


import com.ajou_nice.with_pet.controller.ReservationController;
import com.ajou_nice.with_pet.domain.entity.PetSitter;
import com.ajou_nice.with_pet.domain.entity.Reservation;
import com.ajou_nice.with_pet.domain.entity.Review;
import com.ajou_nice.with_pet.domain.entity.User;
import com.ajou_nice.with_pet.domain.entity.embedded.Address;
import com.ajou_nice.with_pet.enums.ReservationStatus;
import com.ajou_nice.with_pet.enums.UserRole;
import com.ajou_nice.with_pet.exception.AppException;
import com.ajou_nice.with_pet.exception.ErrorCode;
import com.ajou_nice.with_pet.repository.PetSitterRepository;
import com.ajou_nice.with_pet.repository.ReservationRepository;
import com.ajou_nice.with_pet.repository.ReviewRepository;
import com.ajou_nice.with_pet.repository.UserRepository;
import com.ajou_nice.with_pet.service.ReservationService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ReviewTest {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PetSitterRepository petSitterRepository;
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	ReservationController reservationController;
	@Autowired
	ReservationService reservationService;

	@Autowired
	ReviewRepository reviewRepository;

	private User user;
	private PetSitter petSitter;

	static{
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}

	@Transactional
	public void initialize(){
		Address address = Address.simpleAddressGenerator("213","adasd", "244");
		user = User.simpleUserForTest("장승현3", "simpleuser3", "1234", "jason5102@ajou.ac.kr",
				UserRole.ROLE_USER, "010-3931-5102", address);
		User user2 = User.simpleUserForTest("장승현4", "simplepetsitter4", "1234", "jason5102@ajou.ac.kr",
				UserRole.ROLE_PETSITTER, "010-3931-5102", address);
		userRepository.save(user); userRepository.save(user2);

		petSitter = PetSitter.simplePetSitterForTest(user2.getName(), user2.getPhone(), "www.google.com", "213", "sdfs",
				"dsfds", user2);
		petSitterRepository.save(petSitter);
	}

	@DisplayName("반려인의 후기 작성")
	@Transactional
	@Test
	public void postReviewTest() throws Exception{
	    //given
		initialize();
		LocalDateTime checkIn = LocalDateTime.of(2023, 6, 13, 5, 13);
		LocalDateTime checkOut = LocalDateTime.of(2023, 6, 14, 6, 13);
		Reservation reservation1 = Reservation.forSimpleTest(checkIn, checkOut, user, petSitter, 35000);
		reservation1.updateForTest("소형견", 10000);
		reservation1.updateStatus(ReservationStatus.DONE.toString());
		reservationRepository.save(reservation1);
	    //when
		Review review = Review.of(reservation1, 3.5, "리뷰작성 test입니다.");
		reviewRepository.save(review);
	    //then

		Assertions.assertEquals(review.getReservation(), reservation1);
	    
	}

	@DisplayName("반려인의 후기 작성 후 펫시터 평가 반영")
	@Transactional
	@Test
	public void reviewCountTest() throws Exception{
	    //given
		initialize();
		LocalDateTime checkIn = LocalDateTime.of(2023, 6, 13, 5, 13);
		LocalDateTime checkOut = LocalDateTime.of(2023, 6, 14, 6, 13);
		Reservation reservation1 = Reservation.forSimpleTest(checkIn, checkOut, user, petSitter, 35000);
		reservation1.updateForTest("소형견", 10000);
		reservation1.updateStatus(ReservationStatus.DONE.toString());
		reservationRepository.save(reservation1);
	  
	    //when
		Review review = Review.of(reservation1, 3.5, "리뷰작성 test입니다.");
		reviewRepository.save(review);

		PetSitter petsitter = petSitterRepository.findById(reservation1.getPetSitter().getId()).orElseThrow(()->{
			throw new AppException(ErrorCode.PETSITTER_NOT_FOUND, ErrorCode.PETSITTER_NOT_FOUND.getMessage());
		});
		petsitter.updateReview(3.5);

	    //then
		Assertions.assertEquals(petsitter.getReview_count(), 1);
		Assertions.assertEquals(petsitter.getStar_rate(), 3.5);
	}

	@DisplayName("반려인의 후기 작성 service test")
	@Transactional
	@Test
	public void reviewServiceTest() throws Exception{
	    //given
		initialize();
		LocalDateTime checkIn = LocalDateTime.of(2023, 6, 13, 5, 13);
		LocalDateTime checkOut = LocalDateTime.of(2023, 6, 14, 6, 13);
		Reservation reservation1 = Reservation.forSimpleTest(checkIn, checkOut, user, petSitter, 35000);
		reservation1.updateForTest("소형견", 10000);
		reservation1.updateStatus(ReservationStatus.DONE.toString());
		reservationRepository.save(reservation1);
	  
	    //when
		PetSitter newPetSitter = reservationService.postReview(user.getId(), reservation1.getReservationId(), "좋았습니다.", 3.5);
	  
	    //then
		Assertions.assertEquals(newPetSitter.getStar_rate(), 3.5);
	    
	 }
	  
	  
}

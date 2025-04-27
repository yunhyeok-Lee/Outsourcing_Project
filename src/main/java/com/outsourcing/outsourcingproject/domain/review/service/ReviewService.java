package com.outsourcing.outsourcingproject.domain.review.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;
import com.outsourcing.outsourcingproject.domain.review.dto.OwnerReviewRequestDto;
import com.outsourcing.outsourcingproject.domain.review.dto.ReviewRequestDto;
import com.outsourcing.outsourcingproject.domain.review.dto.ReviewUpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.review.dto.StoreReviewResponseDto;
import com.outsourcing.outsourcingproject.domain.review.entity.Review;
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.entity.User;
import com.outsourcing.outsourcingproject.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	/* 리뷰 생성
	1. Order 객체 생성(주문 유효성 검사)  // 입력받은 주문이 DB에 존재하는지 검사하면서 객체 생성 <- 주문 배달 상태와 리뷰 생성 자격 검증 위해 객체 꺼내야 함
	2. 주문의 배달 상태 검증 - 배달 완료 상태인지
	3. 리뷰 유효성 검사 - 해당 주문의 리뷰가 이미 존재하는지 //
	4. 리뷰 생성 자격 검증 <- token 추출 userId vs orderId 추출 userId를 비교 <- order 객체가 있어야 검증할 수 있음
	5. DB에 SAVE
	*/
	@Transactional
	public void createReview(Long orderId, ReviewRequestDto requestDto, String token) {

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		boolean exists = reviewRepository.existsByOrder_Id(orderId);

		if (exists) {
			throw new CustomException(ErrorCode.ALREADY_REVIEW_EXISTS);
		}

		if (!order.getDeliveryStatus().equals(DeliveryStatus.COMPLETED)) {
			throw new CustomException(ErrorCode.NOT_COMPLETED_ORDER);
		}

		User orderUser = order.getUser();
		Long userIdByOrder = orderUser.getId();
		Long userIdByToken = jwtUtil.getUserIdFromToken(token);

		if (!Objects.equals(userIdByOrder, userIdByToken)) {
			throw new CustomException(ErrorCode.NO_REVIEW_CREATE_PERMISSION);
		}

		Review review = Review.builder()
			.rating(requestDto.getRating())
			.title(requestDto.getTitle())
			.content(requestDto.getContent())
			.order(order)
			.user(orderUser)
			.store(order.getStore())
			.build();

		reviewRepository.save(review);

		// 해당 가게 리뷰수 증가 로직
		// review.getStore().getReviewCounts() += 1; <- parent 유무 파악할 이유 없음 <- 애초에 주문에 리뷰가 있는 순간 걸러지는 예외처리 적용했음
		// 이거 review가 아니라 order.getStore로 받아도 될 것 같기는 함. N+1 고려할 필요 X
	}

	/* 가게 리뷰 조회
	1. 가게 유효성 검사 - 존재하는지
	2. 가게 리뷰 유효성 검사 - 존재하는지  <<- review not found 예외 처리가 맞을지 그냥 빈 리스트 조회가 맞을지 고민 <- 빈 리스트 조회 하기로 함
	3. 가게 ID 기반 리뷰 리스트 가져오기
	4. N+1 대응 방식 필요함.
	*/
	public Page<StoreReviewResponseDto> getStoreReviews(Long storeId, Pageable pageable) {

		boolean exists = storeRepository.existsById(storeId);
		if (!exists) {
			throw new CustomException(ErrorCode.STORE_NOT_FOUND);
		}

		// 가져온 가게 아이디에 해당하는 리뷰 page 객체로 받아오기
		Page<Review> reviewList = reviewRepository.findByStoreId(storeId, pageable);

		// 받아온 리뷰들을 DTO 객체에 눌러 담기 (Entity -> Dto 변환 작업)
		Page<StoreReviewResponseDto> storeReviewResponseList = reviewList
			.map(StoreReviewResponseDto::new);

		return storeReviewResponseList;
	}

	/* 리뷰 수정
	1. Transactional 처리
	2. 객체 생성 및 리뷰 유효성 검사
	어차피 불러올 객체라면 미리 boolean으로 확인하지 않기. [ 오히려 메모리 낭비일 수 있음 ]
	해당 예외처리로 걸리는 경우의 수 보다 메서드 성공 경우의 수가 더 많다고 생각하고 설계하는 게 옳음. [ 실제로 그럴 가능성이 훨씬 큼 ]
	3. 리뷰 수정 자격 검증 // Token의 userId와 ReviewId로 추출한 UserId
	3. requestDto 각 필드에 값이 존재하는지 유효성 검사와 값 변경 진행 (PATCH 방식이라 바뀌지 않은 값이 있을것) [ 영속성 컨텍스트 특징이였던 것 같음 ]
	*/
	@Transactional
	public void updateReview(Long id, ReviewUpdateRequestDto requestDto, String token) {

		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

		Long userIdByReview = review.getUser().getId();
		Long userIdByToken = jwtUtil.getUserIdFromToken(token);

		if (!Objects.equals(userIdByReview, userIdByToken)) {
			throw new CustomException(ErrorCode.NO_REVIEW_UPDATE_PERMISSION);
		}

		if (requestDto.getTitle() != null) {
			review.UpdateTitle(requestDto.getTitle());
		}

		if (requestDto.getContent() != null) {
			review.UpdateContent(requestDto.getTitle());
		}

		if (requestDto.getRating() != null) {
			review.UpdateRaing(requestDto.getRating());
		}

	}

	/* 리뷰 삭제 (soft)
	0. Entity에 SQLDelete 어노테이션 설정
	1. 객체 생성
	2. 자격 검증
	3. DELETE 진행 ( SQLDelete 에서 대신 설정해둔 쿼리문이 실행됨 ) -> SOFT DELETE
	4. ReviewCounts 감소 ( 사장님 리뷰는 감소 x )
	*/
	@Transactional
	public void deleteReview(Long id, String token) {

		Review savedReview = reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

		Long userIdByReview = savedReview.getUser().getId();
		Long userIdByToken = jwtUtil.getUserIdFromToken(token);

		if (!Objects.equals(userIdByReview, userIdByToken)) {
			throw new CustomException(ErrorCode.NO_REVIEW_DELETE_PERMISSION);
		}

		reviewRepository.delete(savedReview);

		// 해당 가게 리뷰수 감소 로직 (사장님 리뷰 삭제인지 확인해야 함 <- 사장님 리뷰는 카운트하지 않음)
		if (savedReview.getParent() == null) {
			// savedReview.getStore().getReviewCounts() -= 1;
		}
	}

	/*사장님 리뷰
		1. 리뷰 객체 생성 및 유효성 검증
		2. 사장님 리뷰 존재 여부 검증 + 답글 1개로 제한 <- 편의상 이렇게 구현했지만, 이후 답글 갯수 확장 시, 코드의 방향성이 바뀌기 때문에 확장성 문제 존재.
		3. 사장님 리뷰 생성 자격 검증 <- token 추출 userId vs Review 추출 userId를 비교
		4.
	*/
	@Transactional
	public void createOwnerReview(Long id, OwnerReviewRequestDto requestDto, String token) {

		// review_id로 review 객체 생성
		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

		boolean exists = reviewRepository.existsByParent(review);
		if (exists) {
			throw new CustomException(ErrorCode.ALREADY_RESPONSED_REVIEW);
		}

		// reviewId로 가게 사장 id 추출 + DB에 같이 저장할 객체들 조회

		Store reviewStore = review.getStore(); // 가게 객체 ( DB 저장용 )

		User ownerUser = reviewStore.getUser(); // 사장님 유저 객체 ( DB 저장용 )

		Long ownerIdByReview = ownerUser.getId(); // 가게 사장 ID

		// 가게와 유저 객체는 어차피 DB 저장 과정에서 필요해서 생성될 객체들임
		// 그런데 가게 사장 ID를 조회하려면 해당 객체들을 통하면 N+1문제를 SKIP 가능
		// -> 어차피 밑에서 날릴 쿼리문인데 앞에서 미리 날려서 가게 사장 ID 조회하는 쿼리문을 간단하게 만든 것
		// 하지만 order 객체의 경우, 가게 사장 ID 조회하는 로직에 필요 없음
		// 따라서 이후 진행될 유효성 검증 이후에 실제 DB 저장 과정에서 진행하는 편이 메모리 낭비를 방지할 수 있음.

		// token에서 user_id 추출
		Long userIdByToken = jwtUtil.getUserIdFromToken(token);

		// 사장님 리뷰 생성 자격 검증
		if (!Objects.equals(ownerIdByReview, userIdByToken)) {
			throw new CustomException(ErrorCode.NO_OWNER_REVIEW_PERMISSION);
		}

		// nickname과 title 정보를 여기서 선언하는 이유도 메모리 낭비 방지 목적
		String nickname = review.getUser().getNickname();

		String autoTitle = nickname + "님 리뷰에 대한 사장님 답글입니다.";

		Review ownerReview = Review.builder()
			.title(autoTitle)
			.content(requestDto.getContent())
			.parent(review)
			.order(review.getOrder())
			.store(reviewStore)
			.user(ownerUser)
			.build();

		reviewRepository.save(ownerReview);

		// 해당 가게 리뷰 수 증가 로직 적용 안함 <- 사장님 리뷰니까 (사용자 리뷰만 셀 계획)

	}
}

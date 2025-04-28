package com.outsourcing.outsourcingproject.domain.review.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
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
	private final EntityFetcher entityFetcher;

	/* 리뷰 생성
	1. Order 객체 생성(주문 유효성 검사)  // 입력받은 주문이 DB에 존재하는지 검사하면서 객체 생성 <- 주문 배달 상태와 리뷰 생성 자격 검증 위해 객체 꺼내야 함
	2. 주문의 배달 상태 검증 - 배달 완료 상태인지
	3. 리뷰 유효성 검사 - 해당 주문의 리뷰가 이미 존재하는지 //
	4. 리뷰 생성 자격 검증 <- token 추출 userId vs orderId 추출 userId를 비교 <- order 객체가 있어야 검증할 수 있음
	5. DB에 SAVE
	*/
	@Transactional
	public void createReview(Long orderId, ReviewRequestDto requestDto, String token) {

		boolean exists = reviewRepository.existsByOrder_Id(orderId);

		if (exists) {
			throw new CustomException(ErrorCode.ALREADY_REVIEW_EXISTS);
		}

		Order order = entityFetcher.getOrderOrThrow(orderId);

		if (!order.getDeliveryStatus().equals(DeliveryStatus.COMPLETED)) {
			throw new CustomException(ErrorCode.NOT_COMPLETED_ORDER);
		}

		User user = order.getUser();

		Long userIdByOrder = user.getId();

		Long userIdByToken = jwtUtil.getUserIdFromToken(token);

		if (!Objects.equals(userIdByOrder, userIdByToken)) {
			throw new CustomException(ErrorCode.NO_REVIEW_CREATE_PERMISSION);
		}

		Review review = Review.builder()
			.rating(requestDto.getRating())
			.title(requestDto.getTitle())
			.content(requestDto.getContent())
			.order(order)
			.user(order.getUser())
			.store(order.getStore())
			.build();

		reviewRepository.save(review);

		// 해당 가게 리뷰수 증가 로직
		storeRepository.increaseReviewCounts(
			review.getStore().getId()); //  <- parent 유무 파악할 이유 없음 <- 애초에 주문에 리뷰가 있는 순간 걸러지는 예외처리 적용했음
		// 이거 review가 아니라 order.getStore로 받아도 될 것 같기는 함. N+1 고려할 필요 X
	}


	/*
		TODO :
		refactor :
		- 리뷰 조회 기능 수정하기 (사장닙 답글 딸려오게 수정) << 질문
		&&
		입력받는 Path값이 가게 ID,
		parent라는 자기참조 필드 존재
		기존에는 custom 쿼리 작성으로
		사용자 리뷰 [parent = null인 FindBy가게ID] where parent = null
		사장님 리뷰 [parent != null인 FindBy가게ID] where parent != null
		두 가지 리뷰 리스트를 각각의 DTO로 각각 받아와서 통합 DTO에 조립하는 방식으로 구현하려 했음
		리스트는 reviewId = 1부터 쌓이고 parent id=1 인 사장님 리뷰가 있다면 해당 리뷰와 리스트로 묶이게 구현
		ex) {[(reviewId:1, parent: null), (reviewId : 2, parent :1)], [(reviewId:3, parent : null), (reviewId:5, parent : 3)],[(reviewId:4, parent:null)]...}
		구현하려고 보니 로직이 너무 복잡함, 이걸 또 페이징 처리해야 하는데 어떻게 구현할 지 벽에 가로막힌 느낌.
		결론 : 구현 못하겠음 [ 거의 심화 알고리즘 문제 같음 ]
		&&


		1. FindBy 가게ID로 리뷰 리스트를 받아옴.
		2. 해당 데이터를 가공하는 게 낫다.
		3. 같은 orderId별로 묶어서. 정렬 순은 최신 작성일
		4. FindBy가게IDOrderbyOrderIdANDCREATED_AT [ 가게 ID를 ORDERID로 검색 ]
		일단 ORDERID로 검색하는게 사용자 리뷰와 사장님 답글의 공통분모이기에 모을 수 있고, ORDER 자체가 시간 순으로 작성되기에 최신 순 정렬까지 가능하다.
		5. 각 ORDERID별 주문을 한 배열에 같이 받아오고 그 배열에 대한 리스트를 받아오게 설정한다.
		6. 그 리스트에 대한 페이지를 받으면 구현 완료다.

		- N+1 문제 EntityGraph 방식으로 대응 << EntityFetcher에 작성하면 되는지 혹은 Repository에 작성하면 되는지 튜터님께 질문
		1. 리뷰 수정, 삭제 / 사장님 리뷰에서 기존 리뷰 작성자 nickname 가져오기 위해서도 사용
		@EntityGraph[ attributePaths = user ]
		findByIdForUserValidation

		2. storeReviews 증감을 위해 store 테이블만 조인
		@EntityGraph[ attributePaths = store ]
		findByIdForStoreUpdate

		3. 가게 사장님 객체 가져오기 위해 store, store.user 테이블조인
		@EntityGraph[ attributePaths = store, store.user ]
		findByIdForOwnerValidation

		- Review CREATE UserId 경로 검증(Path , Token) 관련 에러 해결 << 질문

		↑ 오전 전에 끝내기
		------------------------------------------------------------

		TEST CODE 작성
		리뷰 수 동시성 문제 해결 (Lock 기능을 통해) < 고민 중


	*/

	/* 가게 리뷰 조회
	1. 가게 유효성 검사 - 존재하는지
	2. 가게 리뷰 유효성 검사 - 존재하는지  <<- review not found 예외 처리가 맞을지 그냥 빈 리스트 조회가 맞을지 고민 <- 빈 리스트 조회 하기로 함
	3. 가게 ID 기반 리뷰 리스트 가져오기
	4. N+1 대응 방식 필요함.
	*/
	public Page<StoreReviewResponseDto> getStoreReviews(Long storeId, Integer minRating, Integer maxRating,
		Pageable pageable) {

		boolean exists = storeRepository.existsById(storeId);
		if (!exists) {
			throw new CustomException(ErrorCode.STORE_NOT_FOUND);
		}

		List<Long> orderIds = orderRepository.findOrderIdsByStoreId(storeId);

		Page<Review> reviews = reviewRepository.findReviewsByOrderIdsWithRatingFilter(orderIds, minRating, maxRating,
			pageable);

		return reviews.map(StoreReviewResponseDto::new);
	}

	/* 리뷰 수정
	1. Transactional 처리
	2. 객체 생성 및 리뷰 유효성 검사
	어차피 불러올 객체라면 미리 boolean으로 확인하지 않기. [ 오히려 메모리 낭비일 수 있음 ]
	해당 예외처리로 걸리는 경우의 수 보다 메서드 성공 경우의 수가 더 많다고 생각하고 설계하는 게 옳음. [ 실제로 그럴 가능성이 훨씬 큼 ]
	3. 리뷰 수정 자격 검증 // Token의 userId와 ReviewId로 추출한 UserId
	3. requestDto 각 필드에 값이 존재하는지 유효성 검사와 값 변경 진행 (PATCH 방식이라 바뀌지 않은 값이 있을것) [ 영속성 컨텍스트 특징 ]
	*/
	@Transactional
	public void updateReview(Long id, ReviewUpdateRequestDto requestDto, String token) {

		Review review = entityFetcher.getReviewOrThrow(id);

		User user = review.getUser();
		Long userIdByReview = user.getId();
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

		Review savedReview = entityFetcher.getReviewOrThrow(id);

		User user = savedReview.getUser();
		Long userIdByReview = user.getId();
		Long userIdByToken = jwtUtil.getUserIdFromToken(token);

		if (!Objects.equals(userIdByReview, userIdByToken)) {
			throw new CustomException(ErrorCode.NO_REVIEW_DELETE_PERMISSION);
		}

		reviewRepository.delete(savedReview);

		// 해당 가게 리뷰수 감소 로직 (사장님 리뷰 삭제인지 확인해야 함 <- 사장님 리뷰는 카운트하지 않음)
		if (savedReview.getParent() == null) {
			storeRepository.decreaseReviewCounts(
				savedReview.getStore().getId());
		}
	}

	/*사장님 리뷰
		1. 리뷰 객체 생성 및 유효성 검증
		2. 사장님 리뷰 존재 여부 검증 + 답글 1개로 제한 <- 편의상 이렇게 구현했지만, 이후 답글 갯수 확장 시, 코드의 방향성이 바뀌기 때문에 확장성 문제 존재.
		3. 사장님 리뷰 생성 자격 검증 <- token 추출 userId vs Review 추출 userId를 비교
	*/
	@Transactional
	public void createOwnerReview(Long id, OwnerReviewRequestDto requestDto, String token) {

		// review_id로 review 객체 생성
		Review review = entityFetcher.getReviewOrThrow(id);

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

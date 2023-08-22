<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<%
    request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>제품목록출력</title>
  <style>
.room-reservation-card {
  display: flex;
  justify-content:left;
  align-items: right;
  margin-bottom: 20px;
  padding: 10px;
  max-width: 80%; /* 최대 너비 설정 */
  background-color: #f5f5f5;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.room-reservation-left {
  flex: 1;
  padding: 0 10px; /* 이미지와 텍스트 사이 간격 조절 */
  text-align: right; /* 텍스트 가운데 정렬 */
}

.room-reservation-image img {
  max-height: 200px; /* 이미지 높이 조절 */
  width: auto;
}

.room-reservation-right {
  flex: 2;
  padding: 10px;
}

/* 텍스트 스타일 조정 */
.room-reservation-right h3 {
  font-size: 1.5em; /* 제목 폰트 크기 조정 */
  margin: 0; /* 기본 마진 제거 */
  text-align:left;
}

.room-price,
.room-detail {
  margin: 10px 0; /* 텍스트 간격 조정 */
}



    .adc{
      background-color: #ddd;
    }
    .adc h4,p{
      margin : 15px;
      padding: 15px;
    }


      .product-company {
      display: flex;
      flex-direction: column;
      align-items: left;
      gap: 20px;
      padding: 20px;
    }
    
    .product-de {
      display: flex;
      flex-direction: column;
      align-items: left;
      gap: 20px;
      padding: 20px;
    }

    .product-card {
      background-color: #f5f5f5;
      border: 1px solid #ddd;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
      padding: 20px;
      width: 100%;
      box-sizing: border-box;
      transition: transform 0.2s ease-in-out;
      display: flex;
    }

    .product-card .product-details {
      flex: 1;
      text-align: left;
      margin-left: 20px;
    }

    .product-card .product-details h3 {
      text-align: left;
      margin-top: 0;
      font-weight:bold;
      color:black;
      font-size:2em;

    }
    .product-card .product-details p{
      text-align: left;
      margin-top:10px;
    }

      .mainimg{
        width:50%;
        height:400px;
      }
  

      .booking-link {
          text-align: center;
          margin-top: 10px;
      }

      .resButton {
          background-color: #007bff;
          color: white;
          border: none;
          border-radius: 5px;
          padding: 10px 20px;
          font-size: 14px;
          cursor: pointer;
          transition: background-color 0.3s;
          display: inline-block;
          text-decoration: none;
          width: 300px;
      }

      .resButton:hover {
          background-color: #0056b3;
          color: white;
      }

       /* 모달 창 스타일 */
       .modal {
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 1000;
        }
        .modal-content {
            background-color: #fff;
            margin: 15% auto;
            padding: 20px;
            width: 80%;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            text-align: center;
        }
        .miniModal-content {
            background-color: #fff;
            margin: 20% auto;
            padding: 20px;
            width: 30%;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            text-align: center;
        }
        .modal-content img {

            margin-right: 5px;
        }
        .close {
            position: absolute;
            top: 10px;
            right: 20px;
            font-size: 20px;
            cursor: pointer;
        }

        .resmodal{
            display: none;
            position: fixed;
            left: 0%;
            top: 0%;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 1000;
        }
        
        .carousel-wrapper {
  overflow: hidden;
  width: 90%;
  margin: auto;
}

.carousel-wrapper * {
  box-sizing: border-box;
}

.carousel {
  -webkit-transform-style: preserve-3d;
  -moz-transform-style: preserve-3d;
  transform-style: preserve-3d;
}

.carousel_item {
  opacity: 0;
  position: absolute;
  top: 0;
  width: 500px !important;  /* 원하는 너비로 설정 */
  height: 350px !important; /* 원하는 높이로 설정 */
  object-fit: cover !important;
  margin: auto;
  padding: 1rem 4rem;
  z-index: 100;
  transition: transform 0.5s, opacity 0.5s, z-index 0.5s;
}

.carousel_item.active {
  opacity: 1;
  position: relative;
  z-index: 900;
}

.carousel_item.prev,
.carousel_item.next {
  z-index: 800;
}

.carousel_item.prev {
  transform: translateX(-100%);
}

.carousel_item.next {
  transform: translateX(100%);
}

.carousel_button--prev,
.carousel_button--next {
  position: absolute;
  top: 50%;
  width: 3rem;
  height: 3rem;
  background-color: #fff;
  transform: translateY(-50%);
  border-radius: 50%;
  cursor: pointer;
  z-index: 1001;
  border: 1px solid black;
}

.carousel_button--prev {
  left: 0;
}

.carousel_button--next {
  right: 0;
}

.carousel_button--prev::after,
.carousel_button--next::after {
  content: ' ';
  position: absolute;
  width: 10px;
  height: 10px;
  top: 50%;
  left: 54%;
  border-right: 2px solid black;
  border-bottom: 2px solid black;
  transform: translate(-50%, -50%) rotate(135deg);
}

.carousel_button--next::after {
  left: 47%;
  transform: translate(-50%, -50%) rotate(-45deg);
}

<!--탭메뉴 스타일 등장-->
.main {
            min-width: 320px;
            max-width: 800px;
            padding: 50px;
            margin: 0 auto;
            background: #ffffff;}

        section {
            display: none;
            padding: 20px 0 0;
            border-top: 1px solid #ddd;}

        /*라디오버튼 숨김*/
          input {
              display: none;}

        label {
            display: inline-block;
            margin: 0 0 -1px;
            padding: 15px 25px;
            font-weight: 600;
            text-align: left;
            color: #bbb;
            border: 1px solid transparent;}

        label:hover {
            color: #2e9cdf;
            cursor: pointer;}

        /*input 클릭시, label 스타일*/
        input:checked + label {
              color: #555;
              border: 1px solid #ddd;
              border-top: 2px solid #2e9cdf;
              border-bottom: 1px solid #ffffff;}

        #tab1:checked ~ #content1,
        #tab2:checked ~ #content2 {
            display: block;}

.resButton{
  width:100%;
  height:100%;
  margin-top:10%;
}
/* 리뷰 카드 스타일 */
.reviewlist {
    margin: 20px 0;
}

.review-card {
    background-color: #f5f5f5;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    padding: 20px;
    width: 100%;
    box-sizing: border-box;
    transition: transform 0.2s ease-in-out;
    display: flex;
}

.review-details {
    flex: 1;
    text-align: left;
    margin-left: 20px;
}

/* 별점 스타일 */
.rating {
    display: flex;
    align-items: center;
}

.star-rating {
    display: flex;
}

.star-image {
    width: 20px;
    height: 20px;
    fill: yellow;
    margin-right: 3px;
}

/* 리뷰 날짜 스타일 */
.review-date {
    color: #777;
    font-size: 12px;
    margin-top: 10px;
}
            
  </style>

<script>
  $(document).ready(function(){
$(".mainimg").click(function(){
  $("#myModal").show(); // 모달 창 보이기
});
$(".close").click(function(){
  $("#myModal").hide(); // 모달 창 숨기기
});

// 모달 창 외부 클릭 감지
$(document).click(function(event) {
  var target = $(event.target);
  if (!target.closest("#myModal .modal-content").length && !target.closest(".mainimg").length) {
      $("#myModal").hide();
  }
});
});

</script>
<script>
  (function () {
'use strict'

const get = (target) => {
return document.querySelector(target)
}

class Carousel {
constructor(carouselElement) {
this.carouselElement = carouselElement;
this.itemClassName = 'carousel_item';
this.items = this.carouselElement.querySelectorAll('.carousel_item')

this.totalItems = this.items.length
this.current = 0
this.isMoving = false
}

initCarousel() {
this.isMoving = false
this.items[0].classList.add('active')
this.items[1].classList.add('next')
this.items[this.totalItems-1].classList.add('prev')
}

disabledInteraction() {
this.isMoving = true
setTimeout(() => {
  this.isMoving = false
}, 500)
}

setEventListener() {
this.prevButton = this.carouselElement.querySelector('.carousel_button--prev')
this.nextButton = this.carouselElement.querySelector('.carousel_button--next')
this.prevButton.addEventListener('click', () => {
  this.movePrev()
})
this.nextButton.addEventListener('click', () => {
  this.moveNext()
})
}

moveCarouselTo() {
if(this.isMoving) return
this.disabledInteraction()
let prev = this.current - 1
let next = this.current + 1

if(this.current === 0) {
  prev = this.totalItems - 1
} else if(this.current == this.totalItems - 1) {
  next = 0
}

for (let i = 0; i < this.totalItems; i++) {
  if(i === this.current) {
    this.items[i].className = this.itemClassName + ' active'
  } else if ( i === prev ){
    this.items[i].className = this.itemClassName + ' prev'
  } else if ( i === next) {
    this.items[i].className = this.itemClassName + ' next'
  } else {
    this.items[i].className = this.itemClassName
  }
}
}

moveNext() {
if(this.isMoving) return
if (this.current === this.totalItems - 1) {
  this.current = 0
} else {
  this.current++
}
this.moveCarouselTo()
}

movePrev() {
if(this.isMoving) return
if(this.current === 0){
  this.current = this.totalItems - 1
} else{
  this.current--
}
this.moveCarouselTo()
}
}

document.addEventListener('DOMContentLoaded', () => {
const carouselElement = get('.carousel')
const carousel = new Carousel(carouselElement)

carousel.initCarousel()
carousel.setEventListener()
})

})()
</script>

<script>
  $(document).ready(function(){
// 기존의 이미지 클릭 이벤트
$(".mainimg").click(function(){
  $("#myModal").show();
});
$(".close").click(function(){
  $("#myModal").hide();
});

// 예약하기 버튼 클릭 이벤트
$(".resButton").click(function(){
  $("#reservationModal").show();
});
$("#reservationModal .close").click(function(){
  $("#reservationModal").hide();
});

// 모달 창 외부 클릭 감지
$(document).click(function(event) {
  var target = $(event.target);
  if (!target.closest("#myModal .modal-content").length && !target.closest(".mainimg").length) {
      $("#myModal").hide();
  }
  if (!target.closest("#reservationModal .modal-content").length && !target.closest(".resButton").length) {
      $("#reservationModal").hide();
  }
});
});

</script>


</head>


<body>
  <div class="product-company">
    <div class="product-card">
      <img class="mainimg" src="${contextPath}/resources/images/h1.jpg" alt="" />
      <div class="product-details">
        <h3><b class="head" style="color: #f5ba18;">&nbsp;${company.category}</b>&nbsp;${company.company}</h3>
        <hr>        
        <p class="p-1"><b>평점</b>${company.grade} &nbsp; </p>
        <p class="p-2" style="color:rgb(59, 57, 57);">주소 : ${company.location}</p>
        <p class="p-3"><b>객실정보</b> : ${company.detail}</p>

        <div class="adc">
          <h4>관리자 한마디</h4>
          <p>기쁜마음으로 남녀노소 즐길 수 있는 공간입니다. 
            -이창현 관리자 의 추천 -
          </p>
        </div>
        
      </div>
    </div>
  </div>
  

  <!--탭메뉴등장-->
  <div class="main">
    <input id="tab1" type="radio" name="tabs" checked> <!--디폴트 메뉴-->
    <label for="tab1">객실안내 / 예약</label>

    <input id="tab2" type="radio" name="tabs">
    <label for="tab2">리뷰</label>

    <section id="content1">
      <!-- 객실 예약하기 -->
      <c:forEach var="goods" items="${goods}">
        <div class="room-reservation-card"> <!-- 새로운 클래스 추가 -->
          <div class="room-reservation-image">
            <img src="${contextPath}/resources/images/h1.jpg" alt="${goods.room}" />
          </div>
          <div class="room-reservation-details">
            <h3><b>${goods.room}</b></h3>
            <p class="room-price">가격 : ${goods.price}</p>
            <p class="room-detail">객실안내 : ${goods.detail}</p>
            <div class="btn">
              <button class="resButton">예약하기</button>
              <!--<a href="${contextPath}/user/reservation.do?gno=${goods.gno}&amp;u_no=${userVO.u_no}">예약하기</a>-->
            </div>
          </div>
        </div>
      </c:forEach>
    </section>

    <section id="content2">

        <!-- 리뷰 보기-->
      <c:forEach var="review" items="${review}">
        <div class="reviewlist">
          <div class="review-card">
              <div class="review-details">
                  <h3><b>${review.nickname} 님의 리뷰</b></h3>
                  <div class="rating">
                      <p class="p-1">별점 :</p>
                      <div class="star-rating">
                          <c:forEach begin="1" end="${review.grade}">
                              <img src="${contextPath}/resources/images/grade.png" alt="별" class="star-image" />
                          </c:forEach>
                      </div>
                  </div>
                  <p class="p-3">리뷰내용 : ${review.content}</p>
                  <p class="review-date">${review.writedate}</p>
              </div>
          </div>
      </div>
        <!---->
        </c:forEach>
    </section>


    <!--    -->

    


</div>





<!-- 모달 창 -->
<div id="myModal" class="modal">
  <div class="modal-content">
      <div class="carousel-wrapper">
          <div class="carousel">
            <img class="carousel_item" src="${contextPath}/resources/images/h1.jpg"/>
            <img class="carousel_item" src="${contextPath}/resources/images/subimg1.jpg"/>
            <img class="carousel_item" src="${contextPath}/resources/images/subimg2.jpg"/>
            <img class="carousel_item" src="${contextPath}/resources/images/subimg3.jpg"/>
            <img class="carousel_item" src="${contextPath}/resources/images/subimg4.jpg"/>
            
    
            <div class="carousel_button--next"></div>
            <div class="carousel_button--prev"></div>
          </div>
        </div>
  </div>
</div>


</body>
</html>


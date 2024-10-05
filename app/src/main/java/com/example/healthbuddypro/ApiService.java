package com.example.healthbuddypro;

import com.example.healthbuddypro.Login.LoginRequest;
import com.example.healthbuddypro.Login.LoginResponse;
import com.example.healthbuddypro.Matching.MatchRequest;
import com.example.healthbuddypro.Matching.ProfileDetailResponse;
import com.example.healthbuddypro.Matching.ProfileResponse;
import com.example.healthbuddypro.Matching.LikeResponse;
import com.example.healthbuddypro.Matching.MatchRequestListResponse;
import com.example.healthbuddypro.Matching.MatchRequestResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // 회원가입 API
    @POST("/api/join")
    Call<com.example.healthbuddypro.Signup.SignUpResponse> signUp(@Body com.example.healthbuddypro.Signup.SignUpRequest signUpRequest);

    // 로그인 API
    @POST("/api/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    // 매칭 프로필 목록 조회
    @GET("/api/match/profile")
    Call<ProfileResponse> getProfiles();

    // 매칭 프로필 상세 조회
    @GET("/api/match/profile/{profileId}")
    Call<ProfileDetailResponse> getProfileDetail(@Path("profileId") int profileId);

    // 매칭 프로필 좋아요
    @POST("/api/match/profile/{profileId}/likes")
    Call<LikeResponse> likeProfile(@Path("profileId") int profileId);

    // 매칭 신청 보내기
    @POST("/api/match/request")
    Call<MatchRequestResponse> sendMatchRequest(@Body MatchRequest request);

    // 매칭 신청 내역 조회
    @GET("/api/match/request/user/{userId}")
    Call<MatchRequestListResponse> getMatchRequests(@Path("userId") int userId);

}

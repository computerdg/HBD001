package com.example.healthbuddypro.Matching;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.healthbuddypro.ApiService;
import com.example.healthbuddypro.ProfileDetailActivity;
import com.example.healthbuddypro.R;
import com.example.healthbuddypro.ShortTermMatching.ShortTermMatchFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MatchFragment extends Fragment {

    private ViewPager2 viewPager;
    private ProfilePagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        viewPager = view.findViewById(R.id.viewPager);

        Button btn1To1Matching = view.findViewById(R.id.btn_1to1_matching);
        Button btnShortTermMatching = view.findViewById(R.id.btn_short_term_matching);

        btn1To1Matching.setSelected(true);

        btn1To1Matching.setOnClickListener(v -> {
            btn1To1Matching.setSelected(true);
            btnShortTermMatching.setSelected(false);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new MatchFragment());
            transaction.commit();
        });

        btnShortTermMatching.setOnClickListener(v -> {
            btn1To1Matching.setSelected(false);
            btnShortTermMatching.setSelected(true);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ShortTermMatchFragment());
            transaction.commit();
        });

        fetchProfilesFromBackend();

        return view;
    }

    private void fetchProfilesFromBackend() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://165.229.89.154:8080/") // 실제 서버 URL 설정
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ProfileResponse> call = apiService.getProfiles();
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UserProfile> profiles = response.body().getData();
                    setupViewPager(profiles);
                } else {
                    Toast.makeText(getContext(), "매칭 프로필을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewPager(List<UserProfile> profiles) {
        adapter = new ProfilePagerAdapter(profiles, profile -> {
            Intent intent = new Intent(getContext(), ProfileDetailActivity.class);
            intent.putExtra("nickname", profile.getNickName());
            intent.putExtra("gender", profile.getGender());
            intent.putExtra("age", profile.getAge());
            intent.putExtra("image", profile.getImage());
            startActivity(intent);
        });
        viewPager.setAdapter(adapter);
    }
}

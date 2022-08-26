package com.example.carpoolapp.ui.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentSignupBinding;
import com.example.carpoolapp.model.CommonResponse;
import com.example.carpoolapp.model.SignupRequest;
import com.example.carpoolapp.model.SignupResponse;
import com.example.carpoolapp.server.Retrofit_client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupFragment extends Fragment {
    private FragmentSignupBinding binding;
    private String userId;
    private String userPw;
    private String userCarInfo;
    private String userCarNo;
    private Call<SignupResponse> call;
    private Call<CommonResponse> call_idCheck;
    private SignupResponse signupResponse;
    private boolean flag=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(getLayoutInflater());

        //더 간단한 방법이 있긴한데 엔터를 안치고 로그인버튼 누르면 값 저장이 안됨.
        binding.eTSignupId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userId = editable.toString();
                flag = false;
            }
        });
        binding.eTSignupPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userPw = editable.toString();
            }
        });
        binding.eTSignupCarNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userCarNo = editable.toString();
            }
        });
        binding.eTSignupCarInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userCarInfo = editable.toString();
            }
        });

        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag){
                    SignupRequest signupRequest = new SignupRequest(userId, userPw, userCarInfo, userCarNo);
                    call = Retrofit_client.getApiService().signup(signupRequest);
                    call.enqueue(new Callback<SignupResponse>() {
                        @Override
                        public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                            Toast.makeText(getActivity().getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                            //데이터를 넘겨줘야할 때 사용하기
                            LoginFragment fragment = new LoginFragment();
                            Bundle bundle = new Bundle();
                            fragment.setArguments(bundle);
                            //프래그먼트에서 프래그먼트로 이동
                            requireActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null) //뒤로가기 가능하게 만듬
                                    .setCustomAnimations(R.anim.slide_page_in, R.anim.slide_page_out)
                                    .replace(R.id.ConstraintFragment, fragment).commit();
                        }

                        @Override
                        public void onFailure(Call<SignupResponse> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    showDialog("아이디 중복확인 하세요.");
                }
            }
        });


        binding.idCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId != null) {
                    call_idCheck = Retrofit_client.getApiService().idCheck(userId);
                    call_idCheck.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            if (response.code()==200) {
                                showDialog("사용 가능한 아이디입니다.");
                                flag=true;
                            } else if(response.code()==400){
                                showDialog("이미 있는 아이디 입니다.");
                            }else {
                                showDialog("사용 불가능한 아이디 입니다.");
                            }
                        }
                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {
                            showDialog("통신 실패");
                        }
                    });
                }else {
                    showDialog("아이디를 입력하세요.");
                }
            }
        });


        return binding.getRoot();
    }

    void showDialog(String text) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(text)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}

import { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from "axios";
import axiosService from "./axiosInstance";

export const onRequest = (config: InternalAxiosRequestConfig) => {
  const token = localStorage.getItem("access_token");

  if (token) {
    config.headers["authorization"] = `Bearer ${token}`;
  }
  return config;
};

export const onErrorRequest = (error: Error) => {
  return Promise.reject(error);
};

export const onResponse = (response: AxiosResponse) => response;

export const onErrorResponse = async (error: AxiosError) => {
  const axiosInstance = axiosService.getAxiosInstance(); // Axios 인스턴스 가져옴

  // 401 Unauthorized 에러가 발생했는지 확인
  if (error.response?.status === 401 && error.config) {
    try {
      // refreshToken을 사용하여 accessToken을 재발급 요청
      const response = await axiosInstance.post(
        "/authentication",
        {}, // 빈 본문
        {
          withCredentials: true, // 쿠키를 포함하여 요청
          headers: {
            // Authorization 헤더 제거 또는 설정하지 않음
          },
        }
      );
      console.log("비상비상", response);

      // 새로운 accessToken을 로컬스토리지에 저장
      const authHeader = response.headers["authorization"];
      const newAccessToken = authHeader ? authHeader.split(" ")[1] : undefined; // 'Bearer '를 분리
      console.log("2새로운 accessToken:", newAccessToken);
      if (newAccessToken) {
        console.log("새로운 accessToken:", newAccessToken);
        localStorage.setItem("access_token", newAccessToken);

        // 원래의 요청을 새로운 accessToken으로 재전송
        error.config.headers["authorization"] = `Bearer ${newAccessToken}`;
        return axiosInstance(error.config); // 원래의 요청을 재전송
      } else {
        console.error("새로운 accessToken을 가져오지 못했습니다.");
      }
    } catch (refreshError) {
      // 갱신 중 오류 발생 시 로그인 페이지로 리다이렉트
      console.error("refreshToken 재발급 중 오류 발생:", refreshError);
      window.location.href = "/login";
    }
  }

  return Promise.reject(error);
};

const axiosInterceptor = {
  onRequest,
  onErrorRequest,
  onResponse,
  onErrorResponse,
};

export default axiosInterceptor;
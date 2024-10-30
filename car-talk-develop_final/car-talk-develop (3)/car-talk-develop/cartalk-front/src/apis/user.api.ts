import axios, { AxiosRequestConfig } from "axios";
import { get, post, del, patch } from "../utils/serverHelper";

export const signUp = async (
  loginId: string,
  password: string,
  passwordCheck: string,
  name: string,
  gender: boolean,
  birth: string,
  email: string
): Promise<any> => {
  let url = `/join`;
  const body = {
    loginId,
    password,
    passwordCheck,
    name,
    gender,
    birth,
    email,
  };

  return await post(url, body);
};

export const login = async (
  loginId: string,
  password: string
): Promise<any> => {
  let url = `/login`;
  const body = {
    loginId,
    password,
  };

  return await post(url, body);
};

export const emailVerification = async (id: string): Promise<any> => {
  let url = `/email-verifications/${id}`;

  return await post(url);
};

export const emailVerificationConfirm = async (
  id: string,
  verificationCode: string
): Promise<any> => {
  let url = `/email-verifications/${id}/confirm`;
  const body = {
    verificationCode,
  };

  return await post(url, body);
};

export const logout = async (): Promise<any> => {
  let url = `/logout`;

  return await post(url);
};

export const resetPassword = async (
  loginId: string,
  email: string
): Promise<any> => {
  let url = `/help/password`;
  const body = {
    loginId,
    email,
  };

  return await post(url, body);
};

export const getMyInfo = async (): Promise<any> => {
  let url = `/my-info`;

  const accessToken = localStorage.getItem("access_token");
  const config: AxiosRequestConfig = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  };

  return await get(url, config);
};

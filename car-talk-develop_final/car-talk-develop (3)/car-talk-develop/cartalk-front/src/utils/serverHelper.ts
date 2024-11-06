import { AxiosRequestConfig } from "axios";
import axiosService from "./axiosInstance";

function get<ResponseData>(url: string, config?: AxiosRequestConfig) {
  return axiosService.getAxiosInstance().get<ResponseData>(url, config);
}

function post<RequestData, ResponseData>(
  url: string,
  data?: RequestData,
  config?: AxiosRequestConfig
) {
  return axiosService.getAxiosInstance().post<ResponseData>(url, data, config);
}

function del<ResponseData>(url: string, config?: AxiosRequestConfig) {
  return axiosService.getAxiosInstance().delete<ResponseData>(url, config);
}

function patch<RequestData, ResponseData>(
  url: string,
  data?: RequestData,
  config?: AxiosRequestConfig
) {
  return axiosService.getAxiosInstance().patch<ResponseData>(url, data, config);
}

function put<RequestData, ResponseData>(
  url: string,
  data?: RequestData,
  config?: AxiosRequestConfig
) {
  return axiosService.getAxiosInstance().put<ResponseData>(url, data, config);
}

export { get, post, del, patch, put };

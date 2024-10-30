import axios, { AxiosInstance } from "axios";
import {
  onRequest,
  onErrorRequest,
  onResponse,
  onErrorResponse,
} from "./axiosInterceptor";

class AxiosService {
  private static instance: AxiosService;
  private axiosInstance: AxiosInstance;

  private constructor() {
    this.axiosInstance = axios.create({
      baseURL: process.env.REACT_APP_SERVER_URL,
      // withCredentials: process.env.REACT_APP_MODE === "dev" ? true : false,
      withCredentials: true,
    });

    this.axiosInstance.interceptors.request.use(onRequest, onErrorRequest);
    this.axiosInstance.interceptors.response.use(onResponse, onErrorResponse);
  }

  public static getInstance(): AxiosService {
    if (!AxiosService.instance) {
      AxiosService.instance = new AxiosService();
    }

    return AxiosService.instance;
  }

  getAxiosInstance(): AxiosInstance {
    return this.axiosInstance;
  }
}

export default AxiosService.getInstance();
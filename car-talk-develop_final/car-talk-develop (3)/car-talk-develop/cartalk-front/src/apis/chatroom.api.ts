import axios from "axios";
import { get, post, del, patch, put } from "../utils/serverHelper";
import { CreateChatroomDto, ChatroomDto, Chatrooms } from "DTOs/chatroom.dto";
import { ApiResponse } from "DTOs/apiResponse.dto";
import { AxiosResponse } from "axios";

// 날짜 변환
const formatDate = (dateString: string): string => {
  const parts = dateString.split(" ");
  const day = parts[0]; // '10.16'
  const period = parts[1]; // '오후'
  const times = parts[2]; // 10:13

  const currentYear = new Date().getFullYear();

  let time = times.split(":"); // '10'을 정수로 변환

  let hour = parseInt(time[0]);
  if (period == "오후" && hour !== 12) {
    hour += 12; // 오후 12시가 아니라면 12를 더함
  } else if (period == "오전" && hour === 12) {
    hour = 0; // 오전 12시는 0시로 변환
  }
  let minutes = parseInt(time[1]);

  // 새로운 Date 객체 생성
  const date = day.split(".");

  // yyyy-mm-dd 형식으로 변환
  const yyyy = currentYear;
  const mm = parseInt(date[0]); // 월은 0부터 시작하므로 +1
  const dd = parseInt(date[1]);

  return `${yyyy}-${mm}-${dd} ${hour}:${minutes}`;
};

// 채팅방 생성
export const postChatroom = async (): Promise<
  ApiResponse<CreateChatroomDto>
> => {
  const response: AxiosResponse<ApiResponse<CreateChatroomDto>> = await post<
    undefined,
    ApiResponse<CreateChatroomDto>
  >("/chat/chatroom");
  return response.data;
};

// 채팅방 내역 조회
export const search = async (): Promise<ApiResponse<Chatrooms>> => {
  const response: AxiosResponse<ApiResponse<Chatrooms>> = await get<
    ApiResponse<Chatrooms>
  >("/chat/chatroom/search");

  return response.data;
};

// 채팅방 삭제
export const deleteChatroom = async (id: number): Promise<any> => {
  const response: AxiosResponse<ApiResponse<null>> = await del<
    ApiResponse<null>
  >(`/chat/chatroom/${id}`);
  return response.data;
};

// 채팅 내역
export const chatHistory = async (id: string): Promise<any> => {
  const response: AxiosResponse<ApiResponse<any>> = await get(
    `/chat/chatroom/${id}/messages`
  );
  console.log(response.data);
  return response.data;
};

// 질문하기
export const question = async (id: string, question: string): Promise<any> => {
  let url = `/chat/${id}/question`;
  const body = { question };
  const response = await post(url, body);
  return response.data;
};

export const putChatroom = async (
  title: string,
  chatroomID: number
): Promise<any> => {
  let url = `/chat/chatroom/${chatroomID}`;

  const body = { title };

  return await put(url, body);
};

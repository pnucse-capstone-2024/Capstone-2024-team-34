export interface CreateChatroomDto {
  chatroomId: number;
}

export interface ChatroomDto {
  id: number;
  title: string;
  createdAt: string;
  updatedAt: string;
}

export interface Chatrooms {
  chatrooms: ChatroomDto[];
}

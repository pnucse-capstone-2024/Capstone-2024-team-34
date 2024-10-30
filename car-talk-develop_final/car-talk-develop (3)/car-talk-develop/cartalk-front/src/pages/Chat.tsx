import { ReactComponent as SendIcon } from 'assets/images/send.icon.svg';
import { ReactComponent as ClipIcon } from 'assets/images/clip.icon.svg';
import { ReactComponent as CarIcon } from 'assets/images/car.icon.svg';
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { chatHistory, question } from 'apis/chatroom.api';

export default function ChatPage() {
    const { id } = useParams<{ id: string }>();
    const [messages, setMessages] = useState<{ id: number; content: string; isFromChatbot: boolean, createAt: string }[]>([]);
    const [displayedMessage, setDisplayedMessage] = useState<string>('');
    const [inputValue, setInputValue] = useState<string>('');
    const [isTyping, setIsTyping] = useState<boolean>(false);
    const [isWaitingForReply, setIsWaitingForReply] = useState<boolean>(false);
    const [currentCursor, setCurrentCursor] = useState(0);

    useEffect(() => {
        if (isTyping) {
            const lastMessageIndex = messages.length - 1;
            const lastMessageContent = messages[lastMessageIndex].content;
            let currentDisplayedMessage = ''; // 로컬 변수로 초기화

            const typingInterval = setInterval(() => {
                if (currentDisplayedMessage.length < lastMessageContent.length) {
                    currentDisplayedMessage += lastMessageContent[currentDisplayedMessage.length];
                    setDisplayedMessage(currentDisplayedMessage); // 로컬 변수 값을 사용해 업데이트
                } else {
                    clearInterval(typingInterval);
                    setIsTyping(false);
                }
            }, 100);

            return () => clearInterval(typingInterval);
        }
    }, [isTyping, messages]);

    const handleSendMessage = async () => {
        if (inputValue.trim() !== '') {
            const newMessage = { content: inputValue, isFromChatbot: false, id: currentCursor + 1, createAt: new Date().toISOString() };
            setMessages((prevMessages) => [...prevMessages, newMessage]);
            setInputValue('');
            setIsWaitingForReply(true); // 답장을 기다리는 상태로 설정

            if (id) {
                const response = await question(id, inputValue);
                receiveMessageFromServer(response.response);
            }
        }
    };
    const fetchMessage = async (id: string) => {
        try {
            const response = await chatHistory(id);
            const messages = response.response.body.messages.map((msg: any) => ({
                ...msg,
                createAt: msg.createAt ? new Date(msg.createAt).toISOString() : new Date().toISOString(),
            }));
            setMessages(messages);
            if (messages.length > 0) {
                const lastMessage = messages.reduce((max: any, msg: any) => (msg.id > max.id ? msg : max));
                setCurrentCursor(lastMessage.id);
            }
        } catch (error) {
            console.error("Error fetching messages:", error);
        }
    };

    useEffect(() => {
        if (id) {
            fetchMessage(id);
        }
    }, [id]);

    const receiveMessageFromServer = (serverMessage: any) => {
        setIsWaitingForReply(false); // 답장이 왔으므로 기다리는 상태 해제
        setMessages((prevMessages) => [
            ...prevMessages,
            { content: serverMessage.answer, isFromChatbot: true, id: serverMessage.currentCursor, createAt: new Date().toISOString() }
        ]);
        setDisplayedMessage('');
        setIsTyping(true);
        setCurrentCursor(serverMessage.currentCursor);
    };

    const formatTimestamp = (date: Date) => {
        const hours = date.getHours();
        const minutes = date.getMinutes().toString().padStart(2, '0');
        return `${date.getMonth() + 1}월 ${date.getDate()}일, ${hours}시 ${minutes}분`;
    };

    return (
        <div className="flex flex-col items-center justify-center h-full max-h-screen p-4">
            {/* <div className="flex flex-col w-full max-w-[695px] h-full overflow-y-auto mb-4"> */}
            <div className="flex w-full max-w-[695px]  h-full overflow-y-auto mb-4" style={{ flexDirection: 'column-reverse' }}>
                <div className="flex flex-col gap-2 p-4">
                    {messages.map((msg, index) => (
                        <div key={index} className={`flex flex-col ${!msg.isFromChatbot ? 'items-end' : 'items-start'}`}>
                            <div className={`text-xs text-[#858585] ${!msg.isFromChatbot ? 'text-right' : 'text-left'}`}>
                                {msg.createAt && formatTimestamp(new Date(msg.createAt))}
                            </div>
                            <div className={`flex text-sm p-2 rounded-md max-w-[75%] ${!msg.isFromChatbot ? 'bg-gray-100 text-right' : 'text-left'}`}>
                                <div className='flex justify-start flex-col'>
                                    {msg.isFromChatbot && <CarIcon className="mr-2 w-[1.875rem] h-[1.875rem]" />}
                                </div>
                                <span>
                                    {!msg.isFromChatbot
                                        ? msg.content
                                        : (isTyping && index === messages.length - 1
                                            ? displayedMessage
                                            : msg.content)}
                                </span>
                            </div>
                        </div>
                    ))}
                    {isWaitingForReply && (
                        <div className="flex items-start text-sm p-2 rounded-md max-w-[75%] text-left">
                            <CarIcon className="mr-2" />
                            <span>작성중...</span>
                        </div>
                    )}
                </div>
            </div>
            <div className="flex items-center w-full max-w-[695px] border-2 border-primary-default rounded-full px-3 mb-4">
                <ClipIcon className='m-1' />
                <input
                    className="w-full p-3 focus:outline-none"
                    type="text"
                    placeholder="채팅을 시작해 보세요!"
                    value={inputValue}
                    onChange={(e) => setInputValue(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && handleSendMessage()}
                />
                <SendIcon className='m-1 cursor-pointer' onClick={handleSendMessage} />
            </div>
            <div className="text-xs text-[#9A9B9F] py-3 text-center">
                Used Car Recommendation Chatbot Based on Natural Language Processing
            </div>
        </div>
    );
}

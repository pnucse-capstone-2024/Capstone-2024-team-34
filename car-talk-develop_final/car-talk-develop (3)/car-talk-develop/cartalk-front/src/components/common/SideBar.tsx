import { ReactComponent as NewChatIcon } from 'assets/images/new-chat.icon.svg';
import { ReactComponent as SideHandlerIcon } from 'assets/images/side-handler.icon.svg';
import { ReactComponent as LogoutIcon } from 'assets/images/logout.icon.svg';
import { ReactComponent as EnlargeIcon } from 'assets/images/enlarge.icon.svg';
import { ReactComponent as PenIcon } from 'assets/images/pen.icon.svg';
import { ReactComponent as DeleteIcon } from 'assets/images/delete.icon.svg';

import { ChatDTO } from 'DTOs/chat.dto';
import { useNavigate } from 'react-router-dom';

import { postChatroom, search, deleteChatroom, putChatroom } from 'apis/chatroom.api';
import { CreateChatroomDto, ChatroomDto, Chatrooms } from 'DTOs/chatroom.dto';
import { useEffect, useState } from "react";
import { logout } from 'apis/user.api';


interface SideBarItemProps {
    chat: ChatroomDto;
    getChatrooms: () => Promise<void>;
}

const SideBarItem = ({ chat, getChatrooms }: SideBarItemProps) => {

    const navigate = useNavigate();
    const [isEditing, setIsEditing] = useState(false);
    const [newTitle, setNewTitle] = useState(chat.title);

    const itemClickHandler = () => {
        navigate(`/chat/${chat.id}`);
    }

    const itemDeleteHandler = async () => {
        try {
            const response = await deleteChatroom(chat.id);
            if (response.status === 200) {
                alert(chat.id + '번 방이 삭제되었습니다.');
                await getChatrooms();
                navigate(`/chat`);
            }
        } catch (error) {
            console.error("Error delete chatroom:", error);
        }
    }

    const itemChangeHandler = async () => {
        try {
            const response = await putChatroom(newTitle, chat.id);
            if (response.status === 200) {
                alert(chat.id + '번 방이 변경되었습니다.');
                await getChatrooms();
                setIsEditing(false); // 편집 모드 종료
            }
        } catch (error) {
            console.error("Error change chatroom:", error);
        }
    }

    const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNewTitle(event.target.value);
    };

    return (
        <div
            className="flex justify-between w-full cursor-pointer rounded-lg items-center border-2 border-primary-default bg-white hover:bg-slate-100"
            onClick={itemClickHandler}
        >
            {isEditing ? (
                <input
                    type="text"
                    value={newTitle}
                    onChange={handleTitleChange}
                    onClick={(e) => e.stopPropagation()} // 클릭 이벤트 전파 막기
                    className="font-normal text-xs px-2 py-3 w-full border-2 border-gray-300 rounded-md focus:outline-none focus:border-primary-default"
                />
            ) : (
                <div className="font-normal text-xs px-2 py-3">{chat.title}</div>
            )}
            <div className='flex gap-2 px-2'>
                {isEditing ? (
                    <button
                        onClick={(e) => {
                            e.stopPropagation();
                            itemChangeHandler(); // 변경 사항 저장
                        }}
                        className="text-xs text-blue-500 hover:underline"
                    >
                        저장
                    </button>
                ) : (
                    <PenIcon
                        onClick={(e) => {
                            e.stopPropagation();
                            setIsEditing(true); // 편집 모드로 전환
                        }}
                    />
                )}
                <DeleteIcon onClick={(event) => {
                    event.stopPropagation(); // 이벤트 전파 막기
                    itemDeleteHandler(); // 삭제 핸들러 호출
                }}></DeleteIcon>
            </div>
        </div>
    );
}

const SideBar = () => {

    const navigate = useNavigate();
    const [chatrooms, setChatrooms] = useState<Chatrooms | null>(null);
    const [todayItems, setTodayItems] = useState<ChatroomDto[]>([]);
    const [last3DaysItems, setLast3DaysItems] = useState<ChatroomDto[]>([]);
    const [last7DaysItems, setLast7DaysItems] = useState<ChatroomDto[]>([]);
    const [last30DaysItems, setLast30DaysItems] = useState<ChatroomDto[]>([]);
    const [isSidebarOpen, setIsSidebarOpen] = useState<boolean>(true); // 사이드바 열림 상태 관리


    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    // 대화내역 전부 가져오기
    const getChatrooms = async () => {
        try {
            const result = await search();
            setChatrooms(result.response);
        } catch (error) {
            console.error("Error fetching chatrooms:", error);
        }
    }

    // 날짜 계산
    const getDateDifferenceInDays = (date: string) => {
        const today = new Date();
        const targetDate = new Date(date);
        const timeDifference = today.getTime() - targetDate.getTime();
        return Math.floor(timeDifference / (1000 * 3600 * 24));
    };

    // 새로운 채팅방
    const handleNewChatroomClick = async () => {
        if (localStorage.getItem('access_token') === null) {
            alert('로그인을 진행해주세요.');
            navigate('/login');
        }
        const response = await postChatroom();
        console.log(response.response);
        navigate(`/chat/${response.response?.chatroomId}`);
        getChatrooms();
    }

    // 홈버튼
    const handleGoHomeClick = async () => {
        if (localStorage.getItem('access_token') === null) {
            navigate('/');
        }
        else {
            navigate('/chat')
        }

    }

    // 로그아웃
    const handleLogoutClick = async () => {
        try {
            const response = await logout();
            if (response.status === 200) {
                localStorage.removeItem('access_token');
                localStorage.removeItem('logged_email');
                setChatrooms(null);
                navigate('/');
            }
        } catch (error) {
            console.error(error);
        }

    }

    useEffect(() => {
        if (localStorage.getItem('access_token') === null) {
            return;
        }
        if (window.location.pathname === '/chat' || window.location.pathname.startsWith('/chat')) {
            getChatrooms();
        }
    }, []);

    useEffect(() => {
        if (chatrooms) {
            setTodayItems(chatrooms.chatrooms.filter(chat => getDateDifferenceInDays(chat.updatedAt) === 0));
            setLast3DaysItems(chatrooms.chatrooms.filter(chat => getDateDifferenceInDays(chat.updatedAt) <= 3 && getDateDifferenceInDays(chat.updatedAt) > 0));
            setLast7DaysItems(chatrooms.chatrooms.filter(chat => getDateDifferenceInDays(chat.updatedAt) <= 7 && getDateDifferenceInDays(chat.updatedAt) > 3));
            setLast30DaysItems(chatrooms.chatrooms.filter(chat => getDateDifferenceInDays(chat.updatedAt) <= 30 && getDateDifferenceInDays(chat.updatedAt) > 7));
        }
        else {
            setTodayItems([]);
            setLast3DaysItems([]);
            setLast7DaysItems([]);
            setLast30DaysItems([]);
        }
    }, [chatrooms]);

    return (
        <div className="relative h-screen">
            <div
                className={`bg-[#B2BBC6] bg-opacity-25 h-full transition-all duration-300 ease-in-out ${isSidebarOpen ? 'w-64' : 'w-0'
                    } overflow-hidden`}
                style={{ position: 'absolute', left: 0, top: 0, zIndex: 10 }}
            >
                <div className="flex flex-col items-center justify-between h-full">
                    <div className={`w-full flex ${isSidebarOpen ? 'justify-between' : 'justify-center'} px-2 py-[10px]`}>
                        <SideHandlerIcon className='m-1 cursor-pointer' onClick={toggleSidebar} />
                        {isSidebarOpen && (
                            <div className="text-2xl font-semibold cursor-pointer" onClick={handleGoHomeClick}>CarTalk</div>
                        )}
                        {isSidebarOpen && (
                            <NewChatIcon onClick={handleNewChatroomClick} className='m-[2px] cursor-pointer' />
                        )}
                    </div>

                    {/* 지난 채팅 렌더링 */}
                    {isSidebarOpen && (
                        <div
                            style={{ overflow: 'auto' }}
                            className={`flex flex-col items-start px-4 py-2 w-full h-full gap-4 transition-opacity duration-500 ease-in-out ${isSidebarOpen ? 'opacity-100' : 'opacity-0'}`}
                        >
                            {todayItems.length > 0 && (
                                <div className='flex flex-col w-full gap-2'>
                                    <div className="font-normal text-sm">오늘</div>
                                    {todayItems.map(chat => (
                                        <SideBarItem key={chat.id} chat={chat} getChatrooms={getChatrooms} />
                                    ))}
                                </div>
                            )}

                            {last3DaysItems.length > 0 && (
                                <div className='flex flex-col w-full gap-2'>
                                    <div className="font-normal text-sm">지난 3일</div>
                                    {last3DaysItems.map(chat => (
                                        <SideBarItem key={chat.id} chat={chat} getChatrooms={getChatrooms} />
                                    ))}
                                </div>
                            )}

                            {last7DaysItems.length > 0 && (
                                <div className='flex flex-col w-full gap-2'>
                                    <div className="font-normal text-sm">지난 7일</div>
                                    {last7DaysItems.map(chat => (
                                        <SideBarItem key={chat.id} chat={chat} getChatrooms={getChatrooms} />
                                    ))}
                                </div>
                            )}

                            {last30DaysItems.length > 0 && (
                                <div className='flex flex-col w-full gap-2'>
                                    <div className="font-normal text-sm">지난 30일</div>
                                    {last30DaysItems.map(chat => (
                                        <SideBarItem key={chat.id} chat={chat} getChatrooms={getChatrooms} />
                                    ))}
                                </div>
                            )}
                        </div>
                    )}

                    {isSidebarOpen && (
                        <div
                            className={`flex flex-col w-full gap-4 px-6 py-6 font-medium text-sm text-[#171717] border-t border-[#444654] transition-opacity duration-300 ease-in-out ${isSidebarOpen ? 'opacity-100' : 'opacity-0'}`}
                        >
                            <div className='flex gap-3 items-center'>
                                <EnlargeIcon />
                                {localStorage.getItem('logged_email')}
                            </div>
                            <div
                                className='flex gap-3 items-center cursor-pointer'
                                onClick={handleLogoutClick}
                            >
                                <LogoutIcon />
                                로그아웃
                            </div>
                        </div>
                    )}
                </div>

            </div>
            {/* 오버레이 영역 */}
            {!isSidebarOpen && (
                <div
                    className={`h-full bg-gray-800 transition-all duration-300 ease-in-out w-0`}
                    style={{ position: 'absolute', left: 8, top: 10, zIndex: 5 }}
                    onClick={toggleSidebar}
                >
                    <SideHandlerIcon className='m-1 cursor-pointer' onClick={toggleSidebar} />
                </div>
            )}
        </div>
    )
}

export default SideBar;
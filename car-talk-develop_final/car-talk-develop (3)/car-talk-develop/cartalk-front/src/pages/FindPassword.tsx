import { useState } from 'react';
import { resetPassword } from 'apis/user.api';
import { useNavigate } from 'react-router-dom';

export default function Page() {
    const navigate = useNavigate();

    const [isReset, setIsReset] = useState(false);
    const [loginId, setLoginId] = useState('');
    const [email, setEmail] = useState('');

    const handleResetClick = async () => {
        try {
            const response = await resetPassword(
                loginId,
                email
            );
            console.log(response);
            if (response.status === 200) {
                setIsReset(true);
            }
        } catch (error) {
            console.error(error);
        }
    }

    const handleContinueClick = () => {
        navigate('/login');
    }

    return (
        <div className="flex items-center justify-center h-full">
            {!isReset ? (
                <div className="flex flex-col w-[20rem] gap-5 text-center">
                    <div className="text-3xl font-bold text-[#2E3339]">비밀번호를 잊으셨나요?</div>
                    <div className="relative">
                        <input
                            type="text"
                            id="loginId"
                            value={loginId}
                            onChange={(e) => setLoginId(e.target.value)}
                            className="block px-2.5 pb-2.5 pt-4 w-full text-sm text-primary-dark bg-transparent rounded-lg border border-primary-default appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                            placeholder=" " />
                        <label
                            htmlFor="loginId"
                            className="absolute text-sm text-primary-dark duration-300 transform -translate-y-4 scale-75 top-2 z-10 origin-[0] bg-white px-2 peer-focus:px-2 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto start-1">아이디</label>
                    </div>
                    <div className="relative">
                        <input
                            type="text"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="block px-2.5 pb-2.5 pt-4 w-full text-sm text-primary-dark bg-transparent rounded-lg border border-primary-default appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                            placeholder=" " />
                        <label
                            htmlFor="email"
                            className="absolute text-sm text-primary-dark duration-300 transform -translate-y-4 scale-75 top-2 z-10 origin-[0] bg-white px-2 peer-focus:px-2 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto start-1">이메일</label>
                    </div>
                    <div
                        className="flex items-center justify-center h-[50px] text-sm cursor-pointer text-white font-bold bg-primary-default rounded-[0.25rem]"
                        onClick={handleResetClick}
                    >
                        계속하기
                    </div>
                </div>
            ) : (
                <div className="flex flex-col items-center w-[40rem] gap-20 text-center">
                    <div className="text-3xl font-bold text-[#2E3339]">이메일로 임시 비밀번호를 전송했습니다</div>
                    <div className="text-xl font-bold text-[#2E3339]">입력하신 이메일로 비밀번호를 확인해주세요!!</div>
                    <div
                        className="w-[20rem] flex items-center justify-center h-[50px] text-sm cursor-pointer text-white font-bold bg-primary-default rounded-[0.25rem]"
                        onClick={handleContinueClick}
                    >
                        로그인하러 가기
                    </div>
                </div>
            )
            }
        </div>
    );
}
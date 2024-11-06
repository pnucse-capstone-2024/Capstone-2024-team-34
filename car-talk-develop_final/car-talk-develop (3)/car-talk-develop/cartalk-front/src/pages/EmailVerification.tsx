import { emailVerification, emailVerificationConfirm } from 'apis/user.api';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';

export default function Page() {
    const navigate = useNavigate();

    const [code, setCode] = useState('');
    const { id } = useParams();

    const handleEmailVerificationConfirmClick = async () => {
        if (!id) return;
        try {
            const response = await emailVerificationConfirm(id, code);
            console.log(response);
            if (response.status === 200) {
                navigate('/');
            }
        } catch (error) {
            console.error(error);

        }
    }

    return (
        <div className="flex items-center justify-center h-full">
            <div className="flex flex-col w-[20rem] gap-5 text-center">
                <div className="text-3xl font-bold text-[#2E3339]">이메일 인증하기</div>
                <div className="relative">
                    <input
                        type="text"
                        id="code"
                        value={code}
                        onChange={(e) => setCode(e.target.value)}
                        className="block px-2.5 pb-2.5 pt-4 w-full text-sm text-primary-dark bg-transparent rounded-lg border border-primary-default appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                        placeholder=" " />
                    <label
                        htmlFor="code"
                        className="absolute text-sm text-primary-dark duration-300 transform -translate-y-4 scale-75 top-2 z-10 origin-[0] bg-white px-2 peer-focus:px-2 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto start-1">인증 코드</label>
                </div>
                <div
                    className="flex items-center justify-center h-[50px] text-sm cursor-pointer text-white font-bold bg-primary-default rounded-[0.25rem]"
                    onClick={handleEmailVerificationConfirmClick}
                >
                    완료하기
                </div>
            </div>
        </div>
    );
}
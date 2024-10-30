import { ReactComponent as SendIcon } from 'assets/images/send.icon.svg';
import { ReactComponent as ClipIcon } from 'assets/images/clip.icon.svg';
import { useNavigate } from 'react-router-dom';

export default function Page() {
    const navigate = useNavigate();

    const handleLoginClick = () => {
        navigate('/login');
    }

    const handleRegisterClick = () => {
        navigate('/register');
    }

    return (
        <div className="flex flex-col items-center justify-center h-full">
            <div className="w-full flex justify-between px-5 py-6">
                <span className="text-2xl font-bold">CarTalk</span>
                <div className="flex gap-2">
                    <button
                        className="text-sm font-bold text-white p-2 bg-primary-default border border-black rounded-[10px]"
                        onClick={handleLoginClick}
                    >
                        로그인
                    </button>
                    <button
                        className="text-sm font-normal p-2 bg-[#B2BBC6] bg-opacity-20 rounded-[10px] border border-black"
                        onClick={handleRegisterClick}
                    >
                        회원 가입
                    </button>
                </div>
            </div>
            <div className="w-full h-full text-center items-center flex justify-center pb-20">
                <div className="flex flex-col gap-2">
                    <span className="font-normal text-[40px] italic">Discover the best cars for you & your lifestyle</span>
                    <span className="font-semibold text-xl text-[#919191]">Find your perfect car with our smart recommendations.</span>
                </div>
            </div>
            <div className="flex flex-col w-full max-w-[695px]">
                <div className="flex items-center border-2 border-primary-default rounded-full px-3">
                    <ClipIcon className='m-1'></ClipIcon>
                    <input className="w-full p-3 focus:outline-none" type="text" placeholder="채팅을 시작해 보세요!" />
                    <SendIcon className='m-1'></SendIcon>
                </div>
                <div className="text-xs text-[#9A9B9F] py-3 text-center">
                    Used Car Recommendation Chatbot Based on Natural Language Processing
                </div>
            </div>
        </div>
    );
}
import { useLocation } from "react-router-dom";

import AuthLayout from "./AuthLayout";
import ContentLayout from "./ContentLayout";

type Props = {
    children: React.ReactNode;
}
export default function BaseLayout({ children }: Props) {
    const location = useLocation();

    const isAuthPage = location.pathname.startsWith("/login") || location.pathname.startsWith("/register");

    return (
        <div className="font-pretendard w-full h-full">
            {isAuthPage ? (
                <AuthLayout>
                    {children}
                </AuthLayout>
            ) : (
                <ContentLayout>
                    {children}
                </ContentLayout>
            )}
        </div>
    )
}
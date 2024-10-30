import SideBar from "../components/common/SideBar";

type Props = {
    children: React.ReactNode;
};
export default function BaseLayout({ children }: Props) {
    return (
        <div className="w-full flex mx-auto h-full">
            <div className="max-w-[250px] w-full">
                <SideBar />
            </div>
            <div className="min-w-[1040px] w-full">
                {children}
            </div>
        </div>
    );
}
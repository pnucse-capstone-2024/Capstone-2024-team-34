type Props = {
    children: React.ReactNode;
};
export default function BaseLayout({ children }: Props) {
    return (
        <div className="w-full h-screen items-center">
            {children}
        </div>
    );
}
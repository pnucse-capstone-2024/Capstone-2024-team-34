
export interface ApiResponse<T> {
    status: number;
    response: T | null;
    errorMessage: string | null;
}

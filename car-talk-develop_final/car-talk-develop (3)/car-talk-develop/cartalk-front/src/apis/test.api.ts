import { get, post, del, patch } from "../utils/serverHelper";

export const getTestData = async () => {
  return await get("/test");
};

export const postTestData = async () => {
  return await post("/test");
};

export const delTestData = async () => {
  return await del("/test");
};

export const patchTestData = async (data: object) => {
  return await patch("/api/v1/auth/test", data);
};

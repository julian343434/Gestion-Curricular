import { BASE_URL } from "../environment";

export const GetAccessToken = async ({ username, password }) => {
    try {
        // const response = await fetch(`${BASE_URL}/authenticate`, {
        //     method: "POST",
        //     headers: {
        //         "Content-Type": "application/json",
        //     },
        //     body: JSON.stringify({ username, password }),
        // });

        // const data = await response.json();
        // return data.jwt;


        return "1234567890";

    } catch (error) {
        console.error(error);
    }
};
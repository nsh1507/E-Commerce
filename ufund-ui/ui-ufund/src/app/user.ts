import { Need } from "./need";

export interface User {
    id: number;
    username: string;
    password: string;
    isAdmin: boolean;
    cart: Array<Need>;
}
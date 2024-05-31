import { Need } from "./need";

export interface FundingBasket {
    username: String,
    basket: {
        [key: string]: Need;
    }
}
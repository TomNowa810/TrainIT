/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class DefaultService {

    /**
     * Check if User is known
     * @param userName 
     * @param password 
     * @returns any Return Object of an User
     * @throws ApiError
     */
    public static checkLogin(
userName: string,
password: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/login',
            path: {
                'userName': userName,
                'password': password,
            },
        });
    }

}

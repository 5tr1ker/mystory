/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
export type Libraries = ("services" | "clusterer" | "drawing")[];
export interface LoaderOptions {
    /**
     * script 객체 생성시 사용자 정의 id
     */
    id?: string;
    /**
     * 발급 받은 Kakao 지도 Javscript API 키.
     *
     * @see [준비하기](https://apis.map.kakao.com/web/guide/#ready)
     */
    appkey: string;
    /**
     * 사용하는 라이브러리 목록
     *
     * Kakao 지도 Javascript API 는 지도와 함께 사용할 수 있는 라이브러리 를 지원하고 있습니다.
     * 라이브러리는 javascript API와 관련되어 있지만 조금 특화된 기능을 묶어둔 것을 말합니다. 이 기능은 추가로 불러와서 사용할 수 있도록 되어있습니다.
     * 현재 사용할 수 있는 라이브러리는 다음과 같습니다.
     *
     * clusterer: 마커를 클러스터링 할 수 있는 클러스터러 라이브러리 입니다.
     * services: 장소 검색 과 주소-좌표 변환 을 할 수 있는 services 라이브러리 입니다.
     * drawing: 지도 위에 마커와 그래픽스 객체를 쉽게 그릴 수 있게 그리기 모드를 지원하는 drawing 라이브러리 입니다.
     * 라이브러리는 계속해서 추가될 예정입니다.
     */
    libraries?: Libraries;
    /**
     * 사용자 정의 Kakao 지도 javascript 경로 지정
     *
     * @default "//dapi.kakao.com/v2/maps/sdk.js"
     */
    url?: string;
    /**
     * 보안을 위한 nonce 값 설정
     */
    nonce?: string;
    /**
     * 스크립트 로드 재시도 횟수
     */
    retries?: number;
}
export declare enum LoaderStatus {
    INITIALIZED = 0,
    LOADING = 1,
    SUCCESS = 2,
    FAILURE = 3
}
export type LoaderErorr = Event | string;
/**
 * Kakao Map Api Loader
 *
 * `new Loader(options).load()` 함수를 이용하여 Api를 비동기적으로 삽입할 수 있습니다.
 *
 * 해당 Loader를 이용시 `react-kakao-maps-sdk` 내부에서 injection 되는 이벤트를 감지하여 kakao map api 로딩 이후에 렌더링을 진행합니다.
 */
export declare class Loader {
    private static instance;
    private static loadEventCallback;
    readonly id: string;
    readonly appkey: string;
    readonly url: string;
    readonly libraries: Libraries;
    readonly nonce: string | undefined;
    readonly retries: number;
    private callbacks;
    private done;
    private loading;
    private errors;
    private onEvent;
    constructor({ appkey, id, libraries, nonce, retries, url, }: LoaderOptions);
    get options(): {
        appkey: string;
        id: string;
        libraries: Libraries;
        nonce: string | undefined;
        retries: number;
        url: string;
    };
    static addLoadEventLisnter(callback: (err?: LoaderErorr) => void): (err?: LoaderErorr) => void;
    static removeLoadEventLisnter(callback: (err?: LoaderErorr) => void): boolean;
    load(): Promise<typeof kakao>;
    get status(): LoaderStatus;
    private get failed();
    private loadCallback;
    private resetIfRetryingFailed;
    private reset;
    private execute;
    private setScript;
    private loadErrorCallback;
    createUrl(): string;
    private deleteScript;
    private callback;
    private static equalOptions;
}

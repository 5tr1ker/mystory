/// <reference types="kakao.maps.d.ts" />
/**
 * kakao map 객체를 가져오는 hook 입니다.
 * Map 객체 내부가 아니라면 Error를 발생 시킵니다.
 */
export declare const useMap: (componentName?: string) => kakao.maps.Map;

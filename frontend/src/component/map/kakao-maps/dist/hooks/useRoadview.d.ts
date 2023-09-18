/// <reference types="kakao.maps.d.ts" />
/**
 * kakao roadview 객체를 가져오는 hook 입니다.
 * Roadview 객체 내부가 아니라면 Error를 발생 시킵니다.
 */
export declare const useRoadview: (componentName?: string) => kakao.maps.Roadview;

/// <reference types="kakao.maps.d.ts" />
import React from "react";
export interface CustomOverlayRoadviewProps {
    /**
     * 커스텀 오버레이의 좌표
     */
    position: {
        lat: number;
        lng: number;
    } | {
        /**
         * 가로 각도, 0부터 360 사이의 값으로 북쪽부터 시계방향으로 대응한다.
         */
        pan: number;
        /**
         * 세로 각도, -90부터 90 사이의 값으로 위쪽부터 아래쪽으로 대응한다.
         */
        tilt: number;
        /**
         * 확대 수준, -3부터 3 사이의 정수이다.
         */
        zoom?: number;
        /**
         * 특정 위치의 로드뷰 고유의 아이디 값
         */
        panoId?: number;
    };
    /**
     * true 로 설정하면 컨텐츠 영역을 클릭했을 경우 지도 이벤트를 막아준다.
     */
    clickable?: boolean;
    /**
     * 컨텐츠의 x축 위치. 0_1 사이의 값을 가진다. 기본값은 0.5
     */
    xAnchor?: number;
    /**
     * 컨텐츠의 y축 위치. 0_1 사이의 값을 가진다. 기본값은 0.5
     */
    yAnchor?: number;
    /**
     * 커스텀 오버레이의 z-index
     */
    zIndex?: number;
    /**
     * 로드뷰상에서 커스텀 오버레이의 높이(위치)를 지정한다.
     * 단위는 m(미터)이며 현재 로드뷰의 바닥 높이를 기준으로 떨어져있는 높이를 말한다.
     */
    altitude?: number;
    /**
     * 커스텀 오버레이의 가시반경을 설정한다.
     * 로드뷰의 위치와 커스텀 오버레이의 위치 사이의 거리가 가시반경 이내일 경우에만 로드뷰상에 노출된다.
     * 단위는 m(미터)이며 기본값은 500m이다.
     */
    range?: number;
    /**
     * 커스텀 오버레이를 생성 후 해당 객체를 가지고 callback 함수를 실행 시켜줌
     */
    onCreate?: (customOverlay: kakao.maps.CustomOverlay) => void;
}
/**
 * Roadview에 CustomOverlay를 올릴 때 사용하는 컴포넌트 입니다.
 * `onCreate` 이벤트 또는 `ref` 객체를 통해서 `CustomOverlay` 객체에 직접 접근 및 초기 설정 작업을 지정할 수 있습니다.
 */
export declare const CustomOverlayRoadview: React.ForwardRefExoticComponent<CustomOverlayRoadviewProps & {
    children?: React.ReactNode;
} & React.RefAttributes<kakao.maps.CustomOverlay>>;

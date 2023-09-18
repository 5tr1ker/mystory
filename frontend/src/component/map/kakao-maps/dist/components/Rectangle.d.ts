/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
import React from "react";
interface LatLng {
    lat: number;
    lng: number;
}
export interface RectangleProps {
    bounds: {
        sw: LatLng;
        ne: LatLng;
    };
    /**
     * #xxxxxx 형태의 채움 색 (기본값: ‘#F10000’)
     */
    fillColor?: string;
    /**
     * 채움 불투명도 (0-1) (기본값: 0)
     */
    fillOpacity?: number;
    /**
     * 픽셀 단위의 선 두께 (기본값: 3)
     */
    strokeWeight?: number;
    /**
     * #xxxxxx 형태의 선 색 (기본값: ‘#F10000’)
     */
    strokeColor?: string;
    /**
     * 선 불투명도 (0-1) (기본값: 0.6)
     */
    strokeOpacity?: number;
    /**
     * 선 스타일 (기본값: ‘solid’)
     */
    strokeStyle?: kakao.maps.StrokeStyles;
    /**
     * 선의 z-index 속성 값
     */
    zIndex?: number;
    /**
     * 마우스 커서를 올리면 발생한다.
     */
    onMouseover?: (target: kakao.maps.Rectangle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 마우스 커서가 벗어나면 발생한다.
     */
    onMouseout?: (target: kakao.maps.Rectangle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 마우스를 움직이면 발생한다.
     */
    onMousemove?: (target: kakao.maps.Rectangle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 마우스 버튼을 누르면 발생한다.
     */
    onMousedown?: (target: kakao.maps.Rectangle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 클릭하면 발생한다.
     */
    onClick?: (target: kakao.maps.Rectangle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 객체 생성시 호출 됩니다.
     */
    onCreate?: (target: kakao.maps.Rectangle) => void;
}
/**
 * Map 상에 사각형을 그립니다.
 */
export declare const Rectangle: React.ForwardRefExoticComponent<RectangleProps & React.RefAttributes<kakao.maps.Rectangle>>;
export {};

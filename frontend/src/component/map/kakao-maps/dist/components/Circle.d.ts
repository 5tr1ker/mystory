/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
import React from "react";
export interface CircleProps {
    /**
     * 중심 좌표를 지정합니다.
     */
    center: {
        lat: number;
        lng: number;
    };
    /**
     * 원의 반지름 크기를 지정합니다 (미터 단위).
     */
    radius: number;
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
     * 원의 z-index 속성 값
     */
    zIndex?: number;
    /**
     * 원에 마우스 커서를 올리면 발생한다.
     */
    onMouseover?: (target: kakao.maps.Circle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 마우스 커서가 원에서 벗어나면 발생한다.
     */
    onMouseout?: (target: kakao.maps.Circle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 원에서 마우스를 움직이면 발생한다.
     */
    onMousemove?: (target: kakao.maps.Circle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 원에서 마우스 버튼을 누르면 발생한다.
     */
    onMousedown?: (target: kakao.maps.Circle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 원을 클릭하면 발생한다.
     */
    onClick?: (target: kakao.maps.Circle, mouseEvent: kakao.maps.event.MouseEvent) => void;
    /**
     * 객체 생성시 호출 됩니다.
     */
    onCreate?: (target: kakao.maps.Circle) => void;
}
/**
 * Map 상에 원을 그립니다.
 */
export declare const Circle: React.ForwardRefExoticComponent<CircleProps & React.RefAttributes<kakao.maps.Circle>>;

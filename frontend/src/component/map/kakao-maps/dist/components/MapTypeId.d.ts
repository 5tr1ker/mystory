/// <reference types="kakao.maps.d.ts" />
import React from "react";
export interface MapTypeIdProps {
    /**
     * MapTypeId를 정의 한다.
     */
    type: kakao.maps.MapTypeId | keyof typeof kakao.maps.MapTypeId;
}
/**
 * 추가적으로 보이고 싶은 지도 타입을 추가 할 때 사용한다.
 */
export declare const MapTypeId: React.FC<MapTypeIdProps>;

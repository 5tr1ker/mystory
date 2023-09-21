/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
import React from "react";
export interface ZoomControlProps {
    /**
     * ZoomControl의 Position를 정의 한다.
     */
    position?: kakao.maps.ControlPosition | keyof typeof kakao.maps.ControlPosition;
}
/**
 * 확대·축소 컨트롤을 생성한다.
 */
export declare const ZoomControl: React.ForwardRefExoticComponent<ZoomControlProps & React.RefAttributes<kakao.maps.ZoomControl>>;

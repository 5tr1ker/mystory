/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
import React from "react";
import { CompatibleReactElement, PolymorphicComponentPropsWithOutRef } from "../types";
export interface _StaticMapProps {
    /**
     * MapContinaer의 id에 대해서 지정합니다.
     */
    id?: string;
    /**
     * 중심으로 설정할 위치 입니다.
     */
    center: {
        lat: number;
        lng: number;
    };
    /**
     * 확대 수준
     * @default 3
     */
    level?: number;
    /**
     * 지도 종류 (기본값: 일반 지도)
     */
    mapTypeId?: kakao.maps.MapTypeId | keyof typeof kakao.maps.MapTypeId;
    /**
     * 이미지 지도에 표시할 마커 또는 마커 배열
     */
    marker: boolean | {
        /**
         * 마커 tooltip에 표시될 내용
         */
        text?: string;
        /**
         * 마커 포지션
         */
        position?: {
            lat: number;
            lng: number;
        };
    } | Array<{
        /**
         * 마커 tooltip에 표시될 내용
         */
        text?: string;
        /**
         * 마커 포지션
         */
        position: {
            lat: number;
            lng: number;
        };
    }>;
    /**
     * StaticMap 생성 이벤트 핸들러
     */
    onCreate?: (maker: kakao.maps.StaticMap) => void;
}
export type StaticMapProps<T extends React.ElementType = "div"> = PolymorphicComponentPropsWithOutRef<T, _StaticMapProps> & React.RefAttributes<kakao.maps.StaticMap>;
export type StaticMapComponent = <T extends React.ElementType = "div">(props: StaticMapProps<T>) => CompatibleReactElement;
export declare const StaticMap: StaticMapComponent;

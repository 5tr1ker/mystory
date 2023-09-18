/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
import React from "react";
interface InfoWindowProps {
    map: kakao.maps.Map | kakao.maps.Roadview;
    position: kakao.maps.LatLng | kakao.maps.Viewpoint;
    marker?: kakao.maps.Marker;
    /**
     * 인포윈도우를 열 때 지도가 자동으로 패닝하지 않을지의 여부
     * @default false
     */
    disableAutoPan?: boolean;
    /**
     * 삭제 가능한 인포윈도우
     */
    removable?: boolean;
    /**
     * 인포윈도우 엘리먼트의 z-index 속성 값
     */
    zIndex?: number;
    /**
     * 로드뷰에 올라있는 인포윈도우의 높이 값(m 단위)
     */
    altitude?: number;
    /**
     * 로드뷰 상에서 인포윈도우의 가시반경(m 단위), 두 지점 사이의 거리가 지정한 값보다 멀어지면 인포윈도우는 보이지 않게 된다
     */
    range?: number;
    /**
     * 인포윈도우 객체 생성후 해당 객체를 반환하는 함수
     */
    onCreate?: (infoWindow: kakao.maps.InfoWindow) => void;
}
export declare const InfoWindow: React.ForwardRefExoticComponent<InfoWindowProps & {
    children?: React.ReactNode;
} & React.RefAttributes<kakao.maps.InfoWindow>>;
export {};

/// <reference types="kakao.maps.d.ts" />
import React from "react";
import { CompatibleReactElement, PolymorphicComponentPropsWithOutRef } from "../types";
export declare const KakaoRoadviewContext: React.Context<kakao.maps.Roadview>;
export interface _RoadviewProps {
    /**
     * 중심으로 설정할 위치 입니다.
     * 해당 lat와 lng에 해당하는 radius범위 안에서 가장가까운 Roadview을 선택합니다.
     * 만약 없다면 lat, lng로 설정 됩니다.
     */
    position: {
        lat: number;
        lng: number;
        radius: number;
    };
    /**
     * 로드뷰 시작 지역의 고유 아이디 값.
     */
    panoId?: number;
    /**
     * panoId가 유효하지 않을 경우 지도좌표를 기반으로 데이터를 요청할 수평 좌표값.
     */
    panoX?: number;
    /**
     * panoId가 유효하지 않을 경우 지도좌표를 기반으로 데이터를 요청할 수직 좌표값.
     */
    panoY?: number;
    /**
     * 로드뷰 처음 실행시에 바라봐야 할 수평 각. 0이 정북방향. (0_360)
     */
    pan?: number;
    /**
     * 로드뷰 처음 실행시에 바라봐야 할 수직 각.(-90_90)
     */
    tilt?: number;
    /**
     * 로드뷰 줌 초기값.(-3_3)
     */
    zoom?: number;
    /**
     * 로드뷰 생성후 해당 객체를 전달하는 callback.
     */
    onCreate?: (roadview: kakao.maps.Roadview) => void;
    /**
     * 로드뷰가 초기화를 끝내면 발생한다.
     */
    onInit?: (target: kakao.maps.Roadview) => void;
    /**
     * 파노라마 ID가 바뀌면 발생한다.
     */
    onPanoidChange?: (target: kakao.maps.Roadview) => void;
    /**
     * 시점이 바뀌면 발생한다.
     */
    onViewpointChange?: (target: kakao.maps.Roadview) => void;
    /**
     * 지도 좌표가 바뀌면 발생한다.
     */
    onPositionChanged?: (target: kakao.maps.Roadview) => void;
    /**
     * getNearestPanoId 동작 실패시 호출 합니다.
     */
    onErrorGetNearestPanoId?: (target: kakao.maps.Roadview) => void;
    children?: React.ReactNode | undefined;
}
export type RoadViewProps<T extends React.ElementType = "div"> = PolymorphicComponentPropsWithOutRef<T, _RoadviewProps> & React.RefAttributes<kakao.maps.Roadview>;
export type RoadviewComponent = <T extends React.ElementType = "div">(props: RoadViewProps<T>) => CompatibleReactElement;
/**
 * Roadview를 Roadview를 생성하는 컴포넌트 입니다.
 * props로 받는 `on*` 이벤트는 해당 `kakao.maps.Map` 객체를 반환 합니다.
 * `onCreate` 이벤트를 통해 생성 후 `Roadview` 객체에 직접 접근하여 초기 설정이 가능합니다.
 */
export declare const Roadview: RoadviewComponent;

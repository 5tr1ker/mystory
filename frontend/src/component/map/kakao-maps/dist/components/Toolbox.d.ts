/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
import React from "react";
export type ToolboxProps = {
    /**
     * Toolbox의 Position를 정의 한다.
     */
    position?: kakao.maps.ControlPosition | keyof typeof kakao.maps.ControlPosition;
};
/**
 * 그리기 툴박스를 생성합니다.
 *
 * 해당 컴포넌트는 반드시 `DrawingManager` 컴포넌트의 자식으로 존재해야 합니다.
 */
export declare const Toolbox: React.ForwardRefExoticComponent<ToolboxProps & React.RefAttributes<kakao.maps.drawing.Toolbox>>;

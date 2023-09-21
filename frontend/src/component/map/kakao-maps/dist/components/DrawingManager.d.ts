/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
/// <reference types="kakao.maps.d.ts" />
import React, { ReactNode } from "react";
export declare const DrawingManagerContext: React.Context<kakao.maps.drawing.DrawingManager<kakao.maps.drawing.OverlayType>>;
export type DrawingManagerProps<T extends kakao.maps.drawing.OverlayType = kakao.maps.drawing.OverlayType> = Partial<kakao.maps.drawing.OverlayOptions> & Pick<kakao.maps.drawing.OverlayOptions, `${T}Options`> & {
    /**
     * 마우스 오버 시 가이드 툴팁 표시 여부. ‘draw’, ‘drag’, ‘edit’ 3가지를 지정할 수 있다 (기본값: 모두 표시 안함)
     * 예를들어 [‘draw’]로 설정하면 객체를 그릴때 가이드 툴팁이 표시된다
     */
    guideTooltip?: Array<"draw" | "drag" | "edit">;
    /**
     * 사용할 그리기 요소 지정한다 (기본값: 모든 그리기 요소)
     */
    drawingMode?: Array<T | `${T}`>;
    /**
     * 그리기 요소를 선택하면 발생한다.
     */
    onSelect?: (drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void;
    /**
     * 그리기를 시작하면 발생한다.
     */
    onDrawstart?: (drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void;
    /**
     * 그리기 시작 후, 마우스를 이동하면 발생한다.
     */
    onDraw?: (drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void;
    /**
     * 그리기를 시작하면 발생한다.
     */
    onDrawend?: (drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void;
    /**
     * 다음 단계 그리기를 하면 발생한다. (Polyline, Polygon, Arrow 한정)
     */
    onDrawnext?: (drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void;
    /**
     * 그리기를 취소하면 발생한다.
     */
    onCancle?: (drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void;
    /**
     * 그리기 요소를 삭제하면 발생한다.
     */
    onRemove?: (drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void;
    /**
     * 그리기 요소들의 상태가 변경되면 발생한다.
     * 각 요소의 생성/수정/이동/삭제 동작과 undo 또는 redo 함수 호출이 이에 해당한다.
     */
    onStateChanged?: (drawingManager: kakao.maps.drawing.DrawingManager<T>) => void;
    /**
     * 객체 생성후 해당 이벤트가 발생합니다.
     */
    onCreate?: (drawingManager: kakao.maps.drawing.DrawingManager<T>) => void;
    /**
     * Toolbox에 대해서 추가할 때 사용합니다.
     */
    children?: ReactNode;
};
/**
 * 그리기 관리자 객체를 생성하는 컴포넌트 입니다.
 *
 * 초기 생성자에 필요한 Props는 최초 렌더링에만 반영을 하고 이후에는 값이 변경되더라도 적용되지 않습니다.
 *
 * > `on~` 시리즈를 제외한 props는 초기 렌더링 이후 작동 안함
 *
 * DrawingManager는 이전 Map, Marker, CustomOverlay와 달리 Props를 통해 제어를 하는 것이 아닌 직접 DrawingManager 객체를 이용하여 제어를 해야 합니다.
 *
 * 이를 위해 ref 객체를 통해 `DrawingManager` 객체를 접근 할 수 있으며, 이를 활용하여 여러 이벤트 처리 및 제어가 가능합니다.
 *
 * TypeScript 사용자를 위한 `Generic`이 적용되어 있으므로, `ref` 객체에 대한 typing 및 `drawingMode`에 대해 확실하게 정의해야 합니다.
 *
 * ```tsx
 * const Main = () => {
 *     const managerRef = useRef<kakao.maps.drawing.DrawingManager<
 *       kakao.maps.drawing.OverlayType.POLYLINE
 *     >>(null);
 *
 *     function selectOverlay(type: string) {
 *       const manager = managerRef.current;
 *       manager.cancel();
 *       manager.select(kakao.maps.drawing.OverlayType.POLYLINE);
 *     }
 *
 *     return (
 *       <>
 *         <Map
 *           center={{
 *             // 지도의 중심좌표
 *             lat: 33.450701,
 *             lng: 126.570667,
 *           }}
 *           style={{
 *             width: "100%",
 *             height: "450px",
 *           }}
 *           level={3} // 지도의 확대 레벨
 *         >
 *           <DrawingManager
 *             ref={managerRef}
 *             drawingMode={[
 *               kakao.maps.drawing.OverlayType.POLYLINE,
 *             ]}
 *             guideTooltip={['draw', 'drag', 'edit']}
 *             polylineOptions={{
 *               draggable: true,
 *               removable: true,
 *               editable: true
 *             }}
 *           />
 *         </Map>
 *         <button onClick={(e) => {
 *           selectOverlay('POLYLINE')
 *         }}>선</button>
 *       </>
 *     )
 *   }
 * ```
 *
 * > JavaScript 코드 버전
 *
 * ```jsx live
 * function() {
 *   const Main = () => {
 *     const managerRef = useRef(null);
 *
 *     function selectOverlay() {
 *       const manager = managerRef.current;
 *       manager.cancel();
 *       manager.select(kakao.maps.drawing.OverlayType.POLYLINE);
 *     }
 *
 *     return (
 *       <>
 *         <Map
 *           center={{
 *             // 지도의 중심좌표
 *             lat: 33.450701,
 *             lng: 126.570667,
 *           }}
 *           style={{
 *             width: "100%",
 *             height: "450px",
 *           }}
 *           level={3} // 지도의 확대 레벨
 *         >
 *           <DrawingManager
 *             ref={managerRef}
 *             drawingMode={[
 *               kakao.maps.drawing.OverlayType.POLYLINE,
 *             ]}
 *             guideTooltip={['draw', 'drag', 'edit']}
 *             polylineOptions={{
 *               draggable: true,
 *               removable: true,
 *               editable: true
 *             }}
 *           />
 *         </Map>
 *         <button onClick={selectOverlay}>선</button>
 *       </>
 *     )
 *   }
 *   return (<Main />)
 * }
 * ```
 */
export declare const DrawingManager: <T extends kakao.maps.drawing.OverlayType = kakao.maps.drawing.OverlayType>(_props: Partial<kakao.maps.drawing.OverlayOptions> & Pick<kakao.maps.drawing.OverlayOptions, `${T}Options`> & {
    /**
     * 마우스 오버 시 가이드 툴팁 표시 여부. ‘draw’, ‘drag’, ‘edit’ 3가지를 지정할 수 있다 (기본값: 모두 표시 안함)
     * 예를들어 [‘draw’]로 설정하면 객체를 그릴때 가이드 툴팁이 표시된다
     */
    guideTooltip?: ("drag" | "draw" | "edit")[] | undefined;
    /**
     * 사용할 그리기 요소 지정한다 (기본값: 모든 그리기 요소)
     */
    drawingMode?: (T | `${T}`)[] | undefined;
    /**
     * 그리기 요소를 선택하면 발생한다.
     */
    onSelect?: ((drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void) | undefined;
    /**
     * 그리기를 시작하면 발생한다.
     */
    onDrawstart?: ((drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void) | undefined;
    /**
     * 그리기 시작 후, 마우스를 이동하면 발생한다.
     */
    onDraw?: ((drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void) | undefined;
    /**
     * 그리기를 시작하면 발생한다.
     */
    onDrawend?: ((drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void) | undefined;
    /**
     * 다음 단계 그리기를 하면 발생한다. (Polyline, Polygon, Arrow 한정)
     */
    onDrawnext?: ((drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void) | undefined;
    /**
     * 그리기를 취소하면 발생한다.
     */
    onCancle?: ((drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void) | undefined;
    /**
     * 그리기 요소를 삭제하면 발생한다.
     */
    onRemove?: ((drawingManager: kakao.maps.drawing.DrawingManager<T>, event: kakao.maps.drawing.MouseEvent) => void) | undefined;
    /**
     * 그리기 요소들의 상태가 변경되면 발생한다.
     * 각 요소의 생성/수정/이동/삭제 동작과 undo 또는 redo 함수 호출이 이에 해당한다.
     */
    onStateChanged?: ((drawingManager: kakao.maps.drawing.DrawingManager<T>) => void) | undefined;
    /**
     * 객체 생성후 해당 이벤트가 발생합니다.
     */
    onCreate?: ((drawingManager: kakao.maps.drawing.DrawingManager<T>) => void) | undefined;
    /**
     * Toolbox에 대해서 추가할 때 사용합니다.
     */
    children?: ReactNode;
} & React.RefAttributes<kakao.maps.drawing.DrawingManager<T>>) => null;

'use strict';

var React = require('react');
var useMap = require('../hooks/useMap.cjs');
var jsxRuntime = require('react/jsx-runtime');

const DrawingManagerContext = /*#__PURE__*/React.createContext(undefined);
function useDrawingManagerEvent(target, type, callback) {
  React.useLayoutEffect(() => {
    if (!target || !callback) return;
    const wrapCallback = function () {
      for (var _len = arguments.length, args = new Array(_len), _key = 0; _key < _len; _key++) {
        args[_key] = arguments[_key];
      }
      if (AbortSignal === undefined) return callback(target);else return callback(target, ...args);
    };

    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    target.addListener(type, wrapCallback);
  }, [callback, target, type]);
}

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
const DrawingManager = /*#__PURE__*/React.forwardRef(function DrawingManager(_ref, ref) {
  let {
    arrowOptions,
    circleOptions,
    ellipseOptions,
    markerOptions,
    polygonOptions,
    polylineOptions,
    rectangleOptions,
    drawingMode,
    guideTooltip,
    onSelect,
    onDrawstart,
    onDraw,
    onDrawend,
    onDrawnext,
    onCancle,
    onRemove,
    onStateChanged,
    onCreate,
    children
  } = _ref;
  const map = useMap.useMap("Toolbox");
  const drawingManager = React.useMemo(() => {
    if (!window.kakao.maps.drawing) {
      console.warn("drawing 라이브러리를 별도 로드 해야 사용 가능합니다. `https://apis.map.kakao.com/web/guide/#loadlibrary`");
      return;
    }
    return new kakao.maps.drawing.DrawingManager({
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore
      map,
      drawingMode,
      guideTooltip,
      arrowOptions,
      circleOptions,
      ellipseOptions,
      markerOptions,
      polygonOptions,
      polylineOptions,
      rectangleOptions
    });
  },
  // eslint-disable-next-line react-hooks/exhaustive-deps
  []);
  React.useImperativeHandle(ref, () => drawingManager, [drawingManager]);
  React.useLayoutEffect(() => {
    drawingManager && onCreate && onCreate(drawingManager);
  }, [drawingManager, onCreate]);
  useDrawingManagerEvent(drawingManager, "select", onSelect);
  useDrawingManagerEvent(drawingManager, "drawstart", onDrawstart);
  useDrawingManagerEvent(drawingManager, "draw", onDraw);
  useDrawingManagerEvent(drawingManager, "drawend", onDrawend);
  useDrawingManagerEvent(drawingManager, "drawnext", onDrawnext);
  useDrawingManagerEvent(drawingManager, "cancel", onCancle);
  useDrawingManagerEvent(drawingManager, "remove", onRemove);
  useDrawingManagerEvent(drawingManager, "state_changed", onStateChanged);
  if (!drawingManager) return null;
  return /*#__PURE__*/jsxRuntime.jsx(DrawingManagerContext.Provider, {
    value: drawingManager,
    children: children
  });
});

exports.DrawingManager = DrawingManager;
exports.DrawingManagerContext = DrawingManagerContext;

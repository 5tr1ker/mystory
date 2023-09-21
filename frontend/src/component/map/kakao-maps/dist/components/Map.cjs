'use strict';

var React = require('react');
var useIsomorphicLayoutEffect = require('../hooks/useIsomorphicLayoutEffect.cjs');
var useKakaoEvent = require('../hooks/useKakaoEvent.cjs');
var kakaoMapApiLoader = require('../util/kakaoMapApiLoader.cjs');
var constants = require('../util/constants.cjs');
var jsxRuntime = require('react/jsx-runtime');

const KakaoMapContext = /*#__PURE__*/React.createContext(undefined);
/**
 * 기본적인 Map 객체를 생성하는 Comeponent 입니다.
 * props로 받는 `on*` 이벤트는 해당 `kakao.maps.Map` 객체를 함께 인자로 전달 합니다.
 *
 * `ref`를 통해 `map` 객체에 직접 접근하여 사용 또는 onCreate 이벤트를 이용하여 접근이 가능합니다.
 *
 * > *주의 사항* `Map`, `RoadView` 컴포넌트에 한하여, ref 객체가 컴포넌트 마운트 시점에 바로 초기화가 안될 수 있습니다.
 * >
 * > 컴포넌트 마운트 시점에 `useEffect` 를 활용하여, 특정 로직을 수행하고 싶은 경우 `ref` 객체를 사용하는 것보다
 * > `onCreate` 이벤트와 `useState`를 함께 활용하여 제어하는 것을 추천 드립니다.
 */
const Map = /*#__PURE__*/React.forwardRef(function Map(_ref, ref) {
  let {
    id,
    as,
    children,
    center,
    isPanto = false,
    padding = 32,
    disableDoubleClick,
    disableDoubleClickZoom,
    draggable,
    zoomable,
    keyboardShortcuts,
    level,
    maxLevel,
    minLevel,
    mapTypeId,
    projectionId,
    scrollwheel,
    tileAnimation,
    onBoundsChanged,
    onCenterChanged,
    onClick,
    onDoubleClick,
    onDrag,
    onDragEnd,
    onDragStart,
    onIdle,
    onMaptypeidChanged,
    onMouseMove,
    onRightClick,
    onTileLoaded,
    onZoomChanged,
    onZoomStart,
    onCreate,
    ...props
  } = _ref;
  const Container = as || "div";
  const [isLoaded, setIsLoaded] = React.useState(false);
  const [map, setMap] = React.useState();
  const container = React.useRef(null);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    const callback = kakaoMapApiLoader.Loader.addLoadEventLisnter(err => setIsLoaded(!err));
    return () => {
      kakaoMapApiLoader.Loader.removeLoadEventLisnter(callback);
    };
  }, []);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!isLoaded) return;
    const MapContainer = container.current;
    if (!MapContainer) return;
    const initalMapCenter = "lat" in center ? new kakao.maps.LatLng(center.lat, center.lng) : new kakao.maps.Coords(center.x, center.y);
    const kakaoMap = new kakao.maps.Map(MapContainer, {
      center: initalMapCenter,
      disableDoubleClick,
      disableDoubleClickZoom,
      draggable,
      keyboardShortcuts,
      level,
      mapTypeId: typeof mapTypeId === "string" ? kakao.maps.MapTypeId[mapTypeId] : mapTypeId,
      projectionId,
      scrollwheel,
      tileAnimation
    });
    setMap(kakaoMap);
    return () => {
      MapContainer.innerHTML = "";
    };
  }, [isLoaded, disableDoubleClick, disableDoubleClickZoom, tileAnimation]);
  React.useImperativeHandle(ref, () => map, [map]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map || !onCreate) return;
    onCreate(map);
  }, [map, onCreate]);

  // center position 변경시 map center 변경
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map) return;
    let prevCenter = map.getCenter();
    if (prevCenter instanceof kakao.maps.Coords) {
      prevCenter = prevCenter.toLatLng();
    }
    const centerPosition = "lat" in center ? new kakao.maps.LatLng(center.lat, center.lng) : new kakao.maps.Coords(center.x, center.y);
    if (centerPosition instanceof kakao.maps.LatLng && centerPosition.equals(prevCenter) || centerPosition instanceof kakao.maps.Coords && centerPosition.toLatLng().equals(prevCenter)) {
      return;
    }
    if (isPanto) {
      map.panTo(centerPosition, padding);
    } else {
      map.setCenter(centerPosition);
    }
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
  }, [map, center.lat, center.lng, center.x, center.y]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map || typeof draggable === "undefined") return;
    map.setDraggable(draggable);
  }, [map, draggable]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map || typeof zoomable === "undefined") return;
    map.setZoomable(zoomable);
  }, [map, zoomable]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map || !keyboardShortcuts || typeof keyboardShortcuts !== "boolean") return;
    map.setKeyboardShortcuts(keyboardShortcuts);
  }, [map, keyboardShortcuts]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map || !level) return;
    map.setLevel(level);
  }, [map, level]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map || !mapTypeId) return;
    map.setMapTypeId(typeof mapTypeId === "string" ? kakao.maps.MapTypeId[mapTypeId] : mapTypeId);
  }, [map, mapTypeId]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map || !projectionId) return;
    map.setProjectionId(projectionId);
  }, [map, projectionId]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map || !maxLevel) return;
    map.setMaxLevel(maxLevel);
  }, [map, maxLevel]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!map || !minLevel) return;
    map.setMinLevel(minLevel);
  }, [map, minLevel]);
  useKakaoEvent.useKakaoEvent(map, "bounds_changed", onBoundsChanged);
  useKakaoEvent.useKakaoEvent(map, "center_changed", onCenterChanged);
  useKakaoEvent.useKakaoEvent(map, "click", onClick);
  useKakaoEvent.useKakaoEvent(map, "dblclick", onDoubleClick);
  useKakaoEvent.useKakaoEvent(map, "drag", onDrag);
  useKakaoEvent.useKakaoEvent(map, "dragstart", onDragStart);
  useKakaoEvent.useKakaoEvent(map, "dragend", onDragEnd);
  useKakaoEvent.useKakaoEvent(map, "idle", onIdle);
  useKakaoEvent.useKakaoEvent(map, "maptypeid_changed", onMaptypeidChanged);
  useKakaoEvent.useKakaoEvent(map, "mousemove", onMouseMove);
  useKakaoEvent.useKakaoEvent(map, "rightclick", onRightClick);
  useKakaoEvent.useKakaoEvent(map, "tilesloaded", onTileLoaded);
  useKakaoEvent.useKakaoEvent(map, "zoom_changed", onZoomChanged);
  useKakaoEvent.useKakaoEvent(map, "zoom_start", onZoomStart);
  return /*#__PURE__*/jsxRuntime.jsxs(jsxRuntime.Fragment, {
    children: [/*#__PURE__*/jsxRuntime.jsx(Container, {
      id: id || `${constants.SIGNATURE}_Map`,
      ...props,
      ref: container
    }), map && /*#__PURE__*/jsxRuntime.jsx(KakaoMapContext.Provider, {
      value: map,
      children: children
    })]
  });
});

exports.KakaoMapContext = KakaoMapContext;
exports.Map = Map;

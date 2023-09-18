import React, { useState, useRef, useImperativeHandle } from 'react';
import { useIsomorphicLayoutEffect } from '../hooks/useIsomorphicLayoutEffect.js';
import { useKakaoEvent } from '../hooks/useKakaoEvent.js';
import { Loader } from '../util/kakaoMapApiLoader.js';
import { SIGNATURE } from '../util/constants.js';
import { jsxs, Fragment, jsx } from 'react/jsx-runtime.js';

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
  const [isLoaded, setIsLoaded] = useState(false);
  const [map, setMap] = useState();
  const container = useRef(null);
  useIsomorphicLayoutEffect(() => {
    const callback = Loader.addLoadEventLisnter(err => setIsLoaded(!err));
    return () => {
      Loader.removeLoadEventLisnter(callback);
    };
  }, []);
  useIsomorphicLayoutEffect(() => {
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
  useImperativeHandle(ref, () => map, [map]);
  useIsomorphicLayoutEffect(() => {
    if (!map || !onCreate) return;
    onCreate(map);
  }, [map, onCreate]);

  // center position 변경시 map center 변경
  useIsomorphicLayoutEffect(() => {
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
  useIsomorphicLayoutEffect(() => {
    if (!map || typeof draggable === "undefined") return;
    map.setDraggable(draggable);
  }, [map, draggable]);
  useIsomorphicLayoutEffect(() => {
    if (!map || typeof zoomable === "undefined") return;
    map.setZoomable(zoomable);
  }, [map, zoomable]);
  useIsomorphicLayoutEffect(() => {
    if (!map || !keyboardShortcuts || typeof keyboardShortcuts !== "boolean") return;
    map.setKeyboardShortcuts(keyboardShortcuts);
  }, [map, keyboardShortcuts]);
  useIsomorphicLayoutEffect(() => {
    if (!map || !level) return;
    map.setLevel(level);
  }, [map, level]);
  useIsomorphicLayoutEffect(() => {
    if (!map || !mapTypeId) return;
    map.setMapTypeId(typeof mapTypeId === "string" ? kakao.maps.MapTypeId[mapTypeId] : mapTypeId);
  }, [map, mapTypeId]);
  useIsomorphicLayoutEffect(() => {
    if (!map || !projectionId) return;
    map.setProjectionId(projectionId);
  }, [map, projectionId]);
  useIsomorphicLayoutEffect(() => {
    if (!map || !maxLevel) return;
    map.setMaxLevel(maxLevel);
  }, [map, maxLevel]);
  useIsomorphicLayoutEffect(() => {
    if (!map || !minLevel) return;
    map.setMinLevel(minLevel);
  }, [map, minLevel]);
  useKakaoEvent(map, "bounds_changed", onBoundsChanged);
  useKakaoEvent(map, "center_changed", onCenterChanged);
  useKakaoEvent(map, "click", onClick);
  useKakaoEvent(map, "dblclick", onDoubleClick);
  useKakaoEvent(map, "drag", onDrag);
  useKakaoEvent(map, "dragstart", onDragStart);
  useKakaoEvent(map, "dragend", onDragEnd);
  useKakaoEvent(map, "idle", onIdle);
  useKakaoEvent(map, "maptypeid_changed", onMaptypeidChanged);
  useKakaoEvent(map, "mousemove", onMouseMove);
  useKakaoEvent(map, "rightclick", onRightClick);
  useKakaoEvent(map, "tilesloaded", onTileLoaded);
  useKakaoEvent(map, "zoom_changed", onZoomChanged);
  useKakaoEvent(map, "zoom_start", onZoomStart);
  return /*#__PURE__*/jsxs(Fragment, {
    children: [/*#__PURE__*/jsx(Container, {
      id: id || `${SIGNATURE}_Map`,
      ...props,
      ref: container
    }), map && /*#__PURE__*/jsx(KakaoMapContext.Provider, {
      value: map,
      children: children
    })]
  });
});

export { KakaoMapContext, Map };
